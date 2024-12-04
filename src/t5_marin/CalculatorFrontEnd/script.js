document.getElementById('calculate-btn').addEventListener('click', function () {
  const firstNumber = parseFloat(document.getElementById('first-number').value);
  const operation = document.getElementById('operation').value;
  const secondNumber = parseFloat(document.getElementById('second-number').value);
  const resultField = document.getElementById('result');

  let result;
  if (operation === '+') {
    result = firstNumber + secondNumber;
  } else if (operation === '-') {
    result = firstNumber - secondNumber;
  } else if (operation === '*') {
    result = firstNumber * secondNumber;
  } else if (operation === '/') {
    result = secondNumber !== 0 ? firstNumber / secondNumber : 'Error';
  } else {
    result = 'Invalid operation';
  }

  resultField.value = result;
});

document.getElementById('reset-btn').addEventListener('click', function () {
  document.getElementById('calculator-form').reset();
  document.getElementById('result').value = '';
});

document.getElementById('add-to-list').addEventListener('click', function () {
  const firstNumber = parseFloat(document.getElementById('first-number').value);
  const operation = document.getElementById('operation').value;
  const secondNumber = parseFloat(document.getElementById('second-number').value);
  const resultField = document.getElementById('result');
  const enteredResult = parseFloat(resultField.value);

  // Recalculate the result for validation
  let calculatedResult;
  if (operation === '+') {
    calculatedResult = firstNumber + secondNumber;
  } else if (operation === '-') {
    calculatedResult = firstNumber - secondNumber;
  } else if (operation === '*') {
    calculatedResult = firstNumber * secondNumber;
  } else if (operation === '/') {
    calculatedResult = secondNumber !== 0 ? firstNumber / secondNumber : 'Error';
  }

  // Compare entered result with the recalculated result
  if (calculatedResult !== enteredResult) {
    resultField.value = calculatedResult; // Correct the result in the result field
    alert(`The result was incorrect. It has been corrected to ${calculatedResult}`);
  }

  // Only add valid results to the history
  if (!isNaN(calculatedResult)) {
    const history = document.getElementById('history');

    // Create the history entry text
    const entryText = `${firstNumber} ${operation} ${secondNumber} = ${calculatedResult}`;

    // Check for duplicate entries in the history
    const isDuplicate = Array.from(history.children).some(
      (item) => item.textContent === entryText
    );

    if (!isDuplicate) {
      const historyItem = document.createElement('li');
      historyItem.textContent = entryText;
      history.appendChild(historyItem);
    }
  }
});
