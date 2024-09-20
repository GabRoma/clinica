document.getElementById('usuarioForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const nombre = document.getElementById('nombre').value;
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const rol = document.getElementById('usuarioRol').value;

    let usuarioData = {
        nombre: nombre,
        username: username,
        email: email,
        password: password,
        usuarioRol: rol
    }

    try {
        const response = await fetch('/usuarios/registrar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(usuarioData)
        });

        if (response.ok) {
            alert('Usuario registrado exitosamente');
            window.location.href = '/login'; // Redirigir al login
        } else {
            alert('Error al registrar usuario.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Ocurrió un error al registrar el usuario.');
    }
});