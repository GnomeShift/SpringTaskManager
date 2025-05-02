<h1>
<p align="center">
<a href="https://github.com/GnomeShift/SpringTaskManager" target="_blank" rel="noopener noreferrer">SpringTaskManager</a>
</p>
</h1>

<p align="center">
  <a href="README.md">🇷🇺 Русский</a>
</p>

## 🚀Быстрая навигация
* [Обзор](#обзор)
    * [Функции](#функции)
* [Конфигурация](#конфигурация)
    * [API](#api)

# 🌐Обзор
**SpringTaskManager** - это менеджер задач.

## ⚡Функции
* Управление пользователями.
* Управление задачами.
* REST API.
* Поддержка PostgreSQL.
* Предустановленные роли (ROLE_USER, ROLE_ADMIN)

# ⚙️Конфигурация
#### 1️⃣ Клонируйте репозиторий:
```bash
git clone https://github.com/GnomeShift/SpringTaskManager.git
```

#### 2️⃣ Откройте `application.properties` в текстовом редакторе и укажите значения переменных.

| Переменная                                                   | Значение                                                                                                                                                      |
|--------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| spring.datasource.url=jdbc:postgresql://IP_БД:ПОРТ_БД/ИМЯ_БД | IP, порт, имя БД                                                                                                                                              |
| spring.datasource.username=ЛОГИН                             | Пользователь БД                                                                                                                                               |
| spring.datasource.password=ПАРОЛЬ                            | Пароль пользователя БД                                                                                                                                        |
| spring.jpa.hibernate.ddl-auto=ЗНАЧЕНИЕ                       | create - создать структуру БД (**УДАЛИТ ВСЕ ДАННЫЕ**);<br/>update - обновить структуру БД;<br/>validate - проверить структуру БД;<br/>none - ничего не делать |

#### 3️⃣ Запустите сборку проекта:
```bash
gradlew bootRun
```
> [!NOTE]
> Убедитесь, что у Вас установлен Gradle.

# 📡API
В таблице ниже приведены доступные API-Endpoints.

| API-Endpoint        | Метод  | Описание                    | Тело запроса                                                                                                              |
|---------------------|--------|-----------------------------|---------------------------------------------------------------------------------------------------------------------------|
| **/api/users**      | GET    | Получить всех пользователей | -                                                                                                                         |
| **/api/users/{id}** | GET    | Получить пользователя по ID | -                                                                                                                         |
| **/api/users**      | POST   | Создать пользователя        | `{ "name": "NAME", "email": "mail@example.com", "password": "PASSWORD" }`                                                 |
| **/api/users/{id}** | PUT    | Обновить пользователя       | `{ "name": "NAME UPDATED", "email": "mail.updated@example.com", "password": "PASSWORD" }`                                 |
| **/api/users/{id}** | DELETE | Удалить пользователя        | -                                                                                                                         |
| **/api/tasks**      | GET    | Получить все задачи         | -                                                                                                                         |
| **/api/tasks/{id}** | GET    | Получить задачу по ID       | -                                                                                                                         |
| **/api/tasks**      | POST   | Создать новую задачу        | `{ "title": "TITLE", "description": "DESCRIPTION", "status": "NEW/IN_PROGRESS/DONE", "ownerId": "UUID" }`                 |
| **/api/tasks/{id}** | PUT    | Обновить задачу             | `{ "title": "TITLE UPDATED", "description": "DESCRIPTION UPDATED", "status": "NEW/IN_PROGRESS/DONE", "ownerId": "UUID" }` |
| **/api/tasks/{id}** | DELETE | Удалить задачу              | -                                                                                                                         |

IP-адрес для отправки запросов:
> http://localhost:8080/{api_endpoint}
