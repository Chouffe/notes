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
* Rename file `filename.init` to `filename.final`
```
mv filename.{init,final}
```
* Move multiple files to a directory
```
mv file1 file2 file3 directory
```

## echo

Echo does nothing more than echo its arguments
```
# Useful for experimenting with pattern matching
$ echo picture*
picture1 picture2 picture3
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
# rync SRC DEST
rsync -av ./Backup\ Data1/ /media/chouffe/Backup\ Data
rsync -av /media/chouffe/Backup\ Data /media/chouffe/Backup\ Data1
```

## nc

* Listen to an UDP connection on port 8125 (datadog agent)
```
nc -u -l 8125
```

## ss

Socket Stats
* Get stats about open ports by type
```
$ ss -s
Total: 167 (kernel 0)
TCP:   22356 (estab 66, closed 22272, orphaned 0, synrecv 0, timewait 22271/0), ports 0

Transport Total     IP        IPv6
*         0         -         -
RAW       0         0         0
UDP       16        11        5
TCP       84        72        12
INET      100       83        17
FRAG      0         0         0
```
* List all socket connections
```
ss -tan state time-wait
```
* [TCP TIME-WAIT guide](https://vincent.bernat.ch/en/blog/2014-tcp-time-wait-state-linux)

## Cron

* Add a Cron job
```
sudo crontab -e
```
* Restart cron daemon with latest data
```
service crond restart
```

## Base64

* Encode base64
```
python -m base64 -e <<< "sample string"
```
* Decode base64
```
python -m base64 -d <<< "dGhpcyBpcyBlbmNvZGVkCg=="
```

## htop

* [htop explained](https://peteris.rocks/blog/htop/)
* Memory Usage
  * Green: Used memory
  * Blue: Buffer
  * Orange: Cache
* Virtual Image, VIRT - not a useful number most of the time
> The total amount of virtual memory used by the task. It includes all code, data and shared libraries plus pages that have been swapped out and pages that have been mapped but not used.

* Sort by CPU, MEMORY, TIME: `P, M, T`

## Misc

* Kill a process listening on a given port
```
kill -9 $(lsof -t -i:8080)
```
* [Newbie Traps](https://wiki.bash-hackers.org/scripting/newbie_traps)
* [The Art of the command line](https://github.com/jlevy/the-art-of-command-line)
* [Blissful Bash](https://github.com/pesterhazy/blissful-bash)
* [Pure bash bible](https://github.com/dylanaraps/pure-bash-bible)
* [Bash Handbook](https://github.com/denysdovhan/bash-handbook)
* [Bash Guide](https://github.com/Idnan/bash-guide)
* [ShellGuide](https://google.github.io/styleguide/shellguide.html)
* [The Bash Hacker Wiki](https://wiki.bash-hackers.org/start)
