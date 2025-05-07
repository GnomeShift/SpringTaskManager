document.addEventListener('DOMContentLoaded', () => {
    const editTaskForm = document.getElementById('editTaskForm');
    const taskTitleInput = document.getElementById('taskTitle');
    const taskDescriptionTextarea = document.getElementById('taskDescription');
    const taskOwnerId = document.getElementById('taskOwner')
    const taskStatusSelect = document.getElementById('taskStatus');

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

    async function loadTask() {
        try {
            const response = await fetch(`/api/tasks/${taskId}`, {
                headers: setAuthorizationHeader(new Headers())
            });

            if (!response.ok) {
                throw new Error(`Ошибка HTTP! Код ответа: ${response.status}`);
            }

            const task = await response.json();

            taskTitleInput.value = task.title;
            taskDescriptionTextarea.value = task.description;
            taskOwnerId.value = task.ownerId.toString();
            taskStatusSelect.value = task.status;

        }
        catch (error) {
            console.error('Ошибка загрузки данных:', error);
            alert('Ошибка загрузки данных! Подробнее в консоли.');
        }
    }

    editTaskForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const title = taskTitleInput.value;
        const description = taskDescriptionTextarea.value;
        const ownerId = taskOwnerId.value;
        const status = taskStatusSelect.value;

        try {
            const response = await fetch(`/api/tasks/${taskId}`, {
                method: 'PUT',
                headers: setAuthorizationHeader(new Headers({'Content-Type': 'application/json'})),
                body: JSON.stringify({ title, description, status, ownerId }),
            });

            if (!response.ok) {
                if (response.status === 401) {
                    window.location.href = '/login'
                }
                else {
                    const errorData = await response.json();
                    alert(`Ошибка обновления задачи: ${errorData.message}`);
                }
            }
        }
        catch (error) {
            console.error('Ошибка обновления задачи:', error);
            alert('Ошибка обновления задачи! Подробнее в консоли.');
        }
    });

    if (taskId) {
        loadTask();
    }
});