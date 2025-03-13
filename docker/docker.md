# Docker

## Vocabulary

* `image`: blueprint for packaging an environment and some code
* `container`: running instance of an image
* `service`: container in production
* `task`: single container running in a service

## Installation

* From website: `https://get.docker.com/`

```sh
curl -fsSL https://get.docker.com -o get-docker.sh && sh get-docker.sh
```

## List Docker CLI commands

```sh
docker
```

```sh
docker container --help
```

## Display Docker version and info

```sh
docker --version
```

```sh
docker version
```

```sh
docker info
```

## Container Commands

* Start new container interactively

```sh
docker container run -it <name>
```

* Run additional command in existing container

```sh
docker container exec -it <name> <cmd>
```

* Start a stopped container interactively

```sh
docker container start -ai <name>
```

* List port routing of a running container

```sh
docker container port <name>
```

* Cleanup after running container

```sh
docker container run -it --rm
```

## Network Commands

* show networks

```sh
docker network ls
```

* Inspect a network

```sh
docker network inspect <name>
```

* Create a network

```sh
docker network create --driver
```

* Attach a network to a container

```sh
docker network connect
```

* Detach a network from a container

```sh
docker container disconnect
```

* Connect to the localhost from inside a docker container:
  1. Solution 1: Use `--network="host"` in your docker run command, then `127.0.0.1` in your docker container will point to your docker host.
  2. Solution 2: If you are using Docker-for-Linux 20.10.0+, you can also use the host `host.docker.internal` if you started your Docker container with the `--add-host host.docker.internal:host-gateway` option.

## Image Commands

* History of the image layers

```sh
docker image history nginx
```

* Inspect an image: returns metadata as JSON about the image

```sh
docker image inspect <name>
```

* Tag an image

```sh
docker image tag nginx chouffe/nginx
docker image tag chouffe/nginx chouffe/nginx:testing
```

* Build an image in the current directory `.`

```sh
docker image build -t <tag-name> .
```

## Monitoring

* process list in one container

```sh
docker container top <container-name/id>
```

* details of one container configs

```sh
docker container inspect <container-name/id>
```

* Performance stats for all containers

```sh
docker container stats
```

## Execute Docker image

Run the `hello-world` image:
```sh
docker run hello-world
```

* Use a specific port mapping [host 8083 -> container 80]

```sh
docker run -p 8083:80 friendlyhello:latest
```

* Name a container with `--name`

```sh
docker run --name docker-nginx -p 80:80 nginx
```

* Run in detached mode `-d`

```sh
docker run -d -p 4000:80 friendlyhello
```

* Run image from a registry

```sh
docker run username/repository:tag
```

## List Docker images

* List all docker images on this computer

```sh
docker image ls
```

## List Docker containers (running, all, all in quiet mode)

* List all running containers

```sh
docker container ls
```

* List all containers (even the ones that are not running)

```sh
docker container ls --all
```

```sh
docker container ls -aq
```

## Container

* Execute command in a running container

```sh
docker exec -it datomic-free /bin/bash
```

## Kill/Remove container

* First list containers to get the `hash`

```sh
docker container ls
```

* Stop the container for a given `hash`

```sh
docker container stop <hash>
```

* Force shutdown of the specified container

```sh
docker container rm <hash>
```

* Remove image from this machine

```sh
docker image rm <image id>
```

* Remove all images from this machine

```sh
docker image rm $(docker image ls -a -q)
```

## Tag

* Tag a docker image

```sh
docker tag image username/repository:tag
```

* Eg

```sh
docker tag friendlyhello gordon/get-started:part2
```

## HealthCheck

* Default start time: 30s
* 3 possible states for a container: starting, healthy, unhealthy

### CLI

* docker container cli

```sh
docker container run --name p2 --health-cmd="pg_isready -U postgres || exit 1"  -d postgres
```

* docker service cli

```sh
docker service create --name p2 --health-cmd="pg_isready -U postgres || exit 1"  -d postgres
```

### Compose File

