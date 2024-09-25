import { logout } from './auth.js';

document.addEventListener('DOMContentLoaded', () => {
    const vista = document.body.getAttribute('data-vista');

    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', logout);
    }

    const url = new URL(window.location.href);
    const protectedRoutes = ['/home', '/view/odontologos', '/view/pacientes', '/view/turnos'];

    if (protectedRoutes.includes(url.pathname)) {
        loadProtectedView(url.pathname)
            .then(() => console.log('Protected view loaded successfully'))
            .catch(error => console.error('Error loading protected view:', error));
    }
});

function getToken() {
    return localStorage.getItem("jwt"); // También podrías usar sessionStorage
}

// Función fetch personalizada que agrega automáticamente el token JWT en el encabezado de autorización
async function fetchWithAuth(url, options = {}) {
    const token = getToken(); // Obtener el token almacenado
    console.log('Token JWT almacenado: ', token);

    // Si ya existen encabezados en la solicitud, conservarlos; si no, crear un nuevo objeto de encabezados
    const headers = options.headers ? options.headers : {};

    // Agregar el token JWT al encabezado Authorization, si existe
    if (token) {
        headers['Authorization'] = 'Bearer ' + token;
        console.log('Token enviado en el encabezado: ', headers['Authorization']);
    }

    // Agregar los encabezados actualizados a las opciones de la solicitud
    options.headers = headers;

    // Hacer la solicitud utilizando fetch y devolver la respuesta
    return await fetch(url, options);
}

// Función para cargar vistas protegidas
async function loadProtectedView(url) {
    try {
        const response = await fetchWithAuth(url, { method: 'GET' });

        if (response.ok) {
            const htmlContent = await response.html; // O cualquier tipo de respuesta que esperes (JSON, HTML, etc.)
            document.body.innerHTML = htmlContent;
        } else if (response.status === 403) {
            alert('Acceso denegado: No tienes permiso para esta ruta. Redirigiendo a la página de inicio de sesión.');
            window.location.href = '/login';
            throw new Error('Acceso denegado: No tienes permiso para esta ruta.');
        }
    } catch (error) {
        console.error('Error en la solicitud: ', error);
    }
}

// Detectar cambios en la URL
window.addEventListener('popstate', (event) => {
    const url = new URL(window.location.href);
    const protectedRoutes = ['/home', '/view/odontologos', '/view/pacientes', '/view/turnos'];

    if (protectedRoutes.includes(url.pathname)) {
        loadProtectedView(url.pathname)
            .then(() => console.log('Protected view loaded successfully'))
            .catch(error => console.error('Error loading protected view:', error));
    }
});