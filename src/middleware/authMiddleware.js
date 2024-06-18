const jwt = require('jsonwebtoken');
require('dotenv').config();

const authMiddleware = (req, res, next) => {
  const authHeader = req.header('Authorization');

  if (!authHeader) {
    return res.status(401).send('Access denied');
  }

  const token = authHeader.split(' ')[1]; // Memisahkan 'Bearer' dari token

  if (!token) {
    return res.status(401).send('Access denied');
  }

  try {
    const decoded = jwt.verify(token, process.env.JWT_SECRET);
    req.user = decoded; // Simpan payload token dalam req.user
    next();
  } catch (error) {
    res.status(400).send('Invalid token');
  }
};

module.exports = authMiddleware;
