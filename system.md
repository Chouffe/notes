# System

## Ports

A port is a communication endpoint.
At the software level, within an operating system, a port is a logical
construct that identifies a specific process or a type of network service.

A port number is always associated with an **IP address** of a **host** and the
**protocol type** of the communication.

The lowest numbered 1024 port numbers identify the historically most commonly
used services, and are called the well-known port numbers. Higher-numbered
ports are available for general use by applications and are known as ephemeral
ports.

A port number is a 16-bit unsigned integer, thus ranging from `0 to 65535`.

| Port Number | Assignment          |
| ----------- | ------------------- |
| 20          | FTP Data Transfer   |
| 21          | FTP Command Control |
| 22          | SSH                 |
| 23          | Telnet              |
| 25          | SMTP                |
| 53          | DNS                 |
| 67, 68      | DHCP                |
| 80          | HTTP                |
| 123         | NTP                 |
| 143         | IMAP                |
| 443         | HTTPS               |
