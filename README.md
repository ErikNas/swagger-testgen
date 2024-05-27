# swagger-testgen

[![GitHub issues](https://img.shields.io/github/issues-search?query=repo%3AErikNas%2Fswagger-testgen%20is%3Aopen&style=for-the-badge&label=open%20issues)](https://github.com/ErikNas/swagger-testgen/issues?q=is%3Aopen)
![GitHub commits](https://img.shields.io/github/last-commit/ErikNas%2Fswagger-testgen?style=for-the-badge)

Генератор Java API-тестов на основании swagger.json

Просто задайте путь до файла с описание swagger спецификации и приложение сгенерирует готовый тестовый проект.

Данный подход позволит вам сэкономить время для старта автоматизации тестирования на проекте и избавит от возможной рутины.

## Ключевые возможности

- Создание проекта для автоматизации API-тестов с нуля
- Генерация позитивных тестов для всех методов и всех ручек
- Создание негативных тестов для обязательных полей через параметризацию

## Начать использовать

Скачайте последний версию из раздела "Релизы".

Чтобы запустить нужно выполнить команду

Для OS Windows
```bash
  swagger-testgen.bat path-to-swagger.json
```

Для OS Linux
```bash
  swagger-testgen.sh path-to-swagger.json
```

Так же можно запустить непосредственно JAR-файл
```bash
  java -jar swagger-testgen.jar path-to-swagger.json
```

## Пример работы

Тут должна быть GIF с демонстрацией работы

![](https://media1.tenor.com/m/yheo1GGu3FwAAAAd/rick-roll-rick-ashley.gif)

## Сделано с помощью

[//]: # (![Java]&#40;https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white&#41;)
[//]: # (![Gradle]&#40;https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white&#41;)
[//]: # (![Thymeleaf]&#40;https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white&#41;)
[//]: # (![Swagger]&#40;https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white&#41;)

![Java](https://img.shields.io/badge/Java-17-%23f89820?style=for-the-badge)
![Gradle](https://img.shields.io/badge/Gradle-8-%2302303A?style=for-the-badge&logo=gradle)

**Приложение:** 
![Swagger](https://img.shields.io/badge/Swagger-All-%2385EA2D?style=for-the-badge&logo=swagger)
![Thymeleaf](https://img.shields.io/badge/Thymelef-3-%23005F0F?style=for-the-badge&logo=thymeleaf)

**Автотесты:**
![JUnit](https://img.shields.io/badge/JUnit-5-%2325A162?style=for-the-badge)
![Rest-Assured](https://img.shields.io/badge/Rest--Assured-5-green?style=for-the-badge)
![Allure](https://img.shields.io/badge/Allure-2-white?style=for-the-badge)
![Hibernate](https://img.shields.io/badge/Hibernate-5-%2359666C?style=for-the-badge&logo=hibernate)
![]()
