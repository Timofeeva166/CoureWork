# Запуск SUT, автотестов и получение репорта
1. Запустить Docker Desktop
2. Открыть проект в Intellij IDEA
3. Запустить контейнер при помощи команды "docker-compose up"
4. Запустить приложение при помощи команды "java -jar ./artifacts/aqa-shop.jar"
5. Запустить тесты командой ".\gradlew test"
6. Получить отчёт при помощи команды ".\gradlew allureServe"
