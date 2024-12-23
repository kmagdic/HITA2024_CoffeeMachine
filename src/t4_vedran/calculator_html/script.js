document.getElementById('calculate').addEventListener('click', () => {
    const numberA = parseFloat(document.getElementById('numberA').value);
    const operation = document.getElementById('operation').value.trim();
    const numberB = parseFloat(document.getElementById('numberB').value);
    let result;

    switch (operation) {
        case '+':
            result = numberA + numberB;
            break;
        case '-':
            result = numberA - numberB;
            break;
        case '*':
            result = numberA * numberB;
            break;
        case '/':
            result = numberA / numberB;
            break;
        case 'pow':
            result = Math.pow(numberA, numberB);
            break;
        default:
            result = 'Invalid Operation';
    }

    document.getElementById('result').value = result;
});

document.getElementById('reset').addEventListener('click', () => {
    document.getElementById('numberA').value = '';
    document.getElementById('operation').value = '';
    document.getElementById('numberB').value = '';
    document.getElementById('result').value = '';
});

document.getElementById('addToHistory').addEventListener('click', () => {
    const history = document.getElementById('history');
    const numberA = document.getElementById('numberA').value;
    const operation = document.getElementById('operation').value;
    const numberB = document.getElementById('numberB').value;
    const result = document.getElementById('result').value;

    if (result !== '' && !isNaN(result)) {
        const historyItem = document.createElement('li');
        historyItem.textContent = `${numberA} ${operation} ${numberB} = ${result}`;
        history.appendChild(historyItem);
    }
});

