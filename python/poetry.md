# Poetry

## Install

To install poetry, install `pipx` and then run:

```sh
pipx install poetry
```

## Common commands

Create a new project/package:

```sh
poetry create mypackage
```

Add a new dependency:

```sh
poetry add numpy
```

Add a dev dependency:

```sh
poetry add --group dev dvc
```

Install the dependencies listed in the pyproject.toml

```sh
poetry install
```
