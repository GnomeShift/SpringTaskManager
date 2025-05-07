document.addEventListener('DOMContentLoaded', () => {
    const taskListDiv = document.getElementById('taskList');
    const createTaskForm = document.getElementById('createTaskForm');
    const logoutButton = document.getElementById('logoutButton');

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

    async function loadTasks() {
        try {
            const response = await fetch('/api/tasks', {
                headers: setAuthorizationHeader(new Headers())
            });

            if (!response.ok) {
                if (response.status === 401) {
                    window.location.href = '/login';
                }

                const errorData = await response.json();
                alert(`Error loading tasks: ${errorData.message || response.statusText}`);
                return;
            }

            const tasks = await response.json();
            displayTasks(tasks);
        }
        catch (error) {
            console.error('Ошибка загрузки данных:', error);
            taskListDiv.innerHTML = '<p class="text-danger">Ошибка загрузки данных!</p>';
        }
    }

    function displayTasks(tasks) {
        taskListDiv.innerHTML = '';

        if (tasks.length === 0) {
            taskListDiv.innerHTML = '<p>Задачи не найдены!</p>';
            return;
        }

        tasks.forEach(task => {
            const taskCard = document.createElement('div');
            taskCard.className = 'task-card';

            const titleElement = document.createElement('h5');
            titleElement.textContent = task.title;

            const descriptionElement = document.createElement('p');
            descriptionElement.textContent = task.description;

            const ownerId = document.createElement('p')
            ownerId.textContent = task.ownerId;

            const statusBadge = document.createElement('p');
            statusBadge.className = `status-badge ${task.status.toLowerCase().replace(' ', '-')}`;
            statusBadge.textContent = task.status;

            const editLink = document.createElement('a');
            editLink.href = `/task/${task.id}/edit`;
            editLink.textContent = 'Изменить';
            editLink.className = 'btn btn-sm btn-outline-primary';

            taskCard.appendChild(titleElement);
            taskCard.appendChild(descriptionElement);
            taskCard.appendChild(ownerId)
            taskCard.appendChild(statusBadge);
            taskCard.appendChild(editLink);

            taskListDiv.appendChild(taskCard);
        });
    }

    createTaskForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const title = document.getElementById('taskTitle').value;
        const description = document.getElementById('taskDescription').value;
        const ownerId = document.getElementById('taskOwner').value;
        const status = document.getElementById('taskStatus').value;

        try {
            const response = await fetch('/api/tasks', {
                method: 'POST',
                headers: setAuthorizationHeader(new Headers({'Content-Type': 'application/json',})),
                body: JSON.stringify({ title, description, status, ownerId }),
            });

            if (response.ok) {
                await loadTasks();
                createTaskForm.reset();
            }
            else {
                const errorData = await response.json();
                alert(`Ошибка создания задачи: ${errorData.message}`);
            }
        }
        catch (error) {
            console.error('Ошибка создания задачи:', error);
            alert('Во время создания задачи произошла ошибка!');
        }
    });

    logoutButton.addEventListener('click', () => {
        localStorage.removeItem('token');
        window.location.href = '/login';
    });

    loadTasks();
});
