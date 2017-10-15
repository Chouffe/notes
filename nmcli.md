# NMCLI

## Getting help

Getting help:
```
nmcli help
```

Getting help on the general section:
```
nmcli general help
```

## Connections

Show all connections:
```
nmcli connection show
```

Show all active connections:
```
nmcli connection show --active
```

Delete a network configuration:
```
nmcli connection delete UUID
```

Activate a connection
```
nmcli connection up UUID
```

## Devices

List all the devices
```
nmcli device
```

## How to connect to a wifi access point

List all the available access point:
```
nmcli device wifi
```

Rescan for available access points:
```
nmcli device wifi rescan
```

Connect to a wifi access point specified by a SSID (EEE)
```
nmcli device wifi connect EEE password '123456...'
```

## Resources

* [RedHat tutorial](https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/7/html/Networking_Guide/sec-Using_the_NetworkManager_Command_Line_Tool_nmcli.html)
* [Getting started tutorial](https://www.certdepot.net/rhel7-get-started-nmcli/)
