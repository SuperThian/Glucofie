const express = require('express');
const { scanHandler, historyHandler } = require('./handler');

const router = express.Router();

router.post('/scan', scanHandler);
router.get('/history/:userId', historyHandler);

module.exports = router;
