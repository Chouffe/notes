# Network

## Tools

* Install tools
```
sudo apt-get install netcat-openbsd tcpdump traceroute mtr
```

## Ports

* Port Number mapping
* One program per port

| Port Number | Protocol |
| ----------- | -------- |
| 22          | SSH      |
| 80          | HTTP     |
| 443         | HTTPS    |

* Highest port number: `2^16 - 1 = 65535` [Encoded on 16 bits]
* Lowest port number: `1`
* The port range a non-root user can listen on is `[1024-65535]`
* One cannot listen twice on the same port

## ping

* Send `ICMP ECHO_REQUEST` to network hosts
* Test whether the computer can send and receive packets through the internet
* `ping` is simpler than HTTP. The server does not require to run any server
* When `ping` is successful
  * The computer has Internet access
  * The computer at 8.8.8.8 is up and running
  * The ISP knows how to send traffic to 8.8.8.8

### Commands

* ping DNS server 8.8.8.8 three times
```
ping -c3 8.8.8.8
```

## nc - netcat

* Arbitrary TCP and UDP connections and listens
* Thin wrapper around TCP and UDP
* Network Swiss Army Knife

### Commands

* Connect to the wikipedia web server
```
nc en.wikipedia.org
```
* Connect to ssh port on localhost
```
nc localhost 22
```
* Connect to an email server
```
nc gmail-smtp-in.l.google.com 25
```
* Listen on port 1234
```
nc -l 1234
```
* Connect on localhost port 1234
```
nc 127.0.0.1 1234
```
* Scan opened port on localhost in range 1-100
```
nc -zv localhost 1-100
```
* Sending HTTP Headers
  * Not specifying `Host:` int the header will return the home page
* Redirect to `eff.org` when pointing the browser to localhost:2345
```
printf 'HTTP/1.1 302 Moved\r\nLocation: https://www.eff.org/' | nc -l 2345
```

## lsof

* list open files, including network sockets

### Commands

* List only network sockets
```
sudo lsof -i
```

## DNS

* `DNS`: Domain Name System
* Distributed directory with Caching
* Root servers direct request to specific domain requests
* Global Top Level Domain `gTLD` (com)
* If the DNS goes down, the site cannot be reached for most users
* Local caching server recursively redirect requests to the appropriate name servers and then store the record in the cache
  * `TTL` can be seen in the response from `dig`
* Domains are structured as trees: `com -> google -> gmail -> www`

### DNS Record Types

* `CNAME`: Canonical Name Record. Alias of one name to another: the DNS lookup will continue by retrying the lookup with the new name.
* `AAAA`: IPv6 address. `4*32 = 128` bits
* `A`: IPv4 address. `32` bits
* `NS`: Name Server Record. Delegates a DNS zone to use the given authoritative name servers

## host

* DNS lookup utility
* DNS Lookup A-Record
```
host -t a google.com
```
* DNS Lookup AAAA-Record
```
host -t aaaa google.com
```

## dig

* DNS lookup utility
* Less human readable but more usable for scripts

## IP Address

* `IPv4`: `32 = 4*8` bit numeric value
  * Usually written as dotted quad: `127.0.0.1`. Each of the number represent one octet (8 bit value)
  * More than a billion values `2^32`
  * Fewer than the number of people on the planet
* Devices on the same network share a particular prefix
  * They can communicate with each other without going through a router

## Network Block

* The top and the bottom addess of a netblock are reserved
* The first address is usually the router
* `198.51.100.0/24`: 24 bit network part, 8 bit host part
  * `2^8 - 3` available IPv4 addresses
* `Subnet mask`: An other way to represent a network size
  * ones on the left
  * zeroes on the right
  * Eg. /24 network `255.255.255.0`
  * Eg. /16 network `255.255.0.0`
  * Eg. `ff.ff.ff.00` in hex
