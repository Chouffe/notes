# AWS

## S3

* Files can be from 0 Bytes to 5 TB
* Unlimited Storage
* S3 is a universal namespace

### Guarantees

* Read after Write consistency for PUTS of new objects
* Eventual consistency for DELETEs and PUT overwrites

### Storage Classes

* S3 Standard
  * Guarantees
    * 99.99% availability
    * 11x9 durability
* S3 Infrequently Accessed
  * Lower fee than S3 but charged a retrieval fee
* S3 One Zone Infrequently Accessed
  * Do not require the Multiple Availability Zone data resilience
* S3 Intelligent Tiering
  * Move objects based on usage patterns to optimize for cost
* S3 Glacier
  * For archiving
  * Long retrieval time (from minutes to hours)
* S3 Glacier Deep Archive
  * Lowest cost S3 storage class
  * Retrieval time of 12 hours

* Amazon S3 Transfer Acceleration
  * enables fast transfers of files over long distance between end users and S3 buckets with CloudFront

### Buckets

* By default, all buckets are private
* Access control can be setup through Bucket Policies or Access Control Lists
* Encryption at rest can be done with
  * S3 Managed Keys
  * AWS Key Management Service
  * Server Side Encryption with Customer Provided Keys

### Versioning

* Stores all versions of an object (including all writes and deletes)
* Once enabled on a bucket, it cannot be disabled (only suspended)

## CLI

* Install the zsh plugin for autocompletion
* [Advanced AWS CLI query tricks](https://opensourceconnections.com/blog/2015/07/27/advanced-aws-cli-jmespath-query/)

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
