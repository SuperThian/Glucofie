const tf = require('@tensorflow/tfjs-node');

const loadModel = async () => {
  const bucketName = 'nama bucket';
  const modelPath = 'nama model';
  const storage = new Storage();

  const bucket = storage.bucket(bucketName);
  const file = bucket.file(modelPath);
  const fileStream = file.createReadStream();
  
  return new Promise((resolve, reject) => {
    const model = tf.loadLayersModel(tf.io.stream(fileStream));
    model.then(resolve).catch(reject);
  });
};

module.exports = loadModel;