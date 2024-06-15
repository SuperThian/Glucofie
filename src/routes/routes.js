const express = require('express');
const { scanHandler, historyHandler } = require('../server/handler');
// const authMiddleware = require('../middleware/authMiddleware');
const { getUser, getAllUsers, updateUserById } = require('../controllers/authController');
const router = express.Router();

// router.use(authMiddleware);
router.get('/users/:id', getUser);
router.get('/users', getAllUsers);
router.patch('/users/:id', updateUserById);
router.post('/scan', scanHandler);
router.get('/history/:userId', historyHandler);

module.exports = router;
