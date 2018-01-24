# Nvidia Cuda

## Resources

* [Deep Learning Installation Tutorial](http://www.born2data.com/2017/deeplearning_install-part1.html)

## Getting a Working Deep Learning Devbox

### Installing Nvidia Drivers

* Remove previously installed nvidia drivers
```
sudo apt-get remove nvidia*
sudo apt-get autoremove
```
* Download Nvidia Drivers from their website
```
wget http://us.download.nvidia.com/**********/NVIDIA-Linux******************.run ## Copy here the link you have copied
chmod +x NVIDIA-Linux******************.run
```
* Install dependencies
```
sudo apt-add-repository ppa:ubuntu-x-swat/x-updates #if it fails, you may need to repeat the operation.
sudo apt-get update
sudo apt-get install nvidia-current -y
sudo apt-get upgrade -y
```
* Launch Nvidia Driver Installation
```
sudo ./NVIDIA-Linux******************.run
```
* Reboot
```
sudo reboot
```
* Check Nvidia Drivers
```
nvidia-sim

+------------------------------------------------------+
| NVIDIA-SMI ***.**    Driver Version: ***.**          |
|-------------------------------+----------------------+----------------------+
| GPU  Name        Persistence-M| Bus-Id        Disp.A | Volatile Uncorr. ECC |
| Fan  Temp  Perf  Pwr:Usage/Cap|         Memory-Usage | GPU-Util  Compute M. |
|===============================+======================+======================|
|   0  GeForce GTX TIT...  Off  | 0000:05:00.0     Off |                  N/A |
| 22%   59C    P0    74W / 250W |     23MiB / 12284MiB |      0%      Default |
+-------------------------------+----------------------+----------------------+
|   1  GeForce GTX TIT...  Off  | 0000:06:00.0     Off |                  N/A |
| 22%   60C    P0    72W / 250W |     23MiB / 12284MiB |      0%      Default |
+-------------------------------+----------------------+----------------------+
|   2  GeForce GTX TIT...  Off  | 0000:09:00.0     Off |                  N/A |
| 22%   54C    P0    53W / 250W |     23MiB / 12284MiB |      0%      Default |
+-------------------------------+----------------------+----------------------+

+-----------------------------------------------------------------------------+
| Processes:                                                       GPU Memory |
|  GPU       PID  Type  Process name                               Usage      |
|=============================================================================|
|  No running processes found                                                 |
+-----------------------------------------------------------------------------+
```

### Installing Cuda

* Download and install Cuda from Nvidia website
```
# downloading the (currently) most recent version of CUDA 8.0
sudo wget http://developer.download.nvidia.com/******/cuda-repo-ubuntu******.deb #copy here the link obtained above

# installing debian package for CUDA
sudo dpkg -i cuda-repo-ubuntu***************.deb

sudo apt-get update
sudo apt-get install cuda
```
* Add to `.bashrc` the following Environment Variables
```
PATH=/usr/local/cuda-8.0/bin${PATH:+:${PATH}}
LD_LIBRARY_PATH=/usr/local/cuda-8.0/lib64\${LD_LIBRARY_PATH:+:${LD_LIBRARY_PATH}}
```
* Check the installation
```
cat /proc/driver/nvidia/version

NVRM version: NVIDIA UNIX x86_64 Kernel Module  ***.**  ************************
GCC version:  gcc version *.*.* (Ubuntu ****************)
```
```
# installing the samples and checking the GPU
cd /usr/local/cuda-8.0/samples/5_Simulations/nbody
sudo make
./nbody
```

### Installing CuDNN

* Download CuDNN from Nvidia website
```
wget http://developer.download.nvidia.com/*********************************************/cudnn-*.*-linux-x64-*******.tgz
```
* Install CuDNN
```
# unpack the library
tar -xzvf cudnn-8.0-linux-x64-v5.1.tgz

sudo cp cuda/include/cudnn.h /usr/local/cuda/include
sudo cp cuda/lib64/libcudnn* /usr/local/cuda/lib64
sudo chmod a+r /usr/local/cuda/include/cudnn.h /usr/local/cuda/lib64/libcudnn*
```

### Installing Tensorflow

* [TF Installation resource](https://www.tensorflow.org/install/install_linux)

#### Install with virtualenv

* Install pip and virtualenv
```
sudo apt-get install python3-pip python3-dev python-virtualenv # for Python 3.n
```
* Create a virtualenv - `targetDirectory` is `tensorflow` for instance
```
virtualenv --system-site-packages -p python3 targetDirectory  # for Python 3.n
```
* Activate virtualenv
```
source ~/tensorflow/bin/activate
```
* Ensure pip is installed
```
easy_install -U pip
```
* Install tensorflow for GPU
```
pip3 install --upgrade tensorflow-gpu # for Python 3.n and GPU
```
* Check that tensorflow is properly installed and running on GPU
```python3
# Ensuring GPU is enabled

def sample_tf_check():
    import tensorflow as tf
    # Creates a graph.
    with tf.device('/gpu:0'):
        a = tf.constant([1.0, 2.0, 3.0, 4.0, 5.0, 6.0], shape=[2, 3], name='a')
        b = tf.constant([1.0, 2.0, 3.0, 4.0, 5.0, 6.0], shape=[3, 2], name='b')
    c = tf.matmul(a, b)
    # Creates a session with log_device_placement set to True.
    sess = tf.Session(config=tf.ConfigProto(log_device_placement=True))
    # Runs the op.
    print(sess.run(c))

sample_tf_check()
```
