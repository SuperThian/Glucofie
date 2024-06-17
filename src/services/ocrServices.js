const tesseract = require('tesseract.js');

const extractTextFromImage = async (image) => {
  // Perform OCR using Tesseract.js
  const {
    data: { text },
  } = await tesseract.recognize(image, 'eng');

  return text;
};

module.exports = extractTextFromImage;
