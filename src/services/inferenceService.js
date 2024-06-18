const InputError = require("../exceptions/InputError");
const firestore = require("../utils/firestore");

const calculateScore = (nutrition) => {
  const {
    "Lemak Total": fat,
    Protein: protein,
    "Karbohidrat Total": carbs,
    Gula: sugar,
  } = nutrition;

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
    label = "Aman";
  } else if (totalScore <= 6) {
    label = "Risiko Rendah";
  } else if (totalScore <= 9) {
    label = "Risiko Sedang";
  } else {
    label = "Risiko Tinggi";
  }

  return { label, totalScore, sugarScore, carbScore, proteinScore, fatScore };
};

const inferenceService = async (ocrText, userId, diabeticProfile) => {
  try {
    const nutrition = {
      "Lemak Total": ocrText["Lemak Total"],
      Protein: ocrText["Protein"],
      "Karbohidrat Total": ocrText["Karbohidrat Total"],
      Gula: ocrText["Gula"],
    };

    const { label, totalScore, sugarScore, carbScore, proteinScore, fatScore } =
      calculateScore(nutrition);

    let suggestion;
    switch (label) {
      case "Aman":
        suggestion =
          "Aman untuk dikonsumsi oleh semua orang, termasuk penderita diabetes.";
        break;
      case "Risiko Rendah":
        suggestion =
          "Dapat dikonsumsi oleh penderita diabetes dengan pengawasan dokter atau ahli gizi. Konsumsi secukupnya dan perhatikan batasan gula harian.";
        break;
      case "Risiko Sedang":
        suggestion =
          "Tidak direkomendasikan untuk penderita diabetes, kecuali atas konsultasi dan pengawasan ketat dari dokter atau ahli gizi.";
        break;
      case "Risiko Tinggi":
        suggestion =
          "Tidak dianjurkan untuk dikonsumsi oleh penderita diabetes karena kandungan gula yang tinggi dapat meningkatkan risiko komplikasi.";
        break;
    }

    // Store the result in Firestore
    await firestore.collection("nutritionData").add({
      userId,
      nutrition,
      label,
      totalScore,
      sugarScore,
      carbScore,
      proteinScore,
      fatScore,
      suggestion,
      diabeticProfile,
      timestamp: new Date(),
    });

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
    throw new InputError(`Error in processing: ${error.message}`);
  }
};

module.exports = {
  inferenceService,
};
