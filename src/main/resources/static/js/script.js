// ==========================================================================================
// Bienvenido al JS! En la creación de este script se derramaron mares de lágrimas :D
// ==========================================================================================
document.addEventListener("DOMContentLoaded", function () {
    const vista = document.body.getAttribute('data-vista');

    if (vista === 'odontologos') {
        cargarOdontologos();
        document.getElementById('odontologoForm').addEventListener('submit', agregarOdontologo);
    } else if (vista === 'pacientes') {
        cargarPacientes();
        document.getElementById('pacienteForm').addEventListener('submit', agregarPaciente);
    } else if (vista === 'turnos') {
        cargarTurnos();
        cargarPacientesEnSelect();
        cargarOdontologosEnSelect();
        document.getElementById('turnoForm').addEventListener('submit', asignarTurno);
    }
});

// =============================================
// Cargar Odontólogos
// =============================================
function cargarOdontologos() {
    fetch('/odontologos')
        .then(response => response.json())
        .then(data => {
            let tbody = document.getElementById('odontologosTableBody');
            tbody.innerHTML = '';
            data.forEach(odontologo => {
                let row = `<tr>
                        <td>${odontologo.id}</td>
                        <td>${odontologo.nombre}</td>
                        <td>${odontologo.apellido}</td>
                        <td>${odontologo.matricula}</td>
                        <td><button class="btn btn-danger btn-sm" onclick="eliminarOdontologo(${odontologo.id})">Eliminar</button></td>
                       </tr>`;
                tbody.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al cargar odontólogos:', error));
}

// =============================================
// Agregar Odontólogo
// =============================================
function agregarOdontologo(e) {
    e.preventDefault();

    let nombre = document.getElementById('nombre').value;
    let apellido = document.getElementById('apellido').value;
    let matricula = document.getElementById('matricula').value;

    let odontologoData = {
        nombre: nombre,
        apellido: apellido,
        matricula: matricula
    };

    fetch('/odontologos/agregar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(odontologoData)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error al agregar odontólogo');
            }
        })
        .then(data => {
            alert('Odontólogo agregado exitosamente');
            document.getElementById('odontologoForm').reset();
            cargarOdontologos();
            const modal = bootstrap.Modal.getInstance(document.getElementById('odontologoModal'));
            modal.hide();
            location.reload();
        })
        .catch(error => console.error('Error al agregar odontólogo:', error));
}

// =============================================
// Eliminar Odontólogo
// =============================================
function eliminarOdontologo(id) {
    if (confirm('¿Estás seguro de que quieres eliminar este odontólogo?')) {
        fetch(`/odontologos/eliminar/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    alert('Odontólogo eliminado exitosamente');
                    cargarOdontologos();
                } else {
                    throw new Error('Error al eliminar odontólogo');
                }
            })
            .catch(error => console.error('Error al eliminar odontólogo:', error));
    }
}

// =============================================
// Cargar Pacientes
// =============================================
function cargarPacientes() {
    fetch('/pacientes')
        .then(response => response.json())
        .then(data => {
            let tbody = document.getElementById('pacientesTableBody');
            tbody.innerHTML = '';
            data.forEach(paciente => {
                let row = `<tr>
                        <td>${paciente.id}</td>
                        <td>${paciente.nombre}</td>
                        <td>${paciente.apellido}</td>
                        <td>${paciente.cedula}</td>
                        <td><button class="btn btn-danger btn-sm" onclick="eliminarPaciente(${paciente.id})">Eliminar</button></td>
                       </tr>`;
                tbody.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al cargar pacientes:', error));
}

// =============================================
// Agregar Paciente
// =============================================
function agregarPaciente(e) {
    e.preventDefault();

    let nombre = document.getElementById('nombre').value;
    let apellido = document.getElementById('apellido').value;
    let cedula = document.getElementById('cedula').value;
    let email = document.getElementById('email').value;
    let fechaIngreso = document.getElementById('fechaIngreso').value;
    let calle = document.getElementById('calle').value;
    let numero = document.getElementById('numero').value;
    let localidad = document.getElementById('localidad').value;
    let provincia = document.getElementById('provincia').value;

    let pacienteData = {
        nombre: nombre,
        apellido: apellido,
        cedula: cedula,
        email: email,
        fechaIngreso: fechaIngreso,
        domicilio: {
            calle: calle,
            numero: numero,
            localidad: localidad,
            provincia: provincia
        }
    };

    fetch('/pacientes/agregar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pacienteData)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error al agregar paciente');
            }
        })
        .then(data => {
            alert('Paciente agregado exitosamente');
            document.getElementById('pacienteForm').reset();
            cargarPacientes();
            const modal = bootstrap.Modal.getInstance(document.getElementById('pacienteModal'));
            modal.hide();
            location.reload();
        })
        .catch(error => console.error('Error al agregar paciente:', error));
}

// =============================================
// Eliminar Paciente
// =============================================
function eliminarPaciente(id) {
    if (confirm('¿Estás seguro de que quieres eliminar este paciente?')) {
        fetch(`/pacientes/eliminar/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    alert('Paciente eliminado exitosamente');
                    cargarPacientes();  // Recargar la lista de pacientes
                } else {
                    throw new Error('Error al eliminar paciente');
                }
            })
            .catch(error => console.error('Error al eliminar paciente:', error));
    }
}

