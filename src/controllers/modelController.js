const tf = require("@tensorflow/tfjs-node");
const fs = require("fs");
const path = require("path");
const extractTextFromImage = require("../services/ocrServices");
const { cleaned_string, resultData } = require("../services/dataService");

const scanHandler = async (req, res) => {
  try {
    const { image } = req.body;

    // Pastikan ada file yang di-upload
    if (!req.file) {
      return res.status(400).json({ error: "No file uploaded" });
    }

    // Mengambil data gambar dari req.file.buffer (disediakan oleh multer)
    const imageData = req.file.buffer;

    // Decode image menggunakan TensorFlow.js
    const tensorImage = tf.node.decodeImage(imageData);

    // Normalisasi ukuran gambar ke [800, 800]
    const resizedImage = tf.image.resizeNearestNeighbor(
      tensorImage,
      [800, 800]
    );

    // Menambahkan batch dimension [1] ke tensor
    const batchedImage = resizedImage.expandDims().toFloat();

    // Lakukan deteksi bounding box dengan menggunakan model ML (contoh pseudocode)
    const model = req.app.locals.model;
    const boundingBoxesTensor = model.predict(batchedImage);
    const boundingBoxesData = await boundingBoxesTensor.data();

    // Simpan resizeImage sebagai file sementara (misalnya JPEG)
    const tempDir = path.join(__dirname, "../temp"); // Direktori tempat penyimpanan sementara
    const tempFilePath = path.join(tempDir, "image.jpeg"); // Path lengkap file sementara

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

    // Kirim Response text
    res.status(200).json({ OcrText: result, text: text });
  } catch (error) {
    console.error("Error detecting nutrition:", error);
    res.status(500).json({ error: "Failed to detect nutrition information" });
  }
};

//Fungsi analisa gambar berdasarkan userid
const analyzeImage = async (req, res) => {
  try {
    const { ocrText, userId, diabeticProfile } = req.body;
    if (!ocrText || !userId || !diabeticProfile) {
      throw new InputError(
        "Missing required fields: ocrText, userId, diabeticProfile"
      );
    }

    const result = await inferenceService.inferenceService(
      ocrText,
      userId,
      diabeticProfile
    );
    res.status(200).json(result);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

module.exports = {
  scanHandler,
  analyzeImage, //export modul analyzeImage
};
