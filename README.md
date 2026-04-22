# DigiUni Registry

Console-based university registry system (NaUKMA).

## Tech stack
- Java 21
- Maven
- JUnit 5


## Project structure
- domain — entities
- repository — data storage
- service — business logic
- ui — console interface

## Branching model
- main — stable (checkpoints)
- dev — integration
- personal branches — feature development

## Інструкція для користувача
### Як запустити програму:
- 1.Скачайте проєкт з GitHub
- 2.Відкрийте його в IntelliJ IDEA
- 3.Переконайтесь, що встановлена Java 17+
- 4.Запустіть файл Main.java
### Авторизація:
Після запуску з’явиться меню входу:
- введіть логін
- потім введіть пароль
(У програмі у нас вже є готові логін та пароль відповідно ролі(User, Manager, Admin)). В залежності яку роль ви вибрали, у вас будуть різні ролі.

### Як користуватись меню:
У програмі все працює через цифри:
- вводите число, переходите у відповідний розділ.
- 0 - повернення назад або вихід.

Наводимо приклад (як виглядає для студентів(якщо зайшли як admin)):
- 1.Show all
- 2.Add
- 3.Edit
- 4.Delete
- 5.Find by full name
- 6.Find by course
- 7.Find by group
- 0.Back
### Робота зі студентами:
У меню студентів можна:
- додати студента
- показати всіх студентів
- редагувати студента
- видалити студента
- знайти за повним ім'ям
- знайти за курсом
- знайти за групою



