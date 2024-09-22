// turnos.js

async function loadTurnos() {
    const response = await fetch('/turnos', {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('jwt') }
    });
    const turnos = await response.json();

    const tableBody = document.getElementById('turnosTableBody');
    tableBody.innerHTML = '';

    turnos.forEach(turno => {
        const row = `
      <tr>
        <td>${turno.id}</td>
        <td>${turno.paciente.nombre} ${turno.paciente.apellido}</td>
        <td>${turno.odontologo.nombre} ${turno.odontologo.apellido}</td>
        <td>${turno.fechaHora}</td>
        <td>
          <button class="btn btn-danger" onclick="deleteTurno(${turno.id})">Eliminar</button>
        </td>
      </tr>
    `;
        tableBody.innerHTML += row;
    });
}

async function addTurno(e) {
    e.preventDefault();
    const pacienteId = document.getElementById('pacienteId').value;
    const odontologoId = document.getElementById('odontologoId').value;
    const fechaHora = document.getElementById('fechaHora').value;

    await fetch('/turnos/agregar', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('jwt'),
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ paciente: { id: pacienteId }, odontologo: { id: odontologoId }, fechaHora })
    });

    loadTurnos();
}

document.getElementById('turnoForm')?.addEventListener('submit', addTurno);
