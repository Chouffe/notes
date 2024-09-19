# Pyenv

Manage Python versions.

## Commands

list all the available python versions:

```bash
pyenv install --list
```

install a python version:

```bash
pyenv install 3.10.7
```

show the currently selected python version:

```bash
pyenv version
```

show all versions that are available:

```bash
pyenv versions
```

set a local python version - a `.python-version` file is created:

```bash
pyenv local 3.10.7
```

set a global python version - the env variable `PYENV_VERSION` is updated:

```bash
pyenv global 3.12
```

create a virtualenv using the python version from pyenv:

```bash
pyenv exec python -m venv .venv
```
