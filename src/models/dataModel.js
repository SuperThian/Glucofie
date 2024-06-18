const db = require('../utils/firestore');

const storeData = async (result, userId, suggestion) => {
  const data = {
    result,
    suggestion,
    userId,
    timestamp: new Date(),
  };
  const dataRef = db.collection('data').doc();
  data.id = dataRef.id;
  await dataRef.set(data);
  return data;
};

getResultByUserId = async (userId) => {
  const dataRef = db.collection('data').where('userId.id', '==', userId);
  const snapshot = await dataRef.get();
  if (snapshot.empty) {
    return null;
  }
  const data = [];
  snapshot.forEach((doc) => {
    data.push({ id: doc.id, ...doc.data() });
  });
  return data;
};

module.exports = {
  storeData,
  getResultByUserId,
};
