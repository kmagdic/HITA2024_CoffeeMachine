function addToTable() {

    var date = document.getElementById("date").value;
    var sisTlak = document.getElementById("sisTlak").value;
    var dijTlak = document.getElementById("dijTlak").value;
    var otkucaji = document.getElementById("otkucaji").value;
    var opis = document.getElementById("opis").value;
    var opcije = document.getElementById("opcije").value;


    var table = document.getElementById("popisTable");
    var newRow = table.insertRow();
    var cell1 = newRow.insertCell(0);
    var cell2 = newRow.insertCell(1);
    var cell3 = newRow.insertCell(2);
    var cell4 = newRow.insertCell(3);
    var cell5 = newRow.insertCell(4);
    var cell6 = newRow.insertCell(5);


    cell1.innerHTML = date;
    cell2.innerHTML = sisTlak;
    cell3.innerHTML = dijTlak;
    cell4.innerHTML = otkucaji;
    cell5.innerHTML = opis;
    cell6.innerHTML = opcije;

}

function editRow(button) {
        const row = button.parentElement.parentElement;
        const cells = row.getElementsByTagName("td");
        const sisTlak = cells[1].innerText;
        const dijTlak = cells[2].innerText;
        const otkucaji = cells[3].innerText;
        const opis = cells[4].innerText;

        const newSisTlak = prompt("Izmijeni sistolički tlak:", sisTlak);
        const newDijTlak = prompt("Izmijeni dijastolički tlak:", dijTlak);
        const newOtkucaji = prompt("Izmijeni otkucaje srca:", otkucaji);
        const newOpis = prompt("Izmijeni opis:", opis);

        if (newSisTlak !== null) cells[1].innerText = newSisTlak;
        if (newDijTlak !== null) cells[2].innerText = newDijTlak;
        if (newOtkucaji !== null) cells[3].innerText = newOtkucaji;
        if (newOpis !== null) cells[4].innerText = newOpis;
    }

function deleteRow(button) {
    const row = button.parentElement.parentElement;
    row.remove();
}

