// Login function
document.getElementById('loginForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const response = await fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username: email, password })
    })
        .then(response => response.json())
        .then(data => {
            localStorage.setItem("token", data.jwt); // Almacenar el token
            // Redirigir al home usando fetchWithAuth
            navigateWithAuth('/home');
        })
        .catch(error => {
            console.error('Error en la autenticación:', error);
        });
});

// Función para obtener el token JWT almacenado
function getToken() {
    return localStorage.getItem("token");
}

// Función fetch personalizada que agrega automáticamente el token JWT en el encabezado de autorización
async function fetchWithAuth(url, options = {}) {
    const token = getToken();
    const headers = options.headers ? options.headers : {};
    if (token) {
        headers['Authorization'] = 'Bearer ' + token;
    }
    options.headers = headers;
    return await fetch(url, options);
}

// Función para manejar la navegación asegurando que el token JWT esté presente
async function navigateWithAuth(url) {
    fetchWithAuth(url, {
        method: 'GET'
    })
        .then(response => {
            if (response.ok) {
                return response.text(); // O JSON si tu backend responde con datos
            } else if (response.status === 403) {
                throw new Error('Acceso denegado: No tienes permiso para esta ruta.');
            }
        })
        .then(htmlContent => {
            document.body.innerHTML = htmlContent; // Renderizar la nueva vista
        })
        .catch(error => {
            console.error('Error en la navegación:', error);
        });
}

// Asignar la función de navegación a los botones del navbar
document.querySelectorAll('.navbar-link').forEach(button => {
    button.addEventListener('click', (e) => {
        e.preventDefault();
        const url = e.target.getAttribute('href'); // Obtener la URL del enlace
        navigateWithAuth(url);
    });
});

// Manejar cambio de URL manual en el navegador
window.addEventListener('popstate', (event) => {
    const url = window.location.pathname; // Obtener la URL actual
    navigateWithAuth(url); // Navegar usando la función con autorización
});

// Logout function
function logout() {
    localStorage.removeItem('token');
    window.location.href = '/login';
}


