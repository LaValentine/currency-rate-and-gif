# Описание
### Сервис сравнивает вчерашние и сегодняшние значения курса валюты и отображает gif

#### Технологии
* Java 11
* Spring Boot 2.7.0
* Spring Cloud OpenFeign

#### Ссылки
* REST API курсов валют - https://docs.openexchangerates.org/
* REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide

## Запуск приложения
### Step #1
  ```
    gradle clean bootJar
  ```
### Step #2
  ```
    java -jar ./build/libs/currency-rate-and-gif-0.0.1-SNAPSHOT.jar
  ```
## Запуск приложения с использованием Docker
### Step #1
  ```
    docker build -t currency-rate-and-gif .
  ```
### Step #2
  ```
    docker run -p 8080:8080 currency-rate-and-gif
  ```
## Перейдите
- Чтобы получить данные c отображение gif
  ```
    http://localhost:8080/api/currencies/CURRENCY-CODE/gif
  ```
- Чтобы получить данные ввиде json
  ```
    http://localhost:8080/api/currencies/CURRENCY-CODE
  ```
!!! CURRENCY-CODE - код валюты, информацию о котрой нужно получить