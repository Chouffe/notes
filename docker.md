# Docker

## Vocabulary

* `image`:
* `container`: running instance of an image
* `service`: container in production
* `stack`:
* `task`: single container running in a service

## List Docker CLI commands

```
docker
```
```
docker container --help
```

## Display Docker version and info

```
docker --version
```
```
docker version
```
```
docker info
```

## Execute Docker image

```
docker run hello-world
```
* Use a specific port mapping [host 8083 -> container 80]
```
docker run -p 8083:80 friendlyhello:latest
```
* Name a container with `--name`
```
docker run --name docker-nginx -p 80:80 nginx
```
* Run in detached mode `-d`
```
docker run -d -p 4000:80 friendlyhello
```
* Run image from a registry
```
docker run username/repository:tag
```

## List Docker images

* List all docker images on this computer
```
docker image ls
```

## List Docker containers (running, all, all in quiet mode)

* List all running containers
```
docker container ls
```
* List all containers (even the ones that are not running)
```
docker container ls --all
```
```
docker container ls -aq
```

## Kill/Remove container

* First list containers to get the `hash`
```
docker container ls
```
* Stop the container for a given `hash`
```
docker container stop <hash>
```
* Force shutdown of the specified container
```
docker container rm <hash>
```
* Remove image from this machine
```
docker image rm <image id>
```
* Remove all images from this machine
```
docker image rm $(docker image ls -a -q)
```

## Tag

* Tag a docker image
```
docker tag image username/repository:tag
```
* Eg
```
docker tag friendlyhello gordon/get-started:part2
```

## Service

* List tasks associated with an app
```
docker service ps <service>
```
* Scaling the service/app
```
# Edit the number of replicas in your docker-compose.yml file
# Edit the config in general

# Perfom an in-place update
$ docker stack deploy -c docker-compose.yml <image
```

## Stack

* List stacks or apps
```
docker stack ls
```
* Run the specified compose file
```
docker stack deploy -c <composefile> <appname>
```
* Take down the app
```
docker stack rm <name>
```
* Take down the swarm
```
docker swarm leave --force
```


## Build

* Create image using current directory's Dockerfile
```
docker build -t friendlyhello .
```

## Production

* [Use Compose in Production](https://docs.docker.com/compose/production/)

## Nginx and Docker

* [Resource](https://www.digitalocean.com/community/tutorials/how-to-run-nginx-in-a-docker-container-on-ubuntu-14-04)
* [Resource](https://dev.to/domysee/setting-up-a-reverse-proxy-with-nginx-and-docker-compose-29jg)
* serve a index.html with Nginx and Docker on port 8765
```
docker run --name docker-nginx -v ~/docker/nginx-playground/html:/usr/share/nginx/html -p 8765:80 nginx
```
* Copy the default Nginx config on local volume
```
docker cp docker-nginx:/etc/nginx/conf.d/default.conf default.conf
```
* Run nginx with config file
```
docker run --name docker-nginx -p 80:80 -v ~/docker-nginx/html:/usr/share/nginx/html -v ~/docker-nginx/default.conf:/etc/nginx/conf.d/default.conf -d nginx
```
* Reloading the container is needed everytime the config file is updated
```
docker restart docker-nginx
```

## Docker Multi Stage build

* Can chain multiple FROM and ship a minimal image (getting rid of the build artifacts for instance)
* Build up to the builder step and tag the image
```
docker build -t haskell-dev --target builder .
```

## Tips

* Inspect what volumes are mounted in a running container
```
docker inspect -f '{{ .Mounts }}' container-name
```

* Inspect docker object sizes
```
docker system df
```
* Kill Running containers
```
docker kill $(docker ps -q)
```
* Delete old containers
```
docker ps -a | grep 'weeks ago' | awk '{print $1}' | xargs docker rm
```
* Delete dangling images
```
docker rmi $(docker images -q -f dangling=true)
```
* Cleaning APT in a RUN layer
```
RUN {apt commands} \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
```
* Remove all untagged images
```
docker rmi $(docker images | grep "^" | awk '{split($0,a," "); print a[3]}')
```
* Remove all exited containers
```
docker rm -f $(docker ps -a | grep Exit | awk '{ print $1 }')
```
* Volumes can be files
```
# copy file from container
docker run --rm httpd cat /usr/local/apache2/conf/httpd.conf > httpd.conf

# edit file
vim httpd.conf

# start container with modified configuration
docker run --rm -ti -v "$PWD/httpd.conf:/usr/local/apache2/conf/httpd.conf:ro" -p "80:80" httpd
```
* Look at environment variables of an image
```
docker run --rm postgres:10.5 env
```

## Docker Compose

### CLI

* Start docker compose
```
docker-compose up -d
```
* Stop docker compose
```
docker-compose down
```
* Restart only one service
```
docker-compose restart service1
```

### Associated Makefile

* Example
```
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

### Volumes

* Persistent storage for docker containers
* 2 types of volumes
  * One that maps a file or directory to one inside the container
  * One that just make a file or directory persistent (named volumes) without making them accessible on the fs

### Environment Variables

* Specify a file that contains them. One declaration per line.
```
ENV=production
APPLICATION_URL=http://ismydependencysafe
```
* Declaring them directly in docker-compose.yml
```
version: '3'
services:
  nginx:
    ...
    env_file:
      - ./common.env
    environment:
      - ENV=development
      - APPLICATION_URL=http://ismydependencysafe
    ...
```

### Networks

* If no network is specified, all containers are in the same network
* Containers can reference each others by names in the same network

### Resources

* [Setting up a simple Proxy Server Using Docker and Django](https://www.codementor.io/samueljames/nginx-setting-up-a-simple-proxy-server-using-docker-and-python-django-f7hy4e6jv)
* [Docker Cheatsheet](https://github.com/wsargent/docker-cheat-sheet)
