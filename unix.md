# Unix

## Input/Output Redirection

* `>`: put the standard output into the file
* `>>`: append the standard output to the file
* `<`: read the standard input from the file

## Processes

* Find the limits of a process
```
cat /proc/<PID>/limits
```
* Find a PID by name
```
ps aux | grep name
# Or
pgrep name
```
* Find all processes for the current group
```
ps -ag
```
* Run a process that is not attached to the terminal
```
nohup <command> &
```

## Filesystem

* Display file content in octal, decimal or hexadecimal format
```
od -cb <filename>
```

### Permissions

| mode | octal | file    | directory   |
| ---- | ----- | ------- | ----------- |
| r    | 1     | read    | read        |
| w    | 2     | write   | edit/delete |
| x    | 4     | execute | search      |

* A file has a set of permission associated with it
```
$ ls -l /etc/passwd
-rw-r--r-- root root
# group permission (-rw-) | user permission (r--) | other permission (r--) | owener | group
```
* A directory is prefixed with `d`
```
$ ls -ld /
drwxr-xr-x 26 root root
```
* Allow all users to read and execute the file
```
chmod a+rx file
```
* Allow the user who owns the file to execute it
```
chmod u+x file
```
* Turn off write permission for the group
```
chmod g-w file
```
* If a directory is writable, anyone can remove/edit files regardless of the persmission on the files themselves

### Inodes

Administrative information is stored in the inode along with its location on disc, how long it is.
Symbolic links point to the underlying inode of the file.
```
$ ln old_file new_file
$ ls -li
7105506 -rw-rw-r-- 2 chouffe chouffe 59 Jun 15 13:29 new_file
7105506 -rw-rw-r-- 2 chouffe chouffe 59 Jun 15 13:29 old_file
```

### /dev/null

* Discard output
```
command > /dev/null
```

## I/O redirection

* `> file`: writes standard output to `file`
* `2>&1`: writes standard error (2) to standard output (1)
* `> file 2>&1`: writes standard output and error to `file`
* `&> out`: writes standard error and standard output to `out`

* Shell jargon example
```
grep "$*" <<EOF
command1
command2
command3
EOF
