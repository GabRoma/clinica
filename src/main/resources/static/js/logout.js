function logout() {
    localStorage.removeItem('jwtToken');
    window.location.href = '/login'; // Redirigir al login
}
