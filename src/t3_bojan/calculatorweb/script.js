document.addEventListener("DOMContentLoaded", () => {
    const numberA = document.getElementById("numberA");
    const numberB = document.getElementById("numberB");
    const operation = document.getElementById("operation");
    const resultDisplay = document.getElementById("result");
    const calculateButton = document.getElementById("calculate");
    const toggleHistoryButton = document.getElementById("toggleHistory");
    const historyPanel = document.getElementById("historyPanel");
    const historyList = document.getElementById("historyList");

    // Show/Hide History Panel
    toggleHistoryButton.addEventListener("click", () => {
        historyPanel.classList.toggle("visible");
    });

    let previousResult = null;

    // Calculate and display result
    calculateButton.addEventListener("click", () => {
        let a = parseFloat(numberA.value);
        const b = parseFloat(numberB.value);
        const op = operation.value;
        let result;

        if (previousResult !== null) {
            a = previousResult;
        }

        if (isNaN(a) || isNaN(b)) {
            result = "Error";
        } else {
            switch (op) {
                case "+":
                    result = a + b;
                    break;
                case "-":
                    result = a - b;
                    break;
                case "*":
                    result = a * b;
                    break;
                case "/":
                    result = b !== 0 ? a / b : "Error";
                    break;
                default:
                    result = "Error";
            }
        }
    
        if (result !== "Error") {
            addToHistory(a, op, b, result);
            previousResult = result;  
        }
    
        resultDisplay.textContent = result.toString();
        numberA.value = a.toString(); // update the value of numberA with the current value of a
    });

    // Add calculation to history
    function addToHistory(a, op, b, result) {
        const timestamp = new Date().toLocaleString();
        const historyEntry = document.createElement("li");
        historyEntry.textContent = `${timestamp}: ${a} ${op} ${b} = ${result}`;
        historyList.appendChild(historyEntry);
    }
});
