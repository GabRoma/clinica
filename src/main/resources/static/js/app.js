document.addEventListener('DOMContentLoaded', () => {
    const vista = document.body.getAttribute('data-vista');

    if (vista === 'odontologos') {
        loadOdontologos();
    } else if (vista === 'pacientes') {
        loadPacientes();
    } else if (vista === 'turnos') {
        loadTurnos();
    }

    // Evento para cerrar sesión
    const logoutBtn = document.querySelector('button[onclick="logout()"]');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => logout());
    }
});
