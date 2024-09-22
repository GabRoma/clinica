// pacientes.js

async function loadPacientes() {
    const response = await fetch('/pacientes', {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('jwt') }
    });
    const pacientes = await response.json();

    const tableBody = document.getElementById('pacientesTableBody');
    tableBody.innerHTML = '';

    pacientes.forEach(paciente => {
        const row = `
      <tr>
        <td>${paciente.id}</td>
        <td>${paciente.nombre}</td>
        <td>${paciente.apellido}</td>
        <td>${paciente.cedula}</td>
        <td>
          <button class="btn btn-warning" onclick="editPaciente(${paciente.id})">Editar</button>
          <button class="btn btn-danger" onclick="deletePaciente(${paciente.id})">Eliminar</button>
        </td>
      </tr>
    `;
        tableBody.innerHTML += row;
    });
}

async function addPaciente(e) {
    e.preventDefault();
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;
    const cedula = document.getElementById('cedula').value;
    const email = document.getElementById('email').value;
    const fechaIngreso = document.getElementById('fechaIngreso').value;
    const domicilio = {
        calle: document.getElementById('calle').value,
        numero: document.getElementById('numero').value,
        localidad: document.getElementById('localidad').value,
        provincia: document.getElementById('provincia').value,
    };

    await fetch('/pacientes/agregar', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('jwt'),
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ nombre, apellido, cedula, email, fechaIngreso, domicilio })
    });

    loadPacientes();
}

document.getElementById('pacienteForm')?.addEventListener('submit', addPaciente);
