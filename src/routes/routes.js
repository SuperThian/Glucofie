const express = require('express');
const { scanHandler, getResult } = require('../controllers/modelController');
const { getUser, getAllUsers, updateUserById } = require('../controllers/authController');
const authMiddleware = require('../middleware/authMiddleware');
const upload = require('../services/multerService');

const router = express.Router();

router.use(authMiddleware);
router.get('/users/:id', getUser);
router.get('/users', getAllUsers);
router.patch('/users/:id', updateUserById);
router.post('/detect-nutrition', upload.single('image'), scanHandler);
router.get('/result', getResult);

module.exports = router;
