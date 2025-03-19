# Docker Stack

* Stacks accept Compose files for services, networks and volumes
* Stack is only for one Swarm

```sh
docker stack deploy
```

* New `deploy` key in Compose file that is specific to Stacks
* Cannot do the `build` command. It should not happen on your CI system anyway
* Compose ignores `deploy`; Swarm ignores `build`
* `docker-compose` cli not needed on Swarm server

## Commands

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

* Scaling the service/app

```sh
# Edit the number of replicas in your docker-compose.yml file
# Edit the config in general

# Perfom an in-place update
$ docker stack deploy -c docker-compose.yml <image
```
