// odontologos.js

async function loadOdontologos() {
    const response = await fetch('/odontologos', {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('jwt') }
    });
    const odontologos = await response.json();

    const tableBody = document.getElementById('odontologosTableBody');
    tableBody.innerHTML = '';

    odontologos.forEach(odontologo => {
        const row = `
      <tr>
        <td>${odontologo.id}</td>
        <td>${odontologo.nombre}</td>
        <td>${odontologo.apellido}</td>
        <td>${odontologo.matricula}</td>
        <td>
          <button class="btn btn-warning" onclick="editOdontologo(${odontologo.id})">Editar</button>
          <button class="btn btn-danger" onclick="deleteOdontologo(${odontologo.id})">Eliminar</button>
        </td>
      </tr>
    `;
        tableBody.innerHTML += row;
    });
}

async function addOdontologo(e) {
    e.preventDefault();
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;
    const matricula = document.getElementById('matricula').value;

    await fetch('/odontologos/agregar', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('jwt'),
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ nombre, apellido, matricula })
    });

    loadOdontologos();
}

document.getElementById('odontologoForm')?.addEventListener('submit', addOdontologo);
