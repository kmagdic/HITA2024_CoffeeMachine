function loginProcess() {
    var email = document.getElementById("email").value;
    var pass = document.getElementById("pass").value;

    if (email=="user@todo.com" && pass=="user") {
        window.open("employee_todos.html", "_blank")
    }
    else alert("Wrong username or password")
}

/*
function addTodoToTable() {

    var todo = document.getElementById("todo").value;
    var note = document.getElementById("note").value;
    var date = document.getElementById("date").value;

    var table = document.getElementById("todoTable");
    var newRow = table.insertRow();
    var cell1 = newRow.insertCell(0);
    var cell2 = newRow.insertCell(1);
    var cell3 = newRow.insertCell(2);

    cell1.innerHTML = todo;
    cell2.innerHTML = note;
    cell3.innerHTML = date;

}*/

function editRow(button) {
    let row = button.parentNode.parentNode;

    // dohvati ćelije
    let todoCell = row.cells[0];
    let noteCell = row.cells[1];
    let dateCell = row.cells[2];

    // upiši nove vrijednosti u te ćelije
    let todo = prompt("Enter todo:", todoCell.innerHTML);
    let note = prompt("Enter note:", noteCell.innerHTML);
    let date = prompt("Enter date:", dateCell.innerHTML);

    // spremi novo upisane podatke u ćelije
    todoCell.innerHTML = todo;
    noteCell.innerHTML = note;
    dateCell.innerHTML = date;

}

function deleteRow(button) {
    let row = button.parentNode.parentNode;
    //document.getElementById("todoTable").deleteRow(row);
    row.parentNode.removeChild(row);
}

function saveTodo() {
    let todo = document.getElementById("todo").value;
    let note = document.getElementById("note").value;
    let date = document.getElementById("date").value;

    var url = 'employee_todos.html?todo=' + encodeURIComponent(todo) + '&note=' + encodeURIComponent(note) + '&date=' + encodeURIComponent(date);

    window.location.href = url;
}
