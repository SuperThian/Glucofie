const sharp = require('sharp');
const tesseract = require('tesseract.js');

// const preprocessImage = async (imagePath) => {
//   // Read and preprocess the image using sharp
//   const processedImageBuffer = await sharp(imagePath)
//     .grayscale() // Convert to grayscale
//     .threshold(150) // Apply thresholding
//     .toBuffer(); // Convert to buffer

//   return processedImageBuffer;
// };

const extractTextFromImage = async (imagePath) => {
  // // Preprocess the image
  // const processedImageBuffer = await preprocessImage(imagePath);

  // Perform OCR using Tesseract.js
  const {
    data: { text },
  } = await tesseract.recognize(imagePath, 'eng');

  return text;
};

module.exports = extractTextFromImage;
