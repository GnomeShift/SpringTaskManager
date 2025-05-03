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
**baseUrl для отправки запросов**
> http://localhost:8080/{api_endpoint}

В таблице ниже приведены доступные API-Endpoints.

| API-Endpoint                          | Метод  | Роль  | Описание                     | Тело запроса                                                                                                              |
|---------------------------------------|--------|-------|------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| {baseUrl}**/api/auth/register**       | POST   | -     | Регистрация пользователя     |                                                                                                                           |
| {baseUrl}**/api/auth/login**          | POST   | -     | Авторизация пользователя     |                                                                                                                           |
| {baseUrl}**/api/users**               | GET    | ADMIN | Поиск всех пользователей     | -                                                                                                                         |
| {baseUrl}**/api/users/{id}**          | GET    | USER  | Поиск пользователя по ID     | -                                                                                                                         |
| {baseUrl}**/api/users/{id}**          | PUT    | USER  | Обновление пользователя      | `{ "name": "NAME UPDATED", "email": "mail.updated@example.com", "password": "PASSWORD" }`                                 |
| {baseUrl}**/api/users/{id}**          | DELETE | ADMIN | Удаление пользователя        | -                                                                                                                         |
| {baseUrl}**/api/tasks**               | GET    | ADMIN | Поиск всех задач             | -                                                                                                                         |
| {baseUrl}**/api/tasks/owned/{id}**    | GET    | USER  | Поиск задачи по ID           | -                                                                                                                         |
| {baseUrl}**/api/tasks/assigned/{id}** | GET    | USER  | Поиск задачи по владельцу    | -                                                                                                                         |
| {baseUrl}**/api/tasks**               | GET    | USER  | Поиск задачи по назначенному | -                                                                                                                         |
| {baseUrl}**/api/tasks**               | POST   | USER  | Создание новой задачи        | `{ "title": "TITLE", "description": "DESCRIPTION", "status": "NEW/IN_PROGRESS/DONE", "ownerId": "UUID" }`                 |
| {baseUrl}**/api/tasks/{id}**          | PUT    | USER  | Обновление задачи            | `{ "title": "TITLE UPDATED", "description": "DESCRIPTION UPDATED", "status": "NEW/IN_PROGRESS/DONE", "ownerId": "UUID" }` |
| {baseUrl}**/api/tasks/{id}**          | DELETE | USER  | Удаление задачи              | -                                                                                                                         |
