# DVC

Data Version Control.

## Common commands

Initialize dvc - needs to be inside a git repo:

```sh
dvc init
```

Add remote storage:

```sh
dvc remote add -d remote_storage path/to/your/dvc_remote
```

Test adding remote storage using `/tmp/`:

```sh
dvc remote add -d remote_storage /tmp/local_dvc_storage/
```

Turn off analytics collection:

```sh
dvc config core.analytics false
```

Track data files or folder with dvc:

```sh
dvc add data/raw/train
```

It will do the following operations:

- Add `data/raw/train` to `.gitignore`
- Create a file `data/raw/train.dvc` containing md5, nfiles and other
informations about the tracked data
- Copy `data/raw/train` to a staging area - a cache located in `.dvc/cache`

Push data to the remote storage:

```sh
dvc push
```

Get the data from the remote storage:

```sh
dvc checkout data/raw/train.dvc
```

On a freshly cloned repo, one can fetch all data from the remote in cache:

```sh
dvc fetch
```

Or fetch only some files:

```sh
dvc fetch data/raw/val.dvc
```

To combine fetching and checking out:

```sh
dvc pull
```

Modify content:

1. Unlink the file/folder with `dvc unprotect`: `dvc unprotect data/raw/train`
2. Update the data or download new one
3. Add the file/folder back to dvc: `dvc add data/raw/train`

___Note___: Often, it is better to version the raw data and just track a new
version of this. Consider the raw data as immutable.

Commit new data: Record changes to files or directories tracked by DVC.

```sh
dvc commit
```

## Pipelines & DAGs

DVC makes it possible to execute data pipelines and track generated artificats.

```sh
dvc stage add \
  -n prepare \                 # name
  -d src/prepare.py \          # deps
  -d data/raw \                # deps
  -o data/prepared/train.csv \ # outs
  -o data/prepared/test.csv \  # outs
  python src/prepare.py        # cmd 
```

which is equivalent to this section in the `dvc.yaml` file:

```yaml
stages:
  prepare:
    cmd: python src/prepare.py
    deps:
      - data/raw
      - src/prepare.py
    outs:
      - data/prepared/test.csv
      - data/prepared/train.csv
```

## Misc

The configuration file is located in `.dvc/config`.
Use a share cache when multiple repos points to the same data files: `dvc cache dir path/to/shared_cache` and `mv .dvc/cache/* path/to/shared_cache`.
