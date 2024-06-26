# IPython

## Extensions

* Introduction and overview of IPython's features

```sh
?
```

* Detail about `object`

```sh
object?
```

* Extra detail about `object`

```sh
object??
```

## Magic commands

* List all the magic commands available:

```sh
%lsmagic
```

* Show a quickreference sheet:

```sh
%quickref
```

* Reload all modules every time before executing the Python code typed:

```sh
%load_ext autoreload
%autoreload 2
```

* List the commands ran during the session:

```sh
%history
%hist
```

* List environment variables:

```sh
%env
```

* Set environment variables:

```sh
%set_env ENV_NAME=ENV_VALUE
```

* Time execution of a python statement:

```sh
%time
```

* Run the pip package manager within the current kernel:

```sh
%pip
```

* Print all interactive variables:

```sh
%who
```

* Run external python file:

```sh
%run filename.py
```

* Display the content of external file:

```sh
%pycat filename.py
```

* Object detailed information:

```sh
a = "hello world"
%pinfo a
```

* Paste multiple lines as a block:

```sh
%paste
```
