# MXNET

* Run Docker Container for mxnet and Clojure
```
docker run \
       --rm \
       --env="DISPLAY" \
       --env="QT_X11_NO_MITSHM=1" \
       --volume="/tmp/.X11-unix:/tmp/.X11-unix:rw" \
       --volume "$PWD:/home/mxnetuser/app" \
       --volume "$HOME/.m2:/home/mxnetuser/.m2" \
       --volume "$HOME/.m2:/root/.m2" \
       --interactive \
       -p "12121:12121" \
       --tty \
       chouffe/mxnet-clj-cpu \
       lein repl :start :host 0.0.0.0 :port 12121
```
* Run MXNET tests
```
docker run \
       --rm \
       --volume "$PWD:/home/mxnetuser/app" \
       --volume "$HOME/.m2:/home/mxnetuser/.m2" \
       --volume "$HOME/.m2:/root/.m2" \
       --tty \
       chouffe/mxnet-clj-cpu \
       lein test
```

## AWS Deep Learning AMI base

* Update Java Version to 8
```
# choose java 8
sudo update-alternatives --config java
```
* Install leiningen
```
wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein
chmod a+x lein
mv lein /usr/bin
lein
```
* Download MXNET
```
git clone https://github.com/apache/incubator-mxnet.git
```
* Update project.clj to include `clojure-mxnet-linux-gpu` version
```
(defproject module-examples "0.1.0-SNAPSHOT"
  :description "Clojure examples for module"
  :plugins [[lein-cljfmt "0.5.7"]]
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.apache.mxnet.contrib.clojure/clojure-mxnet-linux-gpu "1.4.0"]]
  :pedantic? :skip
  :repositories
  [["staging" {:url "https://repository.apache.org/content/repositories/staging"
               :snapshots true
               :update :always}]
   ["snapshots" {:url "https://repository.apache.org/content/repositories/snapshots"
                 :snapshots true
                 :update :always}]]
  :main mnist-mlp)
```
* Update CUDA version to match package dependency
```
sudo rm /usr/local/cuda
sudo ln -s /usr/local/cuda-9.2 /usr/local/cuda
```
* Monitor GPU processes
```
watch nvidia-sim
```
* Run some example functions
```
lein repl
mnist-mlp=> (run-all [(context/gpu)])
```
