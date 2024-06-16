const tf = require('@tensorflow/tfjs-node');
const loadModel = require('./loadModel');
const InputError = require('../exceptions/InputError');

const calculateScore = (nutrition) => {
  const { 'Lemak Total': fat, Protein: protein, 'Karbohidrat Total': carbs, Gula: sugar } = nutrition;

  let sugarScore = 0;
  if (sugar < 1) sugarScore = 0;
  else if (sugar <= 5) sugarScore = 1;
  else if (sugar <= 10) sugarScore = 2;
  else sugarScore = 3;

  let carbScore = 0;
  if (carbs < 25) carbScore = 0;
  else if (carbs <= 50) carbScore = 1;
  else carbScore = 2;

  let proteinScore = 0;
  if (protein < 10) proteinScore = 0;
  else if (protein <= 20) proteinScore = 1;
  else proteinScore = 2;

  let fatScore = 0;
  if (fat < 3) fatScore = 0;
  else if (fat <= 10) fatScore = 1;
  else fatScore = 2;

  const totalScore = sugarScore + carbScore + proteinScore + fatScore;

  let label;
  if (totalScore <= 3) {
    label = 'Aman';
  } else if (totalScore <= 6) {
    label = 'Risiko Rendah';
  } else if (totalScore <= 9) {
    label = 'Risiko Sedang';
  } else {
    label = 'Risiko Tinggi';
  }

  return { label, totalScore, sugarScore, carbScore, proteinScore, fatScore };
};

const extractTextFromImage = async (imageBuffer) => {
  return new Promise((resolve, reject) => {
    Tesseract.recognize(imageBuffer, 'eng', {
      logger: (m) => console.log(m),
    })
      .then(({ data: { text } }) => {
        resolve(text);
      })
      .catch((err) => {
        reject(err);
      });
  });
};

const parseNutritionInfo = (text) => {
  const nutritionalInfo = {};
  const lines = text.split('\n');
  lines.forEach((line) => {
    const matchLemakTotal = /lemak total.*?(\d+\.?\d*)\s*(g|gr|9)/i.exec(line);
    const matchProtein = /protein\s*(\d+\.?\d*)\s*(g|gr|9)/i.exec(line);
    const matchKarbohidratTotal = /karbohidrat total.*?(\d+\.?\d*)\s*(g|gr|9)/i.exec(line);
    const matchGula = /gula.*?(\d+\.?\d*)\s*(g|gr|9)/i.exec(line);

    if (matchLemakTotal) nutritionalInfo['Lemak Total'] = parseFloat(matchLemakTotal[1]);
    if (matchProtein) nutritionalInfo['Protein'] = parseFloat(matchProtein[1]);
    if (matchKarbohidratTotal) nutritionalInfo['Karbohidrat Total'] = parseFloat(matchKarbohidratTotal[1]);
    if (matchGula) nutritionalInfo['Gula'] = parseFloat(matchGula[1]);
  });
  return nutritionalInfo;
};

const inferenceService = async (model, imageData, diabeticProfile) => {
  try {
    const imageTensor = tf.node.decodeImage(Buffer.from(imageData, 'base64')).resizeNearestNeighbor([224, 224]).expandDims().toFloat();

    const prediction = model.predict(imageTensor);
    const nutrition = await prediction.data();

    // Cropping the image based on model prediction (bounding boxes)
    const boxes = prediction.boxes;
    const croppedImages = [];
    for (let i = 0; i < boxes.length; i++) {
      const [x1, y1, x2, y2] = boxes[i].slice(0, 4).map((coord) => Math.floor(coord));
      const croppedImg = imageData.slice(y1, y2, x1, x2);
      croppedImages.push(croppedImg);
    }

    const ocrPromises = croppedImages.map((croppedImg) => extractTextFromImage(croppedImg));
    const ocrResults = await Promise.all(ocrPromises);

    const nutritionInfo = {};
    ocrResults.forEach((ocrResult) => {
      const parsedInfo = parseNutritionInfo(ocrResult);
      Object.assign(nutritionInfo, parsedInfo);
    });

    const { label, totalScore, sugarScore, carbScore, proteinScore, fatScore } = calculateScore(nutrition, diabeticProfile);

    let suggestion;
    switch (label) {
      case 'Aman':
        suggestion = 'Aman untuk dikonsumsi oleh semua orang, termasuk penderita diabetes.';
        break;
      case 'Risiko Rendah':
        suggestion = 'Dapat dikonsumsi oleh penderita diabetes dengan pengawasan dokter atau ahli gizi. Konsumsi secukupnya dan perhatikan batasan gula harian.';
        break;
      case 'Risiko Sedang':
        suggestion = 'Tidak direkomendasikan untuk penderita diabetes, kecuali atas konsultasi dan pengawasan ketat dari dokter atau ahli gizi.';
        break;
      case 'Risiko Tinggi':
        suggestion = 'Tidak dianjurkan untuk dikonsumsi oleh penderita diabetes karena kandungan gula yang tinggi dapat meningkatkan risiko komplikasi.';
        break;
    }

    return {
      label,
      totalScore,
      sugarScore,
      carbScore,
      proteinScore,
      fatScore,
      suggestion,
    };
  } catch (error) {
    throw new InputError(`Error in prediction: ${error.message}`);
  }
};

module.exports = { inferenceService };
