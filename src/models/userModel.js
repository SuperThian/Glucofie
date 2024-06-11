const db = require('../utils/firestore');

const createUser = async (user) => {
  const userRef = db.collection('users').doc();
  user.id = userRef.id;
  await userRef.set(user);
  return user.id;
};

const getUserByEmail = async (email) => {
  const usersRef = db.collection('users');
  const snapshot = await usersRef.where('email', '==', email).get();
  if (snapshot.empty) {
    return null;
  }

  return snapshot.docs[0].data();
};

const getUserById = async (id) => {
  const userRef = db.collection('users');
  const snapshot = await userRef.where('id', '==', id).get();

  if (snapshot.empty) {
    return null;
  }

  const users = [];
  snapshot.forEach((doc) => {
    users.push({ id: doc.id, ...doc.data() });
  });
  return users;
};

const getAllUser = async () => {
  const userRef = db.collection('users');
  const snapshot = await userRef.get();

  if (snapshot.empty) {
    return [];
  }

  const users = [];
  snapshot.forEach((doc) => {
    users.push({ id: doc.id, ...doc.data() });
  });

  return users;
};

const updateUser = async (id, user) => {
  const userRef = db.collection('users').doc(id);
  await userRef.update(user);
};

module.exports = {
  createUser,
  getUserByEmail,
  getUserById,
  getAllUser,
  updateUser,
};
