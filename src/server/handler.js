const { inferenceService, loadModel } = require('../services/inferenceService');
const { storeScanData, getUserScanHistory } = require('../services/storeData');
const InputError = require('../exceptions/InputError');

let model;

loadModel().then(loadedModel => {
  model = loadedModel;
  console.log('Model loaded successfully');
}).catch(err => {
  console.error('Error loading model', err);
});

const interpretScore = (score) => {
  if (score >= 0 && score <= 3) {
    return 'Aman';
  } else if (score >= 4 && score <= 6) {
    return 'Risiko Rendah';
  } else if (score >= 7 && score <= 9) {
    return 'Risiko Sedang';
  } else if (score >= 10 && score <= 12) {
    return 'Risiko Tinggi';
  } else {
    throw new InputError('Invalid score range');
  }
};

const scanHandler = async (req, res) => {
  try {
    const { imageData, userId } = req.body;
    if (!imageData || !userId) throw new InputError('Missing required fields');

    const sugarContent = await inferenceService(model, imageData);
    const riskClassification = interpretScore(sugarContent);
    const scanData = await storeScanData(userId, sugarContent);

    res.status(200).json({
      sugarContent,
      riskClassification,
      scanData,
    });
  } catch (error) {
    handleError(res, error);
  }
};

const historyHandler = async (req, res) => {
  try {
    const { userId } = req.params;
    if (!userId) throw new InputError('Missing userId parameter');

    const scanHistory = await getUserScanHistory(userId);
    res.status(200).json(scanHistory);
  } catch (error) {
    handleError(res, error);
  }
};

const handleError = (res, error) => {
  if (error instanceof ClientError) {
    res.status(error.statusCode).send(error.message);
  } else {
    console.error('Internal server error', error);
    res.status(500).send('Internal Server Error');
  }
};

module.exports = {
  scanHandler,
  historyHandler,
};
