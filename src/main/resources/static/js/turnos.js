document.addEventListener('DOMContentLoaded', async () => {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        window.location.href = '/login'; // Redirigir si no está autenticado
        return;
    }

    try {
        const response = await fetch('/turnos', {
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (response.ok) {
            const turnos = await response.json();
            const tableBody = document.getElementById('turnosTableBody');
            turnos.forEach(turno => {
                const row = `<tr>
                    <td>${turno.id}</td>
                    <td>${turno.paciente.nombre} ${turno.paciente.apellido}</td>
                    <td>${turno.odontologo.nombre} ${turno.odontologo.apellido}</td>
                    <td>${turno.fechaHora}</td>
                    <td><button class="btn btn-danger" onclick="eliminarTurno(${turno.id})">Eliminar</button></td>
                </tr>`;
                tableBody.innerHTML += row;
            });
        } else {
            alert('Error al cargar los turnos.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Ocurrió un error al cargar los turnos.');
    }
});

document.getElementById('turnoForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const pacienteId = document.getElementById('pacienteId').value;
    const odontologoId = document.getElementById('odontologoId').value;
    const fechaHora = document.getElementById('fechaHora').value;
    const token = localStorage.getItem('jwtToken');

    if (!token) {
        window.location.href = '/login'; // Redirigir si no está autenticado
        return;
    }

    try {
        const response = await fetch('/turnos/agregar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({ paciente: { id: pacienteId }, odontologo: { id: odontologoId }, fechaHora })
        });

        if (response.ok) {
            alert('Turno agregado exitosamente');
            location.reload(); // Recargar la página
        } else {
            alert('Error al agregar turno.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Ocurrió un error al agregar el turno.');
    }
});

async function eliminarTurno(id) {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        window.location.href = '/login'; // Redirigir si no está autenticado
        return;
    }

    try {
        const response = await fetch(`/turnos/eliminar/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (response.ok) {
            alert('Turno eliminado');
            location.reload();
        } else {
            alert('Error al eliminar turno.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Ocurrió un error al eliminar el turno.');
    }
}