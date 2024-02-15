# SSH

* GPG can be used instead of SSH
```
# .gnugpg/gpg-agent.conf
default-cache-ttl 600
max-cache-ttl 7200
enable-ssh-support
write-env-file ~/.gpg-agent-info

# gpg.conf
use-agent

# In zsh profile
export SSH_AUTH_SOCK=$(gpgconf --list-dirs agent-ssh-socket)
```
* Display public key of SSH and add it to services like AWS or github
```
ssh-add -L
```
* Use a bastion host to scp files
```
scp -o ProxyCommand="ssh user@bastion  nc localhost 22" files_to_scp $remote_host:$remote_host_path
```