```yaml
version: "2.1" # minimum version
services:
  web:
    image: nginx
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost"]
      interval: 1m30s
      timeout: 10s
      retries: 3s
      start_period: 1m
```

### Dockerfile

```Dockerfile
FROM nginx:1.3

HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost/ || exit 1
```

```Dockerfile
FROM postgres

HEALTHCHECK --interval=5s --timeout=3s \
  CMD pg_isready -U postgres || exit 1
```

## Service

* List tasks associated with an app

```sh
docker service ps <service>
```

* Scaling the service/app

```sh
# Edit the number of replicas in your docker-compose.yml file
# Edit the config in general

# Perfom an in-place update
$ docker stack deploy -c docker-compose.yml <image
```

## Stack

* List stacks or apps

```sh
docker stack ls
```

* Run the specified compose file

```sh
docker stack deploy -c <composefile> <appname>
```

* Take down the app

```sh
docker stack rm <name>
```

* Take down the swarm

```sh
docker swarm leave --force
```

## Build

* Create image using current directory's Dockerfile

```sh
docker build -t friendlyhello .
```

## Production

* [Use Compose in Production](https://docs.docker.com/compose/production/)

## Nginx and Docker

* [Resource](https://www.digitalocean.com/community/tutorials/how-to-run-nginx-in-a-docker-container-on-ubuntu-14-04)
* [Resource](https://dev.to/domysee/setting-up-a-reverse-proxy-with-nginx-and-docker-compose-29jg)
* serve a index.html with Nginx and Docker on port 8765

```sh
docker run --name docker-nginx -v ~/docker/nginx-playground/html:/usr/share/nginx/html -p 8765:80 nginx
```

* Copy the default Nginx config on local volume

```sh
docker cp docker-nginx:/etc/nginx/conf.d/default.conf default.conf
```

* Run nginx with config file

```sh
docker run --name docker-nginx -p 80:80 -v ~/docker-nginx/html:/usr/share/nginx/html -v ~/docker-nginx/default.conf:/etc/nginx/conf.d/default.conf -d nginx
```

* Reloading the container is needed everytime the config file is updated

```sh
docker restart docker-nginx
```

## Docker Multi Stage build

* Can chain multiple FROM and ship a minimal image (getting rid of the build artifacts for instance)
* Build up to the builder step and tag the image

```sh
docker build -t haskell-dev --target builder .
```

## Tips

* Inspect what volumes are mounted in a running container

```sh
docker inspect -f '{{ .Mounts }}' container-id-or-name
```

* See open port for a given container

```sh
docker inspect --format='{{.Config.ExposedPorts}}' container-id-or-name
```

* Get IP address of a docker container

```sh
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container-id-or-name
```

* Inspect docker object sizes

```sh
docker system df
```

* Kill Running containers

```sh
docker kill $(docker ps -q)
```

* Delete old containers

```sh
docker ps -a | grep 'weeks ago' | awk '{print $1}' | xargs docker rm
```

* Delete dangling images

```sh
docker rmi $(docker images -q -f dangling=true)
```

* Cleaning APT in a RUN layer

```sh
RUN {apt commands} \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
```

* Remove all untagged images

```sh
docker rmi $(docker images | grep "^" | awk '{split($0,a," "); print a[3]}')
```

* Remove all exited containers

```sh
docker rm -f $(docker ps -a | grep Exit | awk '{ print $1 }')
```

* Copy files from containers to Host

```sh
docker cp containerID:/backups/10-17-2018.tar.gz backups/
```

* Volumes can be files

```sh
# copy file from container
docker run --rm httpd cat /usr/local/apache2/conf/httpd.conf > httpd.conf

# edit file
vim httpd.conf

# start container with modified configuration
docker run --rm -ti -v "$PWD/httpd.conf:/usr/local/apache2/conf/httpd.conf:ro" -p "80:80" httpd
```

* Look at environment variables of an image

```sh
docker run --rm postgres:10.5 env
```

## Docker Compose

* Configure relationships between containers
* Save docker container run settings in one file
* Create one liner developer environment startups
  1. yaml file to specify containers, networks and volumes
  2. CLI tool `docker-compose` used for local dev/test automation with the yaml files
* In production, use docker swarm (docker-compose.yml can be used with it)

### YAML

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

### CLI

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

### dockerignore

Add a `.dockerignore` file to tell the Dockerfile which files to exclude from
being copied over.

Some common entries for a `.dockerignore` file:

```txt
node_modules/
Dockerfile
.git
```

### Volumes

* Persistent storage for docker containers
* 3 types of volumes
  * One that maps a file or directory to one inside the container
  * One that just make a file or directory persistent (named volumes) without making them accessible on the fs
  * One for bookmarking a directory in the docker container (unnamed volumes)

```Dockerfile
volumes:
  - nginx/config:/etc/nginx/config
  - psql-data:/var/lib/postgres/data
  - /app/node_modules
```

__Note__: unnamed volumes are useful to tell docker to keep some folders inside
a directory when using a bind mount (eg. `node_modules`)

__Note__: It is possible to specify that bind mount is read only, which means
that the docker container cannot write to it but only read from it.

```bash
docker run ... -v "./feedback/:/app/feedback/:ro"
```

inspect a volume:

```bash
docker volume inspect name_of_the_volume
```

remove all unused volumes:

```bash
docker volume prune -a
```

### Arguments

Build-time ARGuments.
Enable variables at image build time. Not available at run time (CMD or any
application code). One can set them via `--build-arg` at build time.

### Environment Variables

Runtime ENVironment variables.

* Specify a file that contains them. One declaration per line.

```.env
ENV=production
APPLICATION_URL=http://ismydependencysafe
SECRET_KEY  # Taken from the computer at runtime
```

```bash
docker run ... --env-file ./.env
```

* Declaring them directly in docker-compose.yml

```yaml
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

Can be set via `--env` or `-e` when running docker.

In a Dockerfile:

```Dockerfile
...
ENV PORT 80

EXPOSE $PORT
```

### Networking

* If no network is specified, all containers are in the same network
* Containers can reference each others by names in the same network

There are different networking scenarios:

* network requests
* local host machine communication
* container to container communication

#### Networking: Network Requests (WWW)

This works out of the box - it is possible to communicate to outside APIs and
WWW.

#### Networking: Local Host Maching Communication

- replace `localhost` by `host.docker.internal` which is a docker specified hostname.

#### Networking: Container to Container Communication - cross container networking

- cumbersome and not recommended: inspect the container with and get the IP address:

```bash
docker inspect mongodb
```

- create a container network to allow cross container communication:

Within a Docker network, all containers can communicate with each other and IPs
are automatically resolved.

```bash
docker run --network my_network ...
```

__Note__: On needs to first create the network manually with the command
`docker network create my_network`

__Note__: The host or IP address can be referenced by name in the different
containers that are on the same network.

### Resources

* [Setting up a simple Proxy Server Using Docker and Django](https://www.codementor.io/samueljames/nginx-setting-up-a-simple-proxy-server-using-docker-and-python-django-f7hy4e6jv)
* [Docker Cheatsheet](https://github.com/wsargent/docker-cheat-sheet)

## Docker Swarm

* initialize a swarm
  * PKI and security automation
  * Raft DB created to store root CA, configs and secrets

```sh
docker swarm init
```

### Nodes

* List nodes

```sh
docker node ls
```

### Service

* Create a service

```sh
docker service create alpine ping 8.8.8.8
```

* Monitoring

```sh
docker service ps <name>
```

* Scale up a service. Look at `docker service update --help` for a list of options available

```sh
docker service update <name/id> --replicas 3
```

### Secrets Storage

* Easiest secure solution for storing secrets in Swarm
* What is a secret?
  * Usernames and passwords
  * TLS certificates and keys
  * API key
  * SSH keys
  * Any data you would prefer not be "on front page of news"
* Supports generic strings or binary contents up to 500Kb in size
* Only stored on disk on Manager nodes
* Secrets are assigned to Services (who is allowed to see/use the secrets)
* They look like files in container but are actually in-memory fs
  * `/run/secrets/<secret_name>`
  * `/run/secrets/<secret_alias>`
* Local `docker-compose` can use file-based secrets, but not secure

* create a new secret

```sh
docker secret create psql_user psql_user.txt
```

* create a new secret from pipes

```sh
echo "myDBpassWORD" | docker secret create psql_pass -
```

* Eg

```sh
docker service create \
  --name psql \
  --secret psql_user \
  --secret psql_pass \
  -e POSTGRES_PASSWORD_FILE=/run/secrets/psql_pass \
  -e POSTGRES_USER_FILE=/run/secrets/psql_user \
  postgres
```

* In Docker Compose file

```sh
version: "3.1" # At least 3.1 to use Secrets with Stacks

services:
  psql:
    image: postgres
    secrets:
      - psql_user
      - psql_password
    environment:
      POSTGRES_PASSWORD_FILE: /run/secrets/psql_password
      POSTGRES_USER_FILE: /run/secrets/psql_user
secrets:
  psql_user:
    file: ./psql_user.txt
  psql_password:
    file: ./psql_password.txt
```

* Check if the Secrets have been set properly

```sh
docker container exec <name> ls -l /run/secrets
```

* Local Secrets in Docker Compose for local development is done automatically: totally not secure but works out of the box
  * Only works with file based one for now with the `file` key

### Swarm Updates

* Just update the image used to a newer version

```sh
docker service update --image myapp:1.2.1 <servicename>
```

* Adding an environment variable and remove a port

```sh
docker service update --env-add NODE_ENV=production --publish-rm 8080
```

## Docker Stacks

* Stacks accept Compose files for services, networks and volumes
* Stack is only for one Swarm

```sh
docker stack deploy
```

* New `deploy` key in Compose file that is specific to Stacks
* Cannot do the `build` command. It should not happen on your CI system anyway
* Compose ignores `deploy`; Swarm ignores `build`
* `docker-compose` cli not needed on Swarm server

### Commands

* List stacks

```sh
docker stack ls
```

* Remove a stack

```sh
docker stack rm <name>
```

* Deploy a stack

```sh
docker stack -c <config> <name>
```

* Monitoring

```sh
docker stack ps <name>
```

```sh
docker stack services <name>
```

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

## Container Registries

* Secure by default: Docker will not talk to registry without HTTPS
* Except localhost `127.0.0.0/8`

```sh
# Local Container Registry
docker container run -d -p 5000:5000 --name registry registry
docker container run -d -p 5000:5000 --name registry -v $(pwd)/registry-data:/var/lib/registry registry
```

* One needs to tag the image with `host:port/container-name` scheme

```sh
docker tag hello-world 127.0.0.1:5000/hello-world
```

### Commands

* Re-tag an image and push it to your new registry

```sh
docker tag hello-world 127.0.0.1:5000/hello-world
docker push 127.0.0.1:5000/hello-world
```

* Remove image from local cache and pull it from new registry

```sh
docker image remove hello-world
docker imgae remove 127.0.0.1:5000/hello-world
docker pull 127.0.0.1:5000/hello-world
```

### Docker Hub

* The Github of images
* The most popular public image registry
* It also includes some image building

### Docker Store

* The Apple Store of images
* Download Docker "Editions"
* Find certified Docker/Swarm plugins and commercial software

### Docker Cloud

* Web based Docker Swarm creation/management
* CI/CD and Server Ops

## Tips

* Show docker disk usage

```sh
docker system df
```

* Remove unused data

```sh
# Remove everything that is not running
docker system prune
docker image prune
docker container prune
docker volume prune
docker network prune
```

## Best practices

### Best practices for Dockerfiles

- Use the `EXPOSE` command to document that a process in the container
will expose this port. One still needs to expose the port with `-p`
when running the docker container.

## Resources

- [AWS and Docker Tutorial beginner](https://docker-curriculum.com/)
