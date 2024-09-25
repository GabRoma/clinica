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

        if (localStorage.getItem('jwt')) {
            console.log('Token existente encontrado. Eliminando...');
            localStorage.removeItem('jwt');
        }

        localStorage.setItem('jwt', jwt);
        console.log('Nuevo token guardado: ' + jwt);

        alert('Bienvenido!');

        // Redirigir a la vista HOME
        window.location.href = '/home';

    } else {
        alert('Error al iniciar sesión');
    }
});