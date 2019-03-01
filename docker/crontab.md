# Docker - Crontab

* Run a crontab with a Dockerfile with `crond -f -d 8`
```crontab
* * * * * echo "hello stackoverflow" > /proc/1/fd/1 2> /proc/1/fd/2
# remember to end this file with an empty new line
```
```Dockerfile
FROM alpine:3.6

# copy crontabs for root user
COPY crontab /etc/crontabs/root

# start crond with log level 8 in foreground, output to stderr
CMD ["crond", "-f", "-d", "8"]
```

## Resources

* https://stackoverflow.com/questions/37458287/how-to-run-a-cron-job-inside-a-docker-container/37458519#comment84898391_37458519
