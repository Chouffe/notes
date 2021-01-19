# Jupyter Notebooks

* Web application that combines markdown, math equations, code, visualizations

## AWS Jupyter Notebook for Deep Learning

* Go to AWS Marketplace and select `Deep Learning AMI (Ubuntu) Version 8.0`
* ssh into the instance using the pem file
```
ssh -i "pemfile.pem" ubuntu@ec2-1-2-3-4-5.compute-1.amazonaws.com
```
* Source the tensorflow environement
```
source activate tensorflow_p36
```
* Generate a Jupyter Notebook config file
```
jupyter notebook --generate-config
```
* Set a password for the notebook
```
jupyter notebook password
```
* Start the Jupyter Notebook
```
jupyter notebook --ip=0.0.0.0 --no-browser
```


## Command Line

* Launch the notebook server
```
jupyter notebook
```
* Launch the notebook server on AWS server
```
jupyter notebook --ip=0.0.0.0 --no-browser
```
* Install Notebook Conda to help manage environments
```
conda install nb_conda
```
* List running servers with their tokens
```
jupyter notebook list
```
* Setup a Jupyter Notebook Password
```
jupyter notebook password
```

## UI

### Command palette

* It brings up a little pannel where commands can be searched

### Modes

* **Edit Mode**: Type into cells
  * Left border is green
* **Command Mode**: Execute commands
  * Left border is blue

### Keyboard Shortcuts

#### Command Mode

* `Enter/Esc`: Switch between edit/command modes
* `Enter + Shift`: Go to next cell
* `h`: List of shortcuts
* `s`: Save notebook
* `a`: Create cell above
* `b`: Create cell below
* `dd`: Delete cell
* `y`: Change from markdown to code
* `m`: Change from code to markdown
* `p`: Access the command palette

### Code Cells

### Markdown Cells

* cf markdow.md
* Math expressions using LaTeX symbols
  * Notebooks use MathJax to render the LaTeX symbols as math symbols

## Magic Keywords

* Special commands that lets you control the notebook itself, perform system calls
* `%`: Line Magic
* `%%`: Cell Magic

Make autocomplete work again in Jupyter Notebooks - https://stackoverflow.com/a/64554305/791795
```
%config Completer.use_jedi = False
```

### Python Kernel

* [List of all magic commands](http://ipython.readthedocs.io/en/stable/interactive/magics.html)
* Timing Code
  * Function call: `%timeit f(*args)`
  * Entire Cell: `%%timeit ...`
* Embedding visualizations in notebooks: `%matplotlib`
  * Inline: ` %matplotlib inline`
  * High quality: `%config InlineBackend.figure_format = 'retina'`
```
%matplotlib inline
%config InlineBackend.figure_format = 'retina'

import numpy as np
...

plt.plot()
```
* Debugging in Notebooks: `%pdb`
  * When an error occurs, variables, scopes can be inspected
  * [pdb Documentation](https://docs.python.org/3/library/pdb.html)
  * [pdb commands](https://docs.python.org/3.5/library/pdb.html#debugger-commands)
* Documentation: Prefix the method by `?`
```python
?str.replace()
```
* Time a cell
```python
%%time
```

## Converting Notebooks

* Default, big JSON files with `.ipynb` extension
* Convert to HTML file
```
jupyter nbconvert --to html notebook.ipynb
```
* [nbconvert Documentation](https://nbconvert.readthedocs.io/en/latest/usage.html)

## Slideshows

* [Example](http://nbviewer.jupyter.org/format/slides/github/jorisvandenbossche/2015-PyDataParis/blob/master/pandas_introduction.ipynb#)
* Regular notebooks, designate which cells are slides and the type of the slide cell
* Export to slideshow
```
jupyter nbconvert notebook.ipynb --to slides
```
* Serve the presentation
```
jupyter nbconvert notebook.ipynb --to slides --post serve
```

## Resources

* [Tips and Tricks](https://www.dataquest.io/blog/jupyter-notebook-tips-tricks-shortcuts/)
