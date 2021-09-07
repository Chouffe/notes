# Kedro

* Setup an env for a kedro project
```
conda create --name project-env python=3.7 -y
pip install kedro
# Install some linters and checkers
conda install black flake8
```

* Create a new project
```
kedro new
```
* Install dependencies
```
# From a fresh new command
kedro install
# Rebuild dependencies
kedro install --build-reqs
```
* Run a pipeline by name:
```
kedro run --pipeline ds
```
* Load manually the kedro `context`
```py
from pathlib import Path
from kedro.framework.session import KedroSession
from kedro.framework.session.session import _activate_session

current_dir = Path.cwd()
project_path = current_dir
project_path
session = KedroSession.create("slide_types", project_path)
_activate_session(session)
context = session.load_context()
```
