# Nix: Package Manager

## Basic commands

Check to see if [pkg] is available through Nix, showing its Nix attribute path if available
```
nix-env -qaP [pkg]
```

Install a package by its attribute path [attr]
```
nix-env -iA [attr]
```

See installed packages
```
nix-env -q
```

Update [pkg], add --dry-run to see what would be installed without actually updating
```
nix-env -u [pkg]
```

Uninstall [pkg]
```
nix-env -e [pkg]
```

Garbage collector to actually delete packages
```
nix-store --garbage-collector
nix-collect-garbage
```

See the status of available packages, wheter they are installed into the user environment and/or present in the system
```
nix-env -qas
```

```
-PS bash-3.0
--S binutils-2.15
IPS bison-1.875d
```

* I: installed in your current environment?
* P: present on your system?
* S: is there a substitue for the package?

List generations
```
nix-env --list-generations
```

Switch to generation 22
```
nix-env --switch-generation 22
```

List subscribed channels
```
nix-env --list
```

## Profiles

List generations
```
nix-env --list-generations
```

Switch profile
```
nix-env --switch-profile /nix/var/nix/profiles/my-profile
nix-env --switch-profile /nix/var/nix/profiles/default
```
