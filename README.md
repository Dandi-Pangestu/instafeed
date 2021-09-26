# Instafeed Service

There are two functionality to fetch feed data. The first by hit the endpoint and the second by automatic scheduling that default will run every 5 minutes. 
You can configure the cron of scheduling by editing `ig.sync.interval` on `application.properties`.

## Installation

### Prerequisite

* Docker version 19.03.13 or higher
* Docker Compose version 1.27.4 or higher
* Instagram feed API from [NoCodeAPI](https://nocodeapi.com/instagram-api). By default the Instagram feed API has been set 
in `application.properties` with the key `ig.feed.url`. You can generate your own API and set to the `ig.feed.url`.

### How To Run the Application

Build the application

```
mvn clean install
```

Run the application

```
docker-compose up --build
```

### How To Run the Unit Test

Run Read Service

```
mvn clean test
```

## API Documentation

### Sync the Feed Data

```http
POST http://localhost:8080/feeds/sync-ig-feed
```

Example Request:

```curl
curl --location --request POST 'http://localhost:8080/feeds/sync-ig-feed' \
--header 'Content-Type: application/json' \
```
