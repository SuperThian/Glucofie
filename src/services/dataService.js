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

module.exports = {
  cleaned_string,
  resultData,
};
