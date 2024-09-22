// Registro de usuarios
document.getElementById('usuarioForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const nombre = document.getElementById('nombre').value;
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const usuarioRol = document.getElementById('usuarioRol').value;

    const response = await fetch('/usuarios/registrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            nombre,
            username,
            email,
            password,
            usuarioRol
        })
    });

    if (response.ok) {
        alert('Usuario registrado correctamente');
        window.location.href = '/login'; // Redirigir al login después de registrarse
    } else if (response.status === 400) {
        alert('Error en los datos ingresados. Verifica el formulario.');
    } else {
        alert('Error en el servidor. Inténtalo más tarde.');
    }
});
