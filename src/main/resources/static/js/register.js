document.addEventListener('DOMContentLoaded', () => {
    const registerButton = document.getElementById('registerButton');

    registerButton.addEventListener('click', async (event) => {
        event.preventDefault();

        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch('/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ name: name, email: email, password: password })
            });

            if (response.ok) {
                window.location.href = '/login';
            }
            else {
                const errorData = await response.json();
                alert(`Ошибка регистрации: ${errorData.message}`);
            }
        }
        catch (error) {
            console.error('Ошибка регистрации:', error);
            alert('Ошибка регистрации! Подробнее в консоли.');
        }
    });
});
