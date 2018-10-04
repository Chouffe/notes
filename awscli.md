# AWS CLI

## S3

### Commands

* List a bucket
```
aws s3 ls bucket-name
```
* Copy to a bucket with ACL public-read
```
aws s3 cp hello.txt s3://bucket-name/ --acl public-read
```
* Sync to a bucket with ACL public-read
```
aws s3 sync folder-name  s3://bucket-name/ --acl public-read
```
* Remove a file from a bucket
```
aws s3 rm s3://bucket-name/hello.txt
```
* Remove recursiverly from a bucket
```
aws s3 rm s3://bucket-name --recursive
```

### Resources

* [AWS S3 Command Line](https://docs.aws.amazon.com/cli/latest/userguide/using-s3-commands.html)
