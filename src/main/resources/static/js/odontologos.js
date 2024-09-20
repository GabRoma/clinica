document.addEventListener('DOMContentLoaded', async () => {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        window.location.href = '/login'; // Redirigir si no está autenticado
    }

    try {
        const response = await fetch('/odontologos', {
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (response.ok) {
            const odontologos = await response.json();
            const tableBody = document.getElementById('odontologosTableBody');
            odontologos.forEach(odontologo => {
                const row = `<tr>
                    <td>${odontologo.id}</td>
                    <td>${odontologo.nombre}</td>
                    <td>${odontologo.apellido}</td>
                    <td>${odontologo.matricula}</td>
                    <td><button class="btn btn-danger" onclick="eliminarOdontologo(${odontologo.id})">Eliminar</button></td>
                </tr>`;
                tableBody.innerHTML += row;
            });
        } else {
            alert('Error al cargar los odontólogos.');
        }
    } catch (error) {
        console.error('Error:', error);
    }
});

document.getElementById('odontologoForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;
    const matricula = document.getElementById('matricula').value;
    const token = localStorage.getItem('jwtToken');

    try {
        const response = await fetch('/odontologos/agregar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({ nombre, apellido, matricula })
        });

        if (response.ok) {
            alert('Odontólogo agregado exitosamente');
            location.reload(); // Recargar la página
        } else {
            alert('Error al agregar odontólogo.');
        }
    } catch (error) {
        console.error('Error:', error);
    }
});

async function eliminarOdontologo(id) {
    const token = localStorage.getItem('jwtToken');
    try {
        const response = await fetch(`/odontologos/eliminar/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (response.ok) {
            alert('Odontólogo eliminado');
            location.reload();
        } else {
            alert('Error al eliminar odontólogo.');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}
