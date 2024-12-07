function loginProcess() {
    var email = document.getElementById("email").value;
    var pass = document.getElementById("pass").value;

    if (email=="admin@todo.com" && pass=="admin") {
        window.open("employee_todos.html", "_blank")
    }
    else alert("Wrong username or password")
}

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

}