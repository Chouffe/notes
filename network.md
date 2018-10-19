# Network

* `Bandwidth`: Maximum rate that information can be transferred. Measured in bits/second
* `Throughput`: The actual rate that information is transferred
* `Latency`: Delay between the sender and the receiver deconding it
* Network speed is bound by the speed of light
* `Bandwidth delay product (bits) = Bandwidth (bits/s) x Latency (s)`
  * Amount of data that can be in transit through a connection
  * `Analogy: Water Pipe`
    * Bandwidth: Flow rate in a water pipe (liter/s)
    * Latency: Time it takes to get the water from one end to an other of the pipe (s)
    * Bandwidth Delay Product: Amount of water in the pipe (liter)

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
| 53          | DNS      |
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
* Return the round trip time elapsed for the ping/pong trip
  * Measurement of performance and locality across the network

### Commands

* ping DNS server 8.8.8.8 three times
```
ping -c3 8.8.8.8
```

## nc - netcat

* Arbitrary TCP and UDP connections and listens
* Thin wrapper around TCP and UDP
* Network Swiss Army Knife
* If it is not possible to get response from a port with `nc`, it will retry with exponential backoff until it exits

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

## ip

* show / manipulate routing, network devices, interfaces and tunnels
* Find your default gateway
```
ip route show default
```

## IP Address

* `IPv4`: `32 = 4*8` bit numeric value
  * Usually written as dotted quad: `127.0.0.1`. Each of the number represent one octet (8 bit value)
  * More than a billion values `2^32`
  * Fewer than the number of people on the planet
* Devices on the same network share a particular prefix
  * They can communicate with each other without going through a router
* find my public IP address

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

## Interfaces

* Interfaces have `0*n` addresses: `eth0`, `wlp2s0`, `lo`, `docker0`, ...
* Multiple interfaces per host
* Loopback interface `127.0.0.1/8`

## Router

* Device that connects two or more IP networks and acts as a gateway

## netstat

* Find the default gateway
```
netstat -nr
```

## NAT

* `NAT`: Network Address Translation
  * The router rewrites the routing between private internal network and outside public network
  * The route maintains a mapping of ports and IPs
* ISPs assign only one IP per household to circumvent the IP number problem
* All devices hide behind only one IP
* Private address netblocks
  * `10.0.0.0/8`
  * `172.16.0.0/12`
  * `192.168.0.0/16`: Most common on home routers, default gateway `192.168.0.1`
* NAT is a workaround not a solution
* Millions of people in the world are using the same private IPs behind different NAT and routers

## TCP

* `What`
  * Communicate between 2 hosts
  * Multiple applications per host
  * In order Delivery
  * Lossless delivery
  * Keeping connections distinct
* `How`
  * IP Layer (addresses + routing)
  * Port Numbers
  * Sequence Numbers
  * Acknowlegment + retransmission
  * Random initial sequence numbers
* The OS sets aside a buffer to use in reassembling packet data
* TCP Flags
  * `[S]`: SYN (synchronize). Opening a new TCP session and a new initial sequence number
  * `[S.]`
  * `[.]`: ACK (acknowledge). It acknowledges that its sender has received data from the other endpoint.
  * `[P.]`: PSH (push). End of a chunk of application data such as an HTTP request.
  * `[F.]`: FIN (finish). Close a TCP connection. The sender is saying that they are finished sending but can still receive data from the other side
  * `[U]`: URG (urgent)
  * `[R]`: RST (reset)

### TCP 3 way handshake

* SYN `[S]`: client sends to a server to open a TCP connection. Contains a randomized sequence number `seq`
* SYN-ACK `[S.]` from the server. It contains a different initial sequence number
* SYN-ACK `[S.]` from the client

### TCP 4 way teardown

* FIN `[F.]` to indicate it is done sending from the client
* ACK `[.]` from the other endpoint
* FIN `[F.]` from the other endpoint
* ACK `[.]` from the client

## HTTP

* `Sequence Diagrams` are used to represent network protocols
* One exchange on the HTTP level can translate to multiple exchanges at the TCP level

## tcpdump

* Dump traffic on a network
* Capture traffic between host and `8.8.8.8`
```
sudo tcpdump -n host 8.8.8.8
```
* monitor traffic on DNS
```
sudo tcpdump -n port 53

# Then if you cause a dns lookup it will show up
ping yahoo.com
```
* Monitor a webserver
```
sudo tcpdump -n port 80
```

## Routers

* They can drop packets if their are congested. That is why there are packet drops to signal congestion

## traceroute

* print the route packets trace to the network host
* Built on top of TTL on the packet
* Sends a packet with TTL 0 to get the first router and get an error message from the router
* Sends a packet with TTL 1 ... TTL K until it reaches the host and get error messages from all routers in between client and host

## Firewalls

* Devices that filter traffic that's coming into or leaving their network
* Common configuration: drop any configuration traffic except traffic to (host, port) pairs that are supposed to be receiving connections from the internet.

## Resources

* [Wikipedia: Network Perfomance](https://en.wikipedia.org/wiki/Network_performance)
