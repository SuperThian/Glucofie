const InputError = require('../exceptions/InputError');

const cleaned_string = (string) => {
  let pattern = /[\|\*\_\'\—\-\{}"]/g;
  let text = string.replace(pattern, '');
  text = text.replace(/ I /g, ' / ');
  text = text.replace(/^I /g, '');
  text = text.replace(/%/g, '');
  text = text.replace(/gy/g, '');
  text = text.replace(/Ng/g, '');
  text = text.replace(/oy/g, '');
  text = text.replace(/°/g, 'g');
  text = text.replace(/Protein\./g, 'Protein');
  text = text.replace(/Sugar/g, 'gula');
  text = text.replace(/Omg/g, '0mg');
  text = text.replace(/Og/g, '0g');
  text = text.replace(/(\d) (?=\w)/g, '$1');
  text = changeToG(text); // Assuming changeToG is another function you define elsewhere
  text = text.trim();
  return text;
};

const changeToG = (text) => {
  let searchLn = text.match(/(\d)\s|\d$/);
  if (searchLn && searchLn[1] === '9') {
    let index = searchLn.index;
    text = text.substring(0, index) + 'g' + text.substring(index + 1);
  }

  // Search for 'mq' that is followed by a space or at the end of the string
  let searchLnq = text.match(/(\d)mq\s|\d$/);
  if (searchLnq) {
    let index = searchLnq.index + 1;
    text = text.substring(0, index) + 'g' + text.substring(index + 1);
  }

  return text;
};

const resultData = (cleanedText) => {
  let nutritional_info = {};
  let lines = cleanedText.split('\n');

  lines.forEach((line) => {
    let match_lemak_total = line.match(/lemak total.*?(\d+\.?\d*)\s*(g|gr|9)/i);
    let match_protein = line.match(/protein\s*(\d+\.?\d*)\s*(g|gr|9)/i);
    let match_karbohidrat_total = line.match(/karbohidrat total.*?(\d+\.?\d*)\s*(g|gr|9)/i);
    let match_gula = line.match(/gula.*?(\d+\.?\d*)\s*(g|gr|9)/i);

    if (match_lemak_total) {
      nutritional_info['Lemak Total'] = parseFloat(match_lemak_total[1]);
    } else if (match_protein) {
      nutritional_info['Protein'] = parseFloat(match_protein[1]);
    } else if (match_karbohidrat_total) {
      nutritional_info['Karbohidrat Total'] = parseFloat(match_karbohidrat_total[1]);
    } else if (match_gula) {
      nutritional_info['Gula'] = parseFloat(match_gula[1]);
    }
  });

  return nutritional_info;
};

const nutritionClassification = (result_nutrition) => {
  const classifyNutrient = (value, thresholds) => {
    for (let i = thresholds.length - 1; i >= 0; i--) {
      if (value > thresholds[i].max) return thresholds[i + 1].score;
      if (value >= thresholds[i].min) return thresholds[i].score;
    }
    return thresholds[0].score;
  };

  const thresholds = {
    Gula: [
      { score: 0, min: 0, max: 0.99 },
      { score: 1, min: 1, max: 5 },
      { score: 2, min: 5.01, max: 10 },
      { score: 3, min: 10.01, max: Infinity },
    ],
    Protein: [
      { score: 0, min: 0, max: 9.99 },
      { score: 1, min: 10, max: 20 },
      { score: 2, min: 20.01, max: Infinity },
    ],
    'Lemak Total': [
      { score: 0, min: 0, max: 2.99 },
      { score: 1, min: 3, max: 10 },
      { score: 2, min: 10.01, max: Infinity },
    ],
    'Karbohidrat Total': [
      { score: 0, min: 0, max: 24.99 },
      { score: 1, min: 25, max: 50 },
      { score: 2, min: 50.01, max: Infinity },
    ],
  };

  const scores = {
    Gula: classifyNutrient(result_nutrition['Gula'], thresholds['Gula']),
    Protein: classifyNutrient(result_nutrition['Protein'], thresholds['Protein']),
    'Lemak Total': classifyNutrient(result_nutrition['Lemak Total'], thresholds['Lemak Total']),
    'Karbohidrat Total': classifyNutrient(result_nutrition['Karbohidrat Total'], thresholds['Karbohidrat Total']),
  };

  const totalScore = scores['Gula'] + scores['Protein'] + scores['Lemak Total'] + scores['Karbohidrat Total'];

  let interpretation;

  if (totalScore >= 0 && totalScore <= 3) {
    interpretation = 'Aman untuk dikonsumsi oleh semua orang, termasuk penderita diabetes.';
  } else if (totalScore >= 4 && totalScore <= 6) {
    interpretation = 'Risiko Rendah: Dapat dikonsumsi oleh penderita diabetes dengan pengawasan dokter atau ahli gizi. Konsumsi secukupnya dan perhatikan batasan gula harian.';
  } else if (totalScore >= 7 && totalScore <= 9) {
    interpretation = 'Risiko Sedang: Tidak direkomendasikan untuk penderita diabetes, kecuali atas konsultasi dan pengawasan ketat dari dokter atau ahli gizi.';
  } else if (totalScore >= 10 && totalScore <= 12) {
    interpretation = 'Risiko Tinggi: Tidak dianjurkan untuk dikonsumsi oleh penderita diabetes karena kandungan gula yang tinggi dapat meningkatkan risiko komplikasi.';
  } else {
    interpretation = 'Invalid score';
  }

  return interpretation;
};

module.exports = {
  cleaned_string,
  resultData,
  nutritionClassification,
};
