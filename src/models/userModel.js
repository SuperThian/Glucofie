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

module.exports = {
  createUser,
  getUserByEmail,
};
