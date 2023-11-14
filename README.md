# Запуск SUT, автотестов и получение репорта
- Запустить Docker Desktop
- Открыть проект в Intellij IDEA

Для MYSQL:
- Запустить контейнер при помощи команды docker-compose -f docker-compose-mysql.yml up
- Запустить приложение при помощи команды java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
- Запустить тесты командой ./gradlew test "-Ddb.url=jdbc:mysql://localhost:3306/app"
- Получить отчёт при помощи команды .\gradlew allureServe

Для PostgreSQL:
- Запустить контейнер при помощи команды docker-compose -f docker-compose-postgresql.yml up
- Запустить приложение при помощи команды java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
- Запустить тесты командой ./gradlew test "./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
- Получить отчёт при помощи команды .\gradlew allureServe
