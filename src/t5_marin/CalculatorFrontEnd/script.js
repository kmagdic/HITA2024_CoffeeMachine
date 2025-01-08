// Calculate result based on operation
function calculateResult(firstNumber, operation, secondNumber) {
  switch (operation) {
    case '+':
      return firstNumber + secondNumber;
    case '-':
      return firstNumber - secondNumber;
    case '*':
      return firstNumber * secondNumber;
    case '/':
      return secondNumber !== 0 ? firstNumber / secondNumber : 'Error';
    default:
      return 'Invalid operation';
  }
}

// Validate and update result field
function validateAndUpdateResult(firstNumber, operation, secondNumber, resultField) {
  const calculatedResult = calculateResult(firstNumber, operation, secondNumber);
  const enteredResult = parseFloat(resultField.value);

  if (calculatedResult !== enteredResult) {
    resultField.value = calculatedResult; // Correct the result
    alert(`The result was incorrect. It has been corrected to ${calculatedResult}`);
  }

  return calculatedResult;
}

// Add calculation to history
function addToHistory(firstNumber, operation, secondNumber, calculatedResult) {
  if (!isNaN(calculatedResult)) {
    const historyTable = document.getElementById('history-table').querySelector('tbody');
    const calculationText = `${firstNumber} ${operation} ${secondNumber} = ${calculatedResult}`;
    const timestamp = new Date().toLocaleString();

    // Check for duplicates
    const isDuplicate = Array.from(historyTable.rows).some(
      (row) => row.cells[0].textContent === calculationText
    );

    if (!isDuplicate) {
      const newRow = historyTable.insertRow();
      const calcCell = newRow.insertCell(0);
      const timeCell = newRow.insertCell(1);

      calcCell.textContent = calculationText;
      timeCell.textContent = timestamp;
    }
  }
}

// Event listener for calculate button
document.getElementById('calculate-btn').addEventListener('click', function () {
  const firstNumber = parseFloat(document.getElementById('first-number').value);
  const operation = document.getElementById('operation').value;
  const secondNumber = parseFloat(document.getElementById('second-number').value);
  const resultField = document.getElementById('result');

  const result = calculateResult(firstNumber, operation, secondNumber);
  resultField.value = result;
});

// Event listener for reset button
document.getElementById('reset-btn').addEventListener('click', function () {
  document.getElementById('calculator-form').reset();
  document.getElementById('result').value = '';
});

// Event listener for add-to-list button
document.getElementById('add-to-list').addEventListener('click', function () {
  const firstNumber = parseFloat(document.getElementById('first-number').value);
  const operation = document.getElementById('operation').value;
  const secondNumber = parseFloat(document.getElementById('second-number').value);
  const resultField = document.getElementById('result');

  const calculatedResult = validateAndUpdateResult(firstNumber, operation, secondNumber, resultField);
  addToHistory(firstNumber, operation, secondNumber, calculatedResult);
});
