const db = require('../utils/firestore');

const storeData = async (id, data) => {
  const dataRef = db.collection('data').doc();
  data.id = dataRef.id;
  await dataRef.set(data);
  return data.id;
};

const getPrediction = async (id) => {
  const dataRef = db.collection('data').doc(id);
  const snapshot = await dataRef.get();
  if (!snapshot.exists) {
    return null;
  }
  return snapshot.data();
};

module.exports = {
  storeData,
  getPrediction,
};
