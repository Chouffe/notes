# lsof

Lists open files and the corresponding processes

* Show process that use internet connection at the moment
```
lsof -P -i -n
```

* Show process that use a specific port number
```
lsof -i tcp:443
```

* Lis all listening ports together with the PID of the associated process
```
lsof -Pan -i tcp -i udp
```

* List all open ports and their owning executables
```
lsof -i -P | grep -i "listen"
```

* Show all open ports
```
lsof -Pnl -i
```

* Show open ports (LISTEN)
```
lsof -Pni4 | grep LISTEN | column -t
```

* List all files opened by a particular process
```
lsof -c "process"
```

* Show current working directory of a process
```
lsof -p <PID> | grep cwd
```
