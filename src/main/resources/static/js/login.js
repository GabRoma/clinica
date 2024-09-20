document.getElementById('loginForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    let loginData = {
        username : email,
        password : password
    }

    let existingToken = localStorage.getItem('jwtToken');


    try {
        const response = await fetch('/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(loginData)
        });

        if (response.ok) {
            const data = await response.json();
            if (existingToken) {
                console.log("Eliminando el JWT existente.");
                localStorage.removeItem('jwtToken');
            }
            localStorage.setItem('jwtToken', data.jwt); // Almacenar el token JWT
            console.log("JWT Token: ", data.jwt);
            alert('Login exitoso');
            window.location.href = '/home'; // Redirigir al home
        } else {
            alert('Error al iniciar sesión. Verifica tus credenciales.');
        }
    } catch (error) {
        console.error('Error:', error);
    }

    // fetch('/login', {
    //     method: 'POST',
    //     headers: {
    //         'Content-Type': 'application/json'
    //     },
    //     body: JSON.stringify(loginData)
    // })
    //     .then(response => {
    //         if (response.ok) {
    //             return response.json();
    //         } else {
    //             throw new Error('Login failed');
    //         }
    //     })
    //     .then(data => {
    //         localStorage.setItem('jwtToken', data.jwt);
    //         console.log("JWT Token: ", data.jwt);
    //         alert("Bienvenido");
    //         window.location.href = '/home';
    //     })
    //     .catch(error => {
    //         document.getElementById('errorMessage').textContent = "Email o contraseña incorrectos";
    //         console.error('Error al iniciar sesión:', error);
    //     })

});
