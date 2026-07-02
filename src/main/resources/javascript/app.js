const API_URL = "http://localhost:8080/api/students";
const API_AUTH = "http://localhost:8080/api/auth";

document.addEventListener("DOMContentLoaded", getAllStudents);

//bootstrap modal for showing shtuff
function showModal({ title, body, isConfirm = false, onConfirm = null }) {
    const modalEl = document.getElementById('systemModal');
    const modalInstance = bootstrap.Modal.getOrCreateInstance(modalEl);
    
    document.getElementById('systemModalLabel').textContent = title;
    document.getElementById('systemModalBody').textContent = body;
    
    const confirmBtn = document.getElementById('modalConfirmBtn');
    const cancelBtn = document.getElementById('modalCancelBtn');
    
    if (isConfirm) {
        confirmBtn.classList.remove('d-none');
        cancelBtn.textContent = "Cancel";
        
        const newConfirmBtn = confirmBtn.cloneNode(true);
        confirmBtn.parentNode.replaceChild(newConfirmBtn, confirmBtn);
        
        newConfirmBtn.addEventListener('click', () => {
            modalInstance.hide();
            if (onConfirm) onConfirm();
        });
    } else {
        confirmBtn.classList.add('d-none');
        cancelBtn.textContent = "Close";
    }
    
    modalInstance.show();
}

async function getAllStudents() {
    try {
        const response = await fetch(API_URL, { credentials: "include" });
        if (!response.ok) throw new Error("Network response was not ok");

        const students = await response.json();
        const tableBody = document.getElementById("studentTableBody");
        tableBody.innerHTML = "";

        students.forEach(student => {
            const row = `
                <tr>
                    <td>${student.id}</td>
                    <td>${student.firstName}</td>
                    <td>${student.lastName}</td>
                    <td>${student.email}</td>
                    <td>
                        <button class="custom-btn btn-edit" onclick="initializeStudentFormById(${studsent.id})">Edit</button>
                        <button class="custom-btn btn-delete" onclick="deleteStudent(${student.id})">Delete</button>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    } catch (error) {
        console.error("Error fetching students:", error);
        showModal({
            title: "Connection Failed",
            body: "Failed to fetch students. Is your Spring Boot backend running or are you logged out?"
        });
    }
}

async function createStudent(studentData) {
    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(studentData),
            credentials: "include"
        });

        if (response.ok) {
            showModal({ title: "Success", body: "Student added successfully!" });
            resetForm();
        } else {
            showModal({ title: "Error", body: "Error adding student." });
        }
    } catch (error) {
        console.error("Error creating student:", error);
    }
    getAllStudents();
}

async function initializeStudentFormById(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`, { credentials: "include" });
        if (!response.ok) throw new Error("Could not fetch student details");

        const student = await response.json();

        document.getElementById("studentId").value = student.id;
        document.getElementById("firstName").value = student.firstName;
        document.getElementById("lastName").value = student.lastName;
        document.getElementById("email").value = student.email;

        const submitBtn = document.getElementById("submitBtn");
        submitBtn.textContent = "Update";
        submitBtn.style.backgroundColor = "#007bff";

        if(typeof clearValidationStyles === "function") clearValidationStyles();
    } catch (error) {
        console.error("Error fetching student details:", error);
    }
}

async function updateStudent(id, studentData) {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(studentData),
            credentials: "include"
        });

        if (response.ok) {
            showModal({ title: "Success", body: "Student updated successfully!" });
            resetForm();
            getAllStudents();
        } else {
            showModal({ title: "Error", body: "Error updating student." });
        }
    } catch (error) {
        console.error("Error updating student:", error);
    }
}

async function deleteStudent(id) {
    showModal({
        title: "Confirm Deletion",
        body: "Are you sure you want to delete this student?",
        isConfirm: true,
        onConfirm: async () => {
            try {
                const response = await fetch(`${API_URL}/${id}`, {
                    method: "DELETE",
                    credentials: "include"
                });

                if (response.ok) {
                    const message = await response.text();
                    showModal({ title: "Deleted", body: message || "Student deleted successfully!" });
                    getAllStudents();
                } else {
                    showModal({ title: "Error", body: "Error deleting student." });
                }
            } catch (error) {
                console.error("Error deleting student:", error);
            }
        }
    });
}

async function loginUser(loginDto) {
    try {
        const response = await fetch(`${API_AUTH}/auth_login_session`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(loginDto),
            credentials: "include"
        });

        if (response.ok) {
            showModal({ title: "Welcome", body: "Login successful!" });
            setTimeout(() => { window.location.href = "index.html"; }, 1500);
        } else {
            showModal({ title: "Failed", body: "Invalid credentials." });
        }
    } catch (error) {
        console.error("Login error:", error);
    }
}

async function logoutUser() {
    try {
        await fetch(`${API_AUTH}/logout`, {
            method: "POST",
            credentials: "include"
        });
        document.getElementById("studentTableBody").innerHTML = "";
        resetForm();

        showModal({ title: "Goodbye", body: "Logged out successfully!" });

        try {
            await fetch(API_URL, {
                method: "GET",
                headers: {
                    "Authorization": "Basic " + btoa("invalid_user:wrong_password")
                }
            });
        } catch (err) {
            console.log("Browser credential cache cleared.");
        }
        
        setTimeout(() => { window.location.reload(); }, 1500);

    } catch (e) {
        console.error("Logout transmission crash:", e);
    }
}

document.getElementById("studentForm").addEventListener("submit", function(e) {
    e.preventDefault();

    if(typeof validateStudentForm === "function" && !validateStudentForm()) {
        return;
    }

    const id = document.getElementById("studentId").value;
    const studentData = {
        firstName: document.getElementById("firstName").value.trim(),
        lastName: document.getElementById("lastName").value.trim(),
        email: document.getElementById("email").value.trim()
    };

    if(id){
        updateStudent(id, studentData);
    } else {
        createStudent(studentData);
    }
});

function resetForm(){
    document.getElementById("studentId").value = "";
    document.getElementById("studentForm").reset();

    const submitBtn = document.getElementById("submitBtn");
    submitBtn.textContent = "Add Student";
    submitBtn.style.backgroundColor = "#28a745";
}