const { firestore } = require('@google-cloud/firestore');

const storeScanData = async (userId, sugarContent) => {
  const scanData = {
    userId,
    sugarContent,
    timestamp: new Date(),
  };
  await firestore.collection('scans').add(scanData);
  return scanData;
};

const getUserScanHistory = async (userId) => {
  const scans = await firestore.collection('scans')
    .where('userId', '==', userId)
    .orderBy('timestamp', 'desc')
    .get();

  return scans.docs.map(doc => doc.data());
};

module.exports = {
  storeScanData,
  getUserScanHistory,
};