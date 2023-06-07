## ShopApp Using Spring Boot Demo

### Run in docker

#### Build docker image

```
docker build -t shopapp:v0.0.1 .
```

#### Run docker image

```
docker run -p 8081:8080 shopapp:v0.0.1
```

#### Docker run mysql

docker run -dp 3307:3306 --name mysql-container -e MYSQL_ROOT_PASSWORD=1 mysql:8

#### Run docker compose

```
docker-compose -f ./docker-compose.yml up -d --build
```

```
docker-compose -f ./docker-compose.yml down -v
```
