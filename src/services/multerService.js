// multerService.js
const multer = require('multer');

// Konfigurasi multer
const storage = multer.memoryStorage(); // Menggunakan memory storage
const limits = {
  fileSize: 5 * 1024 * 1024, // Batas ukuran file (5 MB)
};
const fileFilter = (req, file, cb) => {
  if (file.mimetype.startsWith('image/')) {
    cb(null, true);
  } else {
    cb(new Error('File type not supported'), false);
  }
};

const upload = multer({ storage, limits, fileFilter });

module.exports = upload;
