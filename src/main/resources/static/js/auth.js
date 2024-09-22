// auth.js

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
    });

    if (response.ok) {
        const { jwt } = await response.json();

        // Check if a token already exists and remove it
        if (localStorage.getItem('jwt')) {
            console.log('Token existente encontrado. Eliminando...');
            localStorage.removeItem('jwt');
        }

        // Save the new token
        localStorage.setItem('jwt', jwt);
        console.log('Nuevo token guardado: ' + jwt);

        alert('Bienvenido!');
        window.location.href = '/home';
    } else {
        alert('Error al iniciar sesión');
    }
});

// Function to make authenticated requests
async function fetchWithAuth(url, options = {}) {
    const token = localStorage.getItem('jwt');
    if (token) {
        options.headers = {
            ...options.headers,
            'Authorization': 'Bearer ' + token
        };
    }
    const response = await fetch(url, options);
    return response;
}

// Example usage of fetchWithAuth
fetchWithAuth('/home')
    .then(response => {
        if (response.ok) {
            console.log('Request successful');
        } else {
            console.log('Request failed');
        }
    });

// Logout function
function logout() {
    localStorage.removeItem('jwt');
    window.location.href = '/login';
}