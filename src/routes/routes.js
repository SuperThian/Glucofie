const express = require('express');
const { scanHandler } = require('../controllers/modelController');
const { getUser, getAllUsers, updateUserById } = require('../controllers/authController');
const upload = require('../services/multerService');

const router = express.Router();

// router.use(authMiddleware);
router.get('/users/:id', getUser);
router.get('/users', getAllUsers);
router.patch('/users/:id', updateUserById);
// Handler untuk deteksi informasi gizi dari gambar
router.post('/detect-nutrition', upload.single('image'), scanHandler);

module.exports = router;
