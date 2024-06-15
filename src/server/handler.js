const { inferenceService, loadModel } = require('../services/inferenceService');
const InputError = require('../exceptions/InputError');
const crypto = require('crypto');

const scanHandler = async (req, res) => {
  try {
    const image = req;
    const model = req.app.locals.model;
    if (!image) {
      return res.status(400).json({
        status: 'fail',
        message: 'No image provided',
      });
    }

    // const { label, totalScore, sugarScore, carbScore, proteinScore, fatScore, suggestion } = await inferenceService(model, imageData, diabeticProfile);
    const id = crypto.randomUUID();
    const createdAt = new Date().toISOString();

    const scanData = {
      id,
      userId,
      result: label,
      totalScore,
      sugarScore,
      carbScore,
      proteinScore,
      fatScore,
      suggestion,
      createdAt,
      diabeticProfile,
    };

    await storeData(id, scanData);

    res.status(200).json({
      status: 'success',
      message: 'Nutrition facts analyzed successfully',
      data: scanData,
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
    const predictions = await getPredictionsFromFirestore();

    const data = predictions.map((prediction) => ({
      id: prediction.id,
      history: prediction,
    }));

    res.status(200).json({
      status: 'success',
      scanHistory,
      predictionHistories: data,
    });
  } catch (error) {
    handleError(res, error);
  }
};

const handleError = (res, error) => {
  if (error instanceof InputError) {
    res.status(400).send(error.message);
  } else {
    console.error('Internal server error', error);
    res.status(500).send('Internal Server Error');
  }
};

module.exports = {
  scanHandler,
  historyHandler,
};
