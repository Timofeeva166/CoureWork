# Запуск SUT, автотестов и получение репорта
1. Запустить Docker Desktop
2. Открыть проект в Intellij IDEA

Для MYSQL:
3. Запустить контейнер при помощи команды docker-compose -f docker-compose-mysql up
4. Запустить приложение при помощи команды java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
5. Запустить тесты командой ./gradlew test "-Ddb.url=jdbc:mysql://localhost:3306/app"
6. Получить отчёт при помощи команды .\gradlew allureServe

Для PostgreSQL:
3. Запустить контейнер при помощи команды docker-compose -f docker-compose-postgresql up
4. Запустить приложение при помощи команды java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
5. Запустить тесты командой ./gradlew test "./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
6. Получить отчёт при помощи команды .\gradlew allureServe
