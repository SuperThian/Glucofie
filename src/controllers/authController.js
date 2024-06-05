const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const { createUser, getUserByEmail } = require('../models/userModel');
require('dotenv').config();

const register = async (req, res) => {
  const { email, password } = req.body;

  try {
    const hashedPassword = await bcrypt.hash(password, 10);
    const userId = await createUser({ email, password: hashedPassword });
    res.status(201).json({ id: userId, message: 'User registered successfully' });
  } catch (error) {
    res.status(500).send('Error registering user: ' + error.message);
  }
};

const login = async (req, res) => {
  const { email, password } = req.body;

  try {
    const user = await getUserByEmail(email);
    if (!user) {
      return res.status(400).send('User not found');
    }

    const isMatch = await bcrypt.compare(password, user.password);
    if (!isMatch) {
      return res.status(400).send('Invalid credentials');
    }

    const token = jwt.sign({ id: user.id, email: user.email }, process.env.JWT_SECRET, { expiresIn: '1h' });
    res.json({ id: user.id, token });
  } catch (error) {
    res.status(500).send('Error logging in: ' + error.message);
  }
};

module.exports = {
  register,
  login,
};
