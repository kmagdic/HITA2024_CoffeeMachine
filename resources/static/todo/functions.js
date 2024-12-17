function loginProcess() {
    var email = document.getElementById("email").value;
    var pass = document.getElementById("pass").value;

    if (email=="admin@todo.com" && pass=="admin") {
        window.open("employees_view_supervisor.html", "_blank")
    }
    else if (email=="user@todo.com" && pass=="user") {
             window.open("add_todo_employee.html", "_blank")
        }
    else alert("Wrong username or password")
}

// ne radi više - korištena je bila kad je add todo bio u istom htmlu kao i tablica svih todoova
/*function addTodoToTable() {

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

    //upiši nove podatke u te ćelije
    let todo = prompt("Enter todo:", todoCell.innerHTML);
    let note = prompt("Enter note:", noteCell.innerHTML);
    let date = prompt("Enter date:", dateCell.innerHTML);

    //spremi novoupisane podatke u ćelije
    todoCell.innerHTML = todo;
    noteCell.innerHTML = note;
    dateCell.innerHTML = date;
}

function deleteRow(button) {
    let row = button.parentNode.parentNode;
    //document.getElementById("todoTable").deleteRow(row);
    row.parentNode.removeChild(row);
}

function addNewTodo() {
    let todo = document.getElementById("todo").value;
    let note = document.getElementById("note").value;
    let date = document.getElementById("date").value;

    var url = 'employee_todos.html?todo=' + encodeURIComponent(todo) + '&note=' + encodeURIComponent(note) + '&date=' + encodeURIComponent(date);

    window.location.href = url;
}

function addEmployee(){

    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    var table = document.getElementById("employeesTable");
    var newRow = table.insertRow();

    var rowIndex = table.rows.length;

    var cell1 = newRow.insertCell(0);
    var cell2 = newRow.insertCell(1);
    var cell3 = newRow.insertCell(2);
    var cell4 = newRow.insertCell(3);
    var cell5 = newRow.insertCell(4);
    var cell6 = newRow.insertCell(5);
    var cell7 = newRow.insertCell(6);
    var cell8 = newRow.insertCell(7);
    cell1.innerHTML = rowIndex;
    cell2.innerHTML = firstname;
    cell3.innerHTML = lastname;
    cell4.innerHTML = email;
    cell5.innerHTML = password;
    cell6.innerHTML =  '<button type="button" class="btn btn-link" onclick="editRow(this)">Edit</button>';
    cell7.innerHTML =  '<button type="button" class="btn btn-link" onclick="deleteRow(this)">Delete</button>';
    cell8.innerHTML =  '<button type="button" class="btn btn-link" onclick="openNewPage()">View</button>';


     // Clear input fields
     clearInputs();
}

    function clearInputs() {

        // Clear input fields
        document.getElementById("firstname").value = "";
        document.getElementById("lastname").value = "";
        document.getElementById("email").value = "";
        document.getElementById("password").value = "";
    }

    function openNewPage() {
        window.location.href = 'employee_todos_supervisor.html';
    }
