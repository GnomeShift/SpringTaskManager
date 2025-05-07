document.addEventListener('DOMContentLoaded', () => {
    const loginButton = document.getElementById('loginButton');

    loginButton.addEventListener('click', async (event) => {
        event.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email: email, password: password })
            });

            if (response.ok) {
                window.location.href = '/dashboard';

                const jwtResponse = await response.json();
                localStorage.setItem('token', jwtResponse.token);
            }
            else {
                const errorData = await response.json();
                alert(`Ошибка входа: ${errorData.message}`);
            }
        }
        catch (error) {
            console.error('Ошибка входа:', error);
            alert('Ошибка входа! Подробнее в консоли');
        }
    });
});
