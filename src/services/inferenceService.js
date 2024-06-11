const tf = require("@tensorflow/tfjs-node");
const loadModel = require("./loadModel");
const InputError = require("../exceptions/InputError");

const inferenceService = async (model, imageData, diabeticProfile) => {
  try {
    const imageTensor = tf.node
      .decodeImage(Buffer.from(imageData, "base64"))
      .resizeNearestNeighbor([224, 224])
      .expandDims()
      .toFloat();

    const prediction = model.predict(imageTensor);
    const score = await prediction.data();
    const sugarContent = score[0];

    let label;
    if (diabeticProfile === "non-diabetic") {
      label = sugarContent < 30 ? "Good" : "Not Good";
    } else if (
      diabeticProfile === "diabetic-1" ||
      diabeticProfile === "diabetic-2" ||
      diabeticProfile === "diabetic-gestasional"
    ) {
      label = sugarContent < 15 ? "Good" : "Not Good";
    } else {
      throw new InputError(`Invalid diabetic profile`);
    }

    return { label, sugarContent };
  } catch (error) {
    throw new InputError(`Error in prediction: ${error.message}`);
  }
};

module.exports = {
  loadModel,
  inferenceService,
};
