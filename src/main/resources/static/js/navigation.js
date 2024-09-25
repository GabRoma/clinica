// // Función para obtener el token JWT del localStorage
// function getToken() {
//     return localStorage.getItem("jwt"); // O sessionStorage si prefieres
// }
//
// // Función para verificar si el token JWT ha expirado
// function isTokenExpired(token) {
//     const payloadBase64 = token.split('.')[1];
//     const payload = JSON.parse(atob(payloadBase64));
//     const expiration = payload.exp;
//
//     // Verificar si el token ha expirado
//     return (Date.now() >= expiration * 1000);
// }
//
// // Función principal fetchWithAuth para todas las solicitudes GET
// async function fetchWithAuth(url) {
//     const token = getToken(); // Obtener el token JWT almacenado
//
//     // Verificar si el token existe y si no ha expirado
//     if (!token || isTokenExpired(token)) {
//         alert('El token ha expirado o no existe. Redirigiendo al inicio de sesión.');
//         window.location.href = '/login'; // Redirigir a la página de login
//         return;
//     }
//
//     // Realizar la solicitud fetch con el token incluido en el encabezado Authorization
//     const response = await fetch(url, {
//         method: 'GET',
//         headers: {
//             'Authorization': 'Bearer ' + token // Agregar el token JWT
//         }
//     });
//
//     // Manejar la respuesta según el código de estado
//     if (response.ok) {
//         return response; // Si la solicitud es exitosa, devolver la respuesta
//     } else if (response.status === 403) {
//         alert('Acceso denegado: No tienes permiso para acceder a esta ruta.');
//         throw new Error('Acceso denegado (403 Forbidden)');
//     } else {
//         throw new Error('Error en la solicitud: ' + response.statusText);
//     }
// }
//
//
// // Función para vista HOME
//
// export async function loadHomeView() {
//     try {
//         const response = await fetchWithAuth('/home');
//         const htmlContent = await response.text();
//         document.body.innerHTML = htmlContent; // Renderizar la vista en la página
//     } catch (error) {
//         console.error('Error al cargar la vista /home: ', error);
//     }
// }
//
//
// // Función para vista Odontologos
//
// export async function loadOdontologosView() {
//     try {
//         const response = await fetchWithAuth('/view/odontologos');
//         const htmlContent = await response.text();
//         document.body.innerHTML = htmlContent; // Renderizar la vista en la página
//     } catch (error) {
//         console.error('Error al cargar la vista /view/odontologos: ', error);
//     }
// }
//
//
// // Función para vista Pacientes
//
// export async function loadPacientesView() {
//     try {
//         const response = await fetchWithAuth('/view/pacientes');
//         const htmlContent = await response.text();
//         document.body.innerHTML = htmlContent; // Renderizar la vista en la página
//     } catch (error) {
//         console.error('Error al cargar la vista /view/pacientes: ', error);
//     }
// }
//
//
// // Función para vista Turnos
//
// export async function loadTurnosView() {
//     try {
//         const response = await fetchWithAuth('/view/turnos');
//         const htmlContent = await response.text();
//         document.body.innerHTML = htmlContent; // Renderizar la vista en la página
//     } catch (error) {
//         console.error('Error al cargar la vista /view/turnos: ', error);
//     }
// }
//


