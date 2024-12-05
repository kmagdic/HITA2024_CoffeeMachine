function formatDateTime(date) {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Mjeseci su 0-indeksirani
    const year = date.getFullYear();
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${day}.${month}.${year} ${hours}:${minutes}:${seconds}`;
}

document.getElementById('calculate').addEventListener('click', function () {
    const firstNumber = parseFloat(document.getElementById('first-number').value);
    const operation = document.getElementById('operation').value;
    const secondNumber = parseFloat(document.getElementById('second-number').value);
    const resultField = document.getElementById('result');

    let result;

    if (isNaN(firstNumber) || isNaN(secondNumber)) {
        alert("Please enter valid numbers!");
        return;
    }

    switch (operation) {
        case '+':
            result = firstNumber + secondNumber;
            break;
        case '-':
            result = firstNumber - secondNumber;
            break;
        case '*':
            result = firstNumber * secondNumber;
            break;
        case '/':
            if (secondNumber === 0) {
                alert("Cannot divide by zero!");
                return;
            }
            result = firstNumber / secondNumber;
            break;
        default:
            alert("Invalid operation! Use +, -, *, or /.");
            return;
    }

    resultField.value = result;
});

document.getElementById('reset').addEventListener('click', function () {
    document.getElementById('first-number').value = '';
    document.getElementById('operation').value = '';
    document.getElementById('second-number').value = '';
    document.getElementById('result').value = '';
});

function addToList(firstNumber, secondNumber, operation, result) {
    const historyList = document.getElementById('history-list');
    const newListItem = document.createElement('li');
    newListItem.textContent = `${firstNumber} ${operation} ${secondNumber} = ${result}`;
    historyList.appendChild(newListItem);
}

function addToTable(firstNumber, secondNumber, operation, result) {
    const historyTable = document.getElementById('history-table').querySelector('tbody');
    const newRow = historyTable.insertRow();

    const recordCell = newRow.insertCell(0);
    const dateCell = newRow.insertCell(1);

    recordCell.textContent = `${firstNumber} ${operation} ${secondNumber} = ${result}`;
    const now = new Date();
    dateCell.textContent = formatDateTime(now);
}

document.getElementById('add-to-list').addEventListener('click', function () {
    const firstNumber = document.getElementById('first-number').value;
    const operation = document.getElementById('operation').value;
    const secondNumber = document.getElementById('second-number').value;
    const result = document.getElementById('result').value;

    if (!firstNumber || !operation || !secondNumber || !result) {
        alert("Please calculate a result first!");
        return;
    }

    addToList(firstNumber, secondNumber, operation, result);
});

document.getElementById('add-to-table').addEventListener('click', function () {
    const firstNumber = document.getElementById('first-number').value;
    const operation = document.getElementById('operation').value;
    const secondNumber = document.getElementById('second-number').value;
    const result = document.getElementById('result').value;

    if (!firstNumber || !operation || !secondNumber || !result) {
        alert("Please calculate a result first!");
        return;
    }

    addToTable(firstNumber, secondNumber, operation, result);
});
