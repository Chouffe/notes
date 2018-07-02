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
