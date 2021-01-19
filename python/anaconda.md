# Anaconda / Miniconda

## Package Management

* `conda` is not python specific
* similar to `pip` but focused around data science
* Not all python libraries are available from the Anaconda distribution and conda
* Pip can be used alongside conda as well

### Commands

* Install a package
```
conda install $package
```
* Install specific version of a package
```
conda install numpy=1.10
```
* Remove package
```
conda remove numpy
```
* Update package
```
conda update numpy
```
* Update all packages in an environment
```
conda update --all
```
* Search for a package
```
conda search $search_term
```
* List installed packages
```
conda list
```

## Virtual Environments

* Conda is a virtual environmen manager
* Similar to `virtualenv`, `pyenv`, ...

### Commands

* Create an environment
```
conda create -n $env_name
```
* Create an environment with packages
```
conda create -n $env_name numpy pandas scipy
```
* Create an environment with a specific python version
```
conda create -n $env_name python=3.3
```
```
conda create -n $env_name python=3
```
* Cloning an environment
```
conda create --name tensorflow_p36_clone --clone tensorflow_p36
```
* List environments
```
conda env list
```
* Entering an environment
```
source activate $my_env
```
* Leaving an environment
```
source deactivate
```
* Exporting an environment
```
conda env export > environment.yaml
```
* Importing an environment
```
conda env create -f environment.yaml
```
* Removing an environment
```
conda env remove -n $env_name
```
* Clone Environment
```
conda create --clone environment_name -n name_cloned_environment
```

## Jupyter

* Register the conda environment to the kernel
```
# Install the ipykernel package
pip install ipykernel
# Register it from withing the activated env
python -m ipykernel install --name=<$NAME>
```
* List all kernels
```
python -m jupyter kernelspec list
```
* Remove a kernel
```
python -m jupyter kernelspec uninstall <$NAME>
```

## SageMaker JupyterLab

* Create a conda environment from yaml file
```
bash
cd /folder/containing/environment.yaml
conda env create -f environment.yaml
conda activate <$ENV_NAME>
# Install the ipykernel package if not already installed
pip install ipykernel
# Register it as a Kernel - may need to "sudo"
python -m ipykernel install --name=<$NAME>
```

## Best practices

* Use separate environments for python 2 and python 3
```
conda create -n py2 python=2
```
```
conda create -n py3 python=3
```
* Create an environment for each project
* Include a pip `requirements.txt` file using `pip freeze` for people not using anaconda
* Install notebook conda package to manage packages from GUI
```
conda install nb_conda
```

## Resources

* [Conda: Myths and Misconceptions](https://jakevdp.github.io/blog/2016/08/25/conda-myths-and-misconceptions/)
* [Conda Documentation](https://conda.io/docs/user-guide/tasks/index.html)