// =============================================
// Cargar Turnos
// =============================================

function cargarPacientesEnSelect() {
    fetch('/pacientes')
        .then(response => response.json())
        .then(data => {
            let pacienteSelect = document.getElementById('pacienteId');
            data.forEach(paciente => {
                let option = document.createElement('option');
                option.value = paciente.id;
                option.text = `${paciente.nombre} ${paciente.apellido}`;
                pacienteSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error al cargar pacientes:', error));
}

function cargarOdontologosEnSelect() {
    fetch('/odontologos')
        .then(response => response.json())
        .then(data => {
            let odontologoSelect = document.getElementById('odontologoId');
            data.forEach(odontologo => {
                let option = document.createElement('option');
                option.value = odontologo.id;
                option.text = `${odontologo.nombre} ${odontologo.apellido}`;
                odontologoSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error al cargar odontólogos:', error));
}

function cargarTurnos() {
    fetch('/turnos')
        .then(response => response.json())
        .then(data => {
            let tbody = document.getElementById('turnosTableBody');
            tbody.innerHTML = '';
            data.forEach(turno => {
                let row = `<tr>
                        <td>${turno.id}</td>
                        <td>${turno.paciente.nombre} ${turno.paciente.apellido}</td>
                        <td>${turno.odontologo.nombre} ${turno.odontologo.apellido}</td>
                        <td>${new Date(turno.fechaHora).toLocaleString()}</td>
                        <td><button class="btn btn-danger btn-sm" onclick="eliminarTurno(${turno.id})">Eliminar</button></td>
                       </tr>`;
                tbody.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al cargar turnos:', error));
}

// =============================================
// Asignar Turno
// =============================================
function asignarTurno(e) {
    e.preventDefault();

    let pacienteId = document.getElementById('pacienteId').value;
    let odontologoId = document.getElementById('odontologoId').value;
    let fechaHora = document.getElementById('fechaHora').value;

    let turnoData = {
        fechaHora: fechaHora,
        paciente: { id: pacienteId },
        odontologo: { id: odontologoId }
    };

    fetch('/turnos/agregar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(turnoData)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error al asignar turno');
            }
        })
        .then(data => {
            alert('Turno asignado exitosamente');
            document.getElementById('turnoForm').reset();
            cargarTurnos();
            location.reload();
        })
        .catch(error => console.error('Error al asignar turno:', error));
}

// =============================================
// Eliminar Turno
// =============================================
function eliminarTurno(id) {
    if (confirm('¿Estás seguro de que quieres eliminar este turno?')) {
        fetch(`/turnos/eliminar/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    alert('Turno eliminado exitosamente');
                    cargarTurnos();
                } else {
                    throw new Error('Error al eliminar turno');
                }
            })
            .catch(error => console.error('Error al eliminar turno:', error));
    }
}

// =============================================
// Registrar Usuario
// =============================================
document.getElementById('usuarioForm').addEventListener('submit', function(e) {
    e.preventDefault();

    let nombre = document.getElementById('nombre').value;
    let username = document.getElementById('username').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;
    let usuarioRol = document.getElementById('usuarioRol').value;

    let usuarioData = {
        nombre: nombre,
        userName: username,
        email: email,
        password: password,
        usuarioRol: usuarioRol
    };

    fetch('/usuarios/agregar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuarioData)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error al registrar usuario');
            }
        })
        .then(data => {
            alert('Usuario registrado exitosamente');
            document.getElementById('usuarioForm').reset();
        })
        .catch(error => console.error('Error al registrar usuario:', error));
});

