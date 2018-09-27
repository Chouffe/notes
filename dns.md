# DNS

## Change local DNS

* Edit `/etc/resolv.conf`
```
nameserver $IP_ADDR
```
* Test that DNS is working properly
```
host google.com
```

## Add custom host records

* Edit `/etc/hosts`
```
$IP_ADDR $DNS
```
* Example
```
192.168.0.97 pi.hole
```
* Test that it works: browser/ping
