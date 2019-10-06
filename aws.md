# AWS

## CloudFormation

### Best practices

* Use cross stack references to reference a VPC or subnets
* Make sure the CF template is environment (dev, prod) and region agnostic
* Never encode credentials in a CF template (use KMS or parameters)
* use CFN init
* Validate templates
* Do not do anything manually
* Verify changes with changesets
* Use DeletionPolicy to prevent valuable components from being deleted
* Get inspiration from: https://github.com/awslabs/aws-cloudformation-templates

### DeletionPolicy

* `Delete`: AWS deletes the resource and all its content (does not apply to S3)
* `Retain`: AWS keeps the resource without deleting it
* `Snapshot`: AWS creates a snapshot for the resource before deleting it (works for some stateful resources)

### Drift

We can find out if a resource has been manually changed (from outside CF itself).

### Helper Scripts

* `cfn-init`: retrieve and interpret the resource metadata, installing packages, creating files and starting services
* `cfn-signal`: signal a CF CreationPolicy or WaitCondition to synchronize other resources in the stack
* `cfn-get-metadata`: retrieve all metadata defined for a resource
* `cfn-hup`: daemon to check for updates to metadata and execute custom hooks when changes are detected

The metadata: `AWS::CloudFormation::Init` allows to define groups, users, create files (and then be called by `cfn-init`)

Logs are kept on the instance in:
* `/var/log/cloud-init-output.log` for `ec2-user-data`
* `/var/log/cfn-init.log` for `cfn-init`

### Former2

* Generate CF templates from existing resources: https://former2.com/

## Lambda

* Billing: per request + compute time

### Serverless

* Setup for profile `serverless-admin`
```
serverless config credentials --provider aws --key $ACCESS_KEY --secret $SECRET_ACCESS_KEY --profile serverless-admin
```
* Add provider information in `serverless.yml`
```
provider:
  name: aws
  runtime: python2.7
  profile: serverless-admin
  region: us-east-1
  ...
```
* Create from a template
```
sls create --template aws-python --path hello-world-python
```
* Deploy the stack
```
sls deploy -v
```
* Deploy only the function
```
sls deploy -f function-name
```
* Invoke
```
sls invoke -f function-name
```
* Retrieve logs
```
sls logs -f hello
```
* Retrive and tail logs
```
sls logs -f hello -t
```
* Remove function
```
sls remove
```

## RDS

* SQL, MySQL, PostgreSQL, Oracle, Aurora, MariaDB
* Multi-AZ: for disaster recovery
* Read Replicas - for performance

## Redshift

* Online Analytics Processing (OLAP) with Redshift: Data warehouse solution
* Used for Business Intelligence or Data Warehousing

## ElastiCache

* In memory cache in the cloud - Redis, Memcached

## EC2

### Placement Groups

* Clustered: single AZ - low network latency, high throughput
* Spread: each instance is placed on distinct underlying hardware - protect from hardware failures for individual critical EC2 instances
* Partitioned: same as spread but with partitions - protect from failures for multiple Ec2 instances (HDFS, HBase, Cassandra)

### Instance meta data

* When sshed into an EC2 instance, one can run the following command to get instance metadata and user-data
```
$ curl http://169.254.169.254/latest/user-data/
$ curl http://169.254.169.254/latest/meta-data/
ami-id
ami-launch-index
ami-manifest-path
block-device-mapping/
events/
hostname
identity-credentials/
instance-action
instance-id
instance-type
local-hostname
local-ipv4
mac
metrics/
network/
placement/
profile
public-hostname
public-ipv4
public-keys/
reservation-id
security-groups
services/
```

## EBS

* Elastic Block Store: Virtual Hard disk
* EBS Snapshots
  * Point in time copy of a disk
  * Live on S3
  * Incremental
* AMIs can be created from Volumes and Snapshots
* EBS volumes can be changed on the fly
  * Storage type
  * Storage size

## EFS

* Elastic File System: Storage service for EC2 instances
  * This can be mounted on different EC2 instances unlike EBS
  * Storage capacity grow/shring automatically
* Read after write consitency

## CloudFront

* Edge Location: Location where the content will be cached
* Origin: origin of all the files the CDN will distribute (S3 bucket, Route53, EC2 instance, ...)
* Objects are cached for TTL
* Cache invalidation can be performed (charges apply)

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

## VPC

* When creating a VPC from the web console, it creates also the following resources
  * Default security group
  * Default Route table
  * Default Network ACL
* It will not create any subnets nor will it create a default internet gateway
* Amazon always reserve 5 IP addresses within a subnet
* Only 1 internet gateway per VPC

### Security Groups

* To allow ping from one instance to another, enable ICMP for the given subnet

### NAT instances (obsolete) & NAT gateways

* When creating a private subnet in a VPC, we could still want the instance to have access to the internet (downloading software) and still making it not possible to ssh into directly
* NAT instances and gateways must be in a public subnet
* There must be a route out of the private subnet to the NAT instance or gateway

### ACLs

* ACL: Access Control List
  * List of DENY/ALLOW rules executed in order
* Default VPC comes with a default ACL which allows all outbound and inbound traffic
* IP addresses can be blocked with ACLs (not with Security Groups)
* A subnet can only be associated with one ACL at a time
* Network ACLs are stateless: responses to allowed inbound traffic are subject to the rules for outbound traffic

### ALB

* At least two public subnets are required to launch an ALB

### VPC Flow logs

* Capture information about the IP traffic going to and from the VPC. Flow log data is stored using Amazon CloudWatch Logs or S3 bucket
* Can be created at 3 levels
  * VPC
  * Subnet
  * Network Interface

### Bastions

A bastion is used to securely administer EC2 instances (using SSH). They are also called jump boxes.
