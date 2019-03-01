# AWS

## Elastic Cache

* Easier to migrate off of EB with
* Automatically creates and maintains Redis instances
* Built in logging and maintenance
* Probably better security that I can do

## RDS

* Automated backups and rollbacks
* Easier to migrate off of EB with
* Automatically creates and maintains Postgres instances
* Built in logging and maintenance
* Probably better security that I can do

## Elastic Beanstalk - EB

### Dockerrun.aws.json

* At least one `containerDefintions` must be marked as essential
```json
{
  "AWSEBDockerrunVersion": 2,
  "containerDefinitions": [
    {
      "name": "client",
      "image": "chouffe/multi-client",
      "hostname": "client",
      "essential": false
    },
    {
      "name": "server",
      "image": "chouffe/multi-server",
      "hostname": "api",
      "essential": false
    },
    {
      "name": "worker",
      "image": "chouffe/multi-worker",
      "hostname": "worker",
      "essential": false
    },
    {
      "name": "nginx",
      "image": "chouffe/multi-nginx",
      "essential": true,
      "portMappings": [
        {
          "hostPort": 80,
          "containerPort": 80
        }
      ],
      "links": ["client", "server"]
    }
  ]
}
```
