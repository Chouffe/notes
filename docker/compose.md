# Docker Compose

* Configure relationships between containers
* Save docker container run settings in one file
* Create one liner developer environment startups
  1. yaml file to specify containers, networks and volumes
  2. CLI tool `docker-compose` used for local dev/test automation with the yaml files
* In production, use docker swarm (docker-compose.yml can be used with it)

## Full App Lifecycle with Compose

* local `docker-compose up` for dev env
* Remote `docker-compose up` for CI env
* Remote `docker stack deploy` for prod env
* The following docker-compose files can be created:
  * `docker-compose.yml`
  * `docker-compose.override.yml`: automatically gets applied to docker-compose
  * `docker-compose.test.yml`
  * `docker-compose.prod.yml`
  * `Dockerfile`

```sh
docker-compose -f docker-compose.yml -f docker-compose.test.yml up -d
```

* Look at the generated yaml config file

```sh
docker-compose -f docker-compose.yml -f docker-compose.test.yml config
```

* For creating and updating stacks one could use the following output.yml

```sh
docker-compose -f docker-compse.yml -f docker-compose.prod.yml config > output.yml
```

## YAML

* `version`: if not specified, it defaults to version 1
* `services`: containers configs
  * `servicename`: DNS name inside network
  * `image`
  * `command`: replace the docker CMD specified by the image
  * `environment`: same as -e in docker container run
  * `volumes`: same as -v in docker container run
  * `ports`: same as -p in docker container run
* `volumes`: same as docker volume create
* `networks`: same as docker network create

```yaml
version: '3.1'  # if no version is specificed then v1 is assumed. Recommend v2 minimum

services:  # containers. same as docker run
  servicename: # a friendly name. this is also DNS name inside network
    image: # Optional if you use build:
    command: # Optional, replace the default CMD specified by the image
    environment: # Optional, same as -e in docker run
    volumes: # Optional, same as -v in docker run
  servicename2:

volumes: # Optional, same as docker volume create

networks: # Optional, same as docker network create
```

```yaml
version: '2'

services:

  wordpress:
    image: wordpress
    ports:
      - 8080:80
    environment:
      WORDPRESS_DB_HOST: mysql
      WORDPRESS_DB_NAME: wordpress
      WORDPRESS_DB_USER: example
      WORDPRESS_DB_PASSWORD: examplePW
      WORDPRESS_SUPER_SECRET  # Taken from your computer at runtime
    volumes:
      - ./wordpress-data:/var/www/html

  mysql:
    image: mariadb
    environment:
      MYSQL_ROOT_PASSWORD: examplerootPW
      MYSQL_DATABASE: wordpress
      MYSQL_USER: example
      MYSQL_PASSWORD: examplePW
    volumes:
      - mysql-data:/var/lib/mysql

volumes:
  mysql-data:
```

## CLI

If all your projeccts had a Dockerfile and a docker-compose.yml, then new
developer onboarding would be: `git clone ... && docker-compose up`

* Start docker compose: setup volumes/networks and start all containers:

```bash
docker-compose up -d
```

* Stop docker compose: stop all containers and remove volumes/networks

```bash
docker-compose down
```

* Stop docker compose and tear down volumes

```bash
docker-compose down -v
```

* Stop docker compose and remove images

```bash
docker-compose down --rmi local
```

* Restart only one service

```bash
docker-compose restart service1
```

* Monitoring

```bash
docker-compose ps
docker-compose top
```

* Logs from the container

```bash
docker compose logs 'container_name'
```

* Tail the logs

```bash
docker compose logs -f 'container_name'
```

### Associated Makefile

* Example

```Makefile
start-dev:
  docker-compose up

start-prod:
  docker-compose -f docker-compose.yml -f docker-compose.prod.yml up

stop-compose:
  @eval docker stop $$(docker ps -a -q)
  docker-compose down

ssh-nginx:
  docker exec -it nginx_server bash

ssh-django-web:
  docker exec -it django_web bash

ssh-db:
  docker exec -it db bash

ssh-es:
  docker exec -it es bash

ssh-kibana:
  docker exec -it kibana bash

check-network-config-details:
  docker network inspect bookme_default

build-prod:
  docker-compose -f docker-compose.yml -f docker-compose.prod.yml build

build-dev:
  docker-compose build
```
