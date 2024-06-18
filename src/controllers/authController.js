const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const { createUser, getUserByEmail, getUserById, getAllUser, updateUser } = require('../models/userModel');
require('dotenv').config();

const register = async (req, res) => {
  const { username, email, password, confirmPassword } = req.body;

  if (password !== confirmPassword) {
    return res.status(400).json({ error: true, message: 'Passwords do not match' });
  }

  try {
    const hashedPassword = await bcrypt.hash(password, 10);

    const newUser = {
      username,
      email,
      password: hashedPassword,
      type_diabetes: null,
      gender: null,
    };

    const userId = await createUser(newUser);

    res.status(201).json({ error: false, message: 'User registered successfully' });
  } catch (error) {
    res.status(500).json({ error: true, message: 'Error registering user: ' + error.message });
  }
};

const login = async (req, res) => {
  const { email, password } = req.body;

  try {
    const user = await getUserByEmail(email);
    if (!user) {
      return res.status(400).json({ error: true, message: 'User not found' });
    }

    const isMatch = await bcrypt.compare(password, user.password);
    if (!isMatch) {
      return res.status(400).json({ error: true, message: 'Invalid credentials' });
    }

    const token = jwt.sign({ id: user.id, username: user.username, email: user.email, type_diabetes: user.type_diabetes }, process.env.JWT_SECRET, { expiresIn: '1h' });
    res.json({
      error: false,
      message: 'Login Success',
      loginResult: {
        id: user.id,
        username: user.username,
        email: user.email,
        token,
      },
    });
  } catch (error) {
    res.status(500).json({ error: true, message: 'Error logging in: ' + error.message });
  }
};

const getUser = async (req, res) => {
  const id = req.params.id;
  try {
    const user = await getUserById(id);
    if (!user) {
      return res.status(404).send('User not found');
    }
    res.json(user);
  } catch (error) {
    res.status(500).send('Error getting user: ' + error.message);
  }
};

const getAllUsers = async (req, res) => {
  try {
    const users = await getAllUser();
    res.json(users);
  } catch (error) {
    res.status(500).send('Error getting users: ' + error.message);
  }
};

const updateUserById = async (req, res) => {
  const id = req.params.id;
  const updates = req.body;
  try {
    const user = await getUserById(id);
    if (!user) {
      return res.status(404).send('User not found');
    }
    await updateUser(id, updates);
    res.json(user);
  } catch (error) {
    res.status(500).send('Error updating user: ' + error.message);
  }
};

module.exports = {
  register,
  login,
  getUser,
  getAllUsers,
  updateUserById,
};
