# Docker Swarm

* initialize a swarm
  * PKI and security automation
  * Raft DB created to store root CA, configs and secrets

```sh
docker swarm init
```

## Nodes

* List nodes

```sh
docker node ls
```

## Service

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

## Secrets Storage

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

## Swarm Updates

* Just update the image used to a newer version

```sh
docker service update --image myapp:1.2.1 <servicename>
```

* Adding an environment variable and remove a port

```sh
docker service update --env-add NODE_ENV=production --publish-rm 8080
```
