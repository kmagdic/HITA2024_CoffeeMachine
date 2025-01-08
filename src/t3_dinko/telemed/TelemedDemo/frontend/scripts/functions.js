 let loginType = 'patient';
    let healthData = {};
    let patients = [
        { id: 1, name: "Iva Ivić", email: "iva.ivic@gmail.com" },
        { id: 2, name: "Marko Markić", email: "marko.markic@gmail.com" },
        { id: 3, name: "Ivan Horvat", email: "ivan.horvat@gmail.com" }
    ];

    function setLoginType(type) {
        loginType = type;
        document.querySelectorAll('.login-option').forEach(option => {
            option.classList.remove('active');
        });
        event.target.classList.add('active');
    }

    function login() {
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        if (email && password) {
            if (loginType === 'doctor') {
                showDoctorDashboard();
            } else {
                showDashboard();
            }
        } else {
            alert('Please enter both email and password');
        }
    }

    function showDashboard() {
        hideAllScreens();
        document.getElementById('dashboardScreen').style.display = 'block';
        displayRecentRecords();
    }

    function showDoctorDashboard() {
        hideAllScreens();
        document.getElementById('doctorScreen').style.display = 'block';
        displayPatientList();
    }

    function showHealthMetrics() {
        hideAllScreens();
        document.getElementById('metricsScreen').style.display = 'block';
    }

    function showSuccess() {
        hideAllScreens();
        document.getElementById('successScreen').style.display = 'block';
    }

    function showPreviousData() {
        hideAllScreens();
        document.getElementById('previousDataScreen').style.display = 'block';
        displayHealthData();
    }

    function showAddPatientForm() {
        hideAllScreens();
        document.getElementById('addPatientScreen').style.display = 'block';
    }

    function hideAllScreens() {
        const screens = ['loginScreen', 'dashboardScreen', 'metricsScreen', 'successScreen', 'doctorScreen', 'previousDataScreen', 'addPatientScreen', 'patientRecordsScreen'];
        screens.forEach(screen => {
            document.getElementById(screen).style.display = 'none';
        });
    }

    function deletePatient(index) {
        if (confirm('Are you sure you want to delete this patient?')) {
            patients.splice(index, 1);
            displayPatientList();
        }
    }

    function updatePatient(index) {
        const patient = patients[index];
        const name = prompt('Update patient name:', patient.name);
        const email = prompt('Update patient email:', patient.email);

        if (name && email) {
            patients[index] = { ...patient, name, email };
            displayPatientList();
        }
    }

    function saveHealthData() {
        const heartRate = document.getElementById('heartRate').value;
        const bloodPressure = document.getElementById('bloodPressure').value;
        const bloodSugar = document.getElementById('bloodSugar').value;
        const date = new Date().toLocaleString();

        if (!healthData[loginType]) {
            healthData[loginType] = [];
        }
        healthData[loginType].push({ date, heartRate, bloodPressure, bloodSugar });
        showSuccess();
        displayRecentRecords();
    }

    function displayHealthData() {
        const tableBody = document.getElementById('healthDataBody');
        tableBody.innerHTML = '';
        if (healthData[loginType]) {
            healthData[loginType].forEach(data => {
                const row = tableBody.insertRow();
                row.insertCell(0).textContent = data.date;
                row.insertCell(1).textContent = data.heartRate;
                row.insertCell(2).textContent = data.bloodPressure;
                row.insertCell(3).textContent = data.bloodSugar;
            });
        }
    }

    function displayPatientList() {
        const patientList = document.getElementById('patientList');
        patientList.innerHTML = '';
        patients.forEach((patient, index) => {
            const patientCard = document.createElement('div');
            patientCard.className = 'patient-card';
            patientCard.innerHTML = `
                <img src="https://i.pravatar.cc/150?img=${patient.id}" alt="${patient.name}">
                <div class="patient-info">
                    <div class="patient-name">${patient.name}</div>
                    <div class="patient-appointment">${patient.email}</div>
                </div>
                <div class="dropdown">
                    <button class="button button-secondary">...</button>
                    <div class="dropdown-content">
                        <a href="#" onclick="deletePatient(${index})">Delete Patient</a>
                        <a href="#" onclick="updatePatient(${index})">Update Patient</a>
                    </div>
                </div>
            `;
            patientCard.onclick = () => showPatientRecords(patient);
            patientList.appendChild(patientCard);
        });
    }

    function addNewPatient() {
        const name = document.getElementById('newPatientName').value;
        const email = document.getElementById('newPatientEmail').value;
        if (name && email) {
            const newId = patients.length + 1;
            patients.push({ id: newId, name, email });
            showDoctorDashboard();
        } else {
            alert('Please enter both name and email');
        }
    }

    function showPatientRecords(patient) {
        hideAllScreens();
        document.getElementById('patientRecordsScreen').style.display = 'block';
        document.getElementById('patientRecordsName').textContent = patient.name;
        const tableBody = document.getElementById('patientHealthDataBody');
        tableBody.innerHTML = '';
        if (healthData[patient.email]) {
            healthData[patient.email].forEach(data => {
                const row = tableBody.insertRow();
                row.insertCell(0).textContent = data.date;
                row.insertCell(1).textContent = data.heartRate;
                row.insertCell(2).textContent = data.bloodPressure;
                row.insertCell(3).textContent = data.bloodSugar;
            });
        } else {
            tableBody.innerHTML = '<tr><td colspan="4">No health data available for this patient.</td></tr>';
        }
    }

    function quickAddHeartRate() {
        const heartRate = prompt("Enter your current heart rate (bpm):");
        if (heartRate) {
            const date = new Date().toLocaleString();
            if (!healthData[loginType]) {
                healthData[loginType] = [];
            }
            healthData[loginType].push({ date, heartRate, bloodPressure: '-', bloodSugar: '-' });
            displayRecentRecords();
            showDashboard();
        }
    }

    function quickAddBloodPressure() {
        const bloodPressure = prompt("Enter your current blood pressure (e.g., 120/80):");
        if (bloodPressure) {
            const date = new Date().toLocaleString();
            if (!healthData[loginType]) {
                healthData[loginType] = [];
            }
            healthData[loginType].push({ date, heartRate: '-', bloodPressure, bloodSugar: '-' });
            displayRecentRecords();
            showDashboard();
        }
    }

    function logout() {
        hideAllScreens();
        document.getElementById('loginScreen').style.display = 'block';
    }

    function displayRecentRecords() {
        const tableBody = document.getElementById('recentRecordsBody');
        tableBody.innerHTML = '';
        if (healthData[loginType]) {
            // Display only the last 3 records
            const recentData = healthData[loginType].slice(-3).reverse();
            recentData.forEach(data => {
                const row = tableBody.insertRow();
                row.insertCell(0).textContent = data.date;
                row.insertCell(1).textContent = data.heartRate || '-';
                row.insertCell(2).textContent = data.bloodPressure || '-';
                row.insertCell(3).textContent = data.bloodSugar || '-';
            });
        }
    }

    // Initialize the app
    document.addEventListener('DOMContentLoaded', () => {
        hideAllScreens();
        document.getElementById('loginScreen').style.display = 'block';
    });