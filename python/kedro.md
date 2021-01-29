# Kedro

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
session = KedroSession.create("aws_rekognition_ocr", project_path)
_activate_session(session)
context = session.load_context()
```
