const tf = require('@tensorflow/tfjs-node');
const loadModel = require('./loadModel');

const inferenceService = async (model, imageData) => {
  const imageTensor = tf.node.decodeImage(Buffer.from(imageData, 'base64'));
  const prediction = model.predict(imageTensor.expandDims(0));
  return prediction.dataSync()[0];
};

module.exports = {
  loadModel,
  inferenceService,
};