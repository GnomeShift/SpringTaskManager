document.addEventListener('DOMContentLoaded', () => {
    const userTableBody = document.getElementById('userTableBody');

    function getToken() {
        return localStorage.getItem('token');
    }

    function setAuthorizationHeader(headers) {
        const token = getToken();

        if (token) {
            headers.set('Authorization', `Bearer ${token}`);
        }

        return headers
    }

    async function loadUsers() {
        try {
            const response = await fetch('/api/users', {
                headers: setAuthorizationHeader(new Headers()),
            });

            if (!response.ok) {
              if (response.status === 401 || response.status === 403) {
                 localStorage.removeItem('token');
                 window.location.href = '/login';
                 return;
              }

                const errorData = await response.json();
                alert(`Ошибка загрузки данных:  ${errorData.message || response.statusText}`);
                return;
            }

            const users = await response.json();
            displayUsers(users);
        }
        catch (error) {
            console.error('Ошибка загрузки данных:', error);
            userTableBody.innerHTML = '<tr><td colspan="5" class="text-danger">Ошибка загрузки данных!</td></tr>';
        }
    }

    function displayUsers(users) {
        userTableBody.innerHTML = '';

        users.forEach(user => {
            const row = document.createElement('tr');

            const idCell = document.createElement('td');
            idCell.textContent = user.id;

            const nameCell = document.createElement('td');
            nameCell.textContent = user.name;

            const emailCell = document.createElement('td');
            emailCell.textContent = user.email;

            const emptyCell = document.createElement('td');
            const deleteButton = document.createElement('button');

            deleteButton.textContent = 'Delete';
            deleteButton.className = 'btn btn-danger btn-sm';
            deleteButton.addEventListener('click', () => deleteUser(user.id));
            emptyCell.appendChild(deleteButton);

            row.appendChild(idCell);
            row.appendChild(nameCell);
            row.appendChild(emailCell);
            row.appendChild(emptyCell);

            userTableBody.appendChild(row);
        });
    }

    async function deleteUser(userId) {
        if (confirm('Вы уверены, что хотите удалить этого пользователя?')) {
            try {
                const response = await fetch(`/api/users/${userId}`, {
                    method: 'DELETE'
                });

                if (response.ok) {
                    await loadUsers();
                }
                else {
                    const errorData = await response.json();
                    alert(`Ошибка удаления: ${errorData.message}`);
                }
            }
            catch (error) {
                console.error('Ошибка удаления:', error);
                alert('Ошибка удаления! Подробнее в консоли');
            }
        }
    }

    loadUsers();
});
