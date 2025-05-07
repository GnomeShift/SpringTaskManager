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
    * [Веб-интерфейс](#веб-интерфейс)
    * [API](#api)

# 🌐Обзор
**SpringTaskManager** - это менеджер задач.

## ⚡Функции
* Веб-интерфейс.
* Управление пользователями.
* Управление задачами.
* REST API.
* Поддержка PostgreSQL.
* Предустановленные роли (USER, ADMIN)

# ⚙️Конфигурация
#### 1️⃣ Клонируйте репозиторий:
```bash
git clone https://github.com/GnomeShift/SpringTaskManager.git
```

#### 2️⃣ Сгенерируйте пару ключей:
```bash
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048 && openssl rsa -pubout -in private_key.pem -out public_key.pem
```
> [!WARNING]
> Ключи, сгенерированные SSH-клиентами (PuTTY и т.д.), начинающиеся с "---- BEGIN SSH", без расширения .pem, не поддерживаются!

#### 3️⃣ Откройте `application.properties` в текстовом редакторе и укажите значения переменных.

| Переменная                                                   | Значение                                                                                                                                                      |
|--------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| spring.datasource.url=jdbc:postgresql://IP_БД:ПОРТ_БД/ИМЯ_БД | IP, порт, имя БД                                                                                                                                              |
| spring.datasource.username=ЛОГИН                             | Пользователь БД                                                                                                                                               |
| spring.datasource.password=ПАРОЛЬ                            | Пароль пользователя БД                                                                                                                                        |
| spring.jpa.hibernate.ddl-auto=ЗНАЧЕНИЕ                       | create - создать структуру БД (**УДАЛИТ ВСЕ ДАННЫЕ**);<br/>update - обновить структуру БД;<br/>validate - проверить структуру БД;<br/>none - ничего не делать |
| jwt.expirationMs=ЗНАЧЕНИЕ                                    | Время жизни JWT-токена в миллисекундах (**по умолчанию - 86400000**)                                                                                          |
| jwt.privateKeyPath=/ПУТЬ/ДО/ФАЙЛА                            | Путь до файла приватного ключа (**по умолчанию - ./src/main/resources/private_key.pem**)                                                                      |
| jwt.publicKeyPath=/ПУТЬ/ДО/ФАЙЛА                             | Путь до файла публичного ключа (**по умолчанию - ./src/main/resources/public_key.pem**)                                                                       |

#### 4️⃣ Запустите сборку проекта:
```bash
gradlew bootRun
```
> [!NOTE]
> Убедитесь, что у Вас установлен Gradle.

# 🌐Веб-интерфейс
**baseUrl для отправки запросов**
> http://localhost:8080/{web_endpoint}

В таблице ниже приведены доступные Web-Endpoints.

| Web-Endpoint            | Описание                 |
|-------------------------|--------------------------|
| {baseUrl}**/register**  | Регистрация пользователя |
| {baseUrl}**/login**     | Авторизация пользователя |
| {baseUrl}**/dashboard** | Главная страница         |
| {baseUrl}**/admin**     | Админ-панель             |

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
