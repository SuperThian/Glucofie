const tf = require('@tensorflow/tfjs-node');
const fs = require('fs');
const path = require('path');
const extractTextFromImage = require('../services/ocrServices');
const { cleaned_string, resultData, nutritionClassification } = require('../services/dataService');
const { storeData, getResultByUserId } = require('../models/dataModel');
const { error } = require('console');

const scanHandler = async (req, res) => {
  try {
    const userId = req.user;

    // Pastikan ada file yang di-upload
    if (!req.file) {
      return res.status(400).json({ error: 'No file uploaded' });
    }

    // Mengambil data gambar dari req.file.buffer (disediakan oleh multer)
    const imageData = req.file.buffer;

    // Decode image menggunakan TensorFlow.js
    const tensorImage = tf.node.decodeImage(imageData);

    // Normalisasi ukuran gambar ke [800, 800]
    const resizedImage = tf.image.resizeNearestNeighbor(tensorImage, [800, 800]);

    // Menambahkan batch dimension [1] ke tensor
    const batchedImage = resizedImage.expandDims().toFloat();

    // Lakukan deteksi bounding box dengan menggunakan model ML (contoh pseudocode)
    const model = req.app.locals.model;
    const boundingBoxesTensor = model.predict(batchedImage);
    const boundingBoxesData = await boundingBoxesTensor.data();

    // Simpan resizeImage sebagai file sementara (misalnya JPEG)
    const tempDir = path.join(__dirname, '../temp'); // Direktori tempat penyimpanan sementara
    const tempFilePath = path.join(tempDir, 'image.jpeg'); // Path lengkap file sementara

    // Mengecek apakah direktori tempat penyimpanan sudah ada, jika belum maka dibuat
    if (!fs.existsSync(tempDir)) {
      fs.mkdirSync(tempDir, { recursive: true });
    }

    // Konversi tensor resizedImage ke format JPEG
    const jpegBuffer = await tf.node.encodeJpeg(resizedImage);

    // Menulis file JPEG ke tempFilePath
    fs.writeFileSync(tempFilePath, jpegBuffer);

    // Gunakan tesserct js untuk mendeteksi nutrisi
    const text = await extractTextFromImage(tempFilePath);

    // Hapus file sementara
    fs.unlinkSync(tempFilePath);

    // Membersihkan teks
    const cleanedText = cleaned_string(text);

    // result
    const result = resultData(cleanedText);

    // nutrition klasifikasi
    const resultNutrition = nutritionClassification(result);

    // store data
    const dataScan = await storeData(result, userId, resultNutrition);

    // Kirim Response text
    res.status(200).json({
      error: false,
      message: 'Scan Success',
    });
  } catch (error) {
    console.error('Error detecting nutrition:', error);
    res.status(500).json({ error: 'Failed to detect nutrition information' });
  }
};

const getResult = async (req, res) => {
  const userId = req.user.id;
  try {
    const data = await getResultByUserId(userId);
    if (!data) {
      return res.status(404).send('Data not found');
    }
    res.json(data);
  } catch (error) {
    res.status(500).send('Error getting data: ' + error.message);
  }
};

module.exports = {
  scanHandler,
  getResult,
};
