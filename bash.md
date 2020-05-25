# Bash

## Cheatsheet

* Get some command examples
```
curl cheat.sh/command
```

## Tips and Tricks

* [Strict Mode](http://redsymbol.net/articles/unofficial-bash-strict-mode/): always start your scripts with the following
```
#!/usr/bin/env bash
set -euo pipefail
```
* Situated bash scripts
```
#!/usr/bin/env bash
set -euo pipefail && cd "$(dirname "${BASH_SOURCE[0]}")/.."
```
* Generate a UUID
```
id="$(python -c 'import uuid; print uuid.uuid4()')"
```
* Verify that an env variable is set
```
[[ -z "${MY_VAR-}" ]] && { echo You need to set MY_VAR >&2; exit 1; }
```
* Verify that a command is installed
```
command -v wget >/dev/null 2>&1 || { echo "Command wget is not installed." >&2; exit 1; }
```

* Loop through dates

```bash
d=2016-06-03
while [ "$d" != 2016-06-04 ]; do
  # Do something with date $d
  echo $d
  # Increment date $d
  d=$(date -I -d "$d + 1 day")
done
```
* Generate a UUID
```bash
uuidgen
```

## date

* Get the formatted datetime - Can be used for daily backups for instance
```
NOW=$(date +"%m-%d-%Y")
```
* Example script
```
#!/bin/bash
NOW=$(date +"%m-%d-%Y")
FILE="backup.$NOW.tar.gz"
echo "Backing up data to /nas42/backup.$NOW.tar.gz file, please wait..."
# rest of script
# tar xcvf /nas42/backup.$NOW.tar.gz /home/ /etc/ /var
```

## mv

* Move 1000 files from one directory to another
```bash
 mv -- *([1, 1000]) /src/directory/
 ```
* Move 1000 files from one directory to another with a given prefix
```bash
 mv -- prefix*([1, 1000]) /src/directory/
```

## printf

* Formats and prints data
* similar to `echo` but can turn `\n` into actual line breaks and format data
```
printf 'HEAD / HTTP/1.1\r\nHost: en.wikipedia.org\r\n\r\n'
```

## rsync

* backup between different Hard Drives
```
rsync -av ./Backup\ Data1/ /media/chouffe/Backup\ Data
```

## nc

* Listen to an UDP connection on port 8125 (datadog agent)
```
nc -u -l 8125
```

## Cron

* Add a Cron job
```
sudo crontab -e
```
* Restart cron daemon with latest data
```
service crond restart
```

## Misc

* Kill a process listening on a given port
```
kill -9 $(lsof -t -i:8080)
```
* [The Art of the command line](https://github.com/jlevy/the-art-of-command-line)
* [Blissful Bash](https://github.com/pesterhazy/blissful-bash)
* [Pure bash bible](https://github.com/dylanaraps/pure-bash-bible)
* [Bash Handbook](https://github.com/denysdovhan/bash-handbook)
* [Bash Guide](https://github.com/Idnan/bash-guide)
