# Kubernetes

## What & Why

* What: System for running many different containers over multiple machines
* Why: Scale number of containers in a multi container environment
* Kubernetes cluster
  * master: controls what each node does
  * nodes
* In development: `minikube`
* In production: managed solutions like `EKS` or `GKE` or DIY!
* `k8s` does not build containers like `docker-compose`
* Containers and services get restarted automatically
* Deployment is decalarative with config files (in production)

## Taxonomy

- __Node__: VM or physical computer that serves as a worker machine in a K8s cluster.
- __Pod__: A group of one or more Containers tied together. A pod is an ephemeral resource.
- __Control Plane__: Coordinates all activities in the cluster such as scheduling applications, maintaining application states, scaling up and down, and rolling out new versions.
- __Deployment__: recommended way to manage the creation and scaling of pods. The Deployment instructs Kubernetes how to create and update instances of your application.

## minikube - Local only

* program for managing the local VM
* `kubectl` for managing containers in the node - used locally and in
production environment
* Required packages for local dev: kubetcl + virtualbox + minikube

* Start minikube

```bash
minikube start
```

* Stop minikube

```bash
minikube stop
```

* Check status

```bash
minikube status
```

* Open the dasboard

```bash
minikube dashboard
```

* Get the `ip` of the VM

```bash
minikube ip
```

* Reconfigure `docker-cli` to use your docker server for the current termnial -
Not a permanent change

```bash
eval $(minikube docker-env)
```

* Minikube Dashboard

```bash
minikube dashboard
```

* List addons

```bash
minikube addons list
```

* Enable an addon

```bash
minikube addons enable metrics-server
```

## kubectl

The common format of a kubectl command is: `kubectl action resource`

* feed a config file to Kubectl to change the current configuration of the cluster

```bash
kubectl apply -f <filename>
```

* feed a folder of config files to kubernetes

```bash
kubectl apply -f <folder>
```

* print the status of all running pods

```bash
kubectl get pods/services/deployments
```

* list events

```bash
kubectl get events
```

* list deployments

```bash
kubectl get deployments --namespace das-server
```

* list namespaces

```bash
kubectl get namespace
```

* specify a given namespace

```bash
kubectl get pods --namespace das-server
```

* view config

```bash
kubectl config view
```

* print additional status of running objects

```bash
kubectl get pods -o wide
```

* describe object

```bash
kubectl describe pods/services <object-name>
```

* delete an object based on a file

```bash
kubectl delete -f <config-file>
```

* delete an object based on an object type and a name

```bash
kubectl delete deployment client-deployment
```

* Get logs from a pod

```bash
kubectl logs client-deployment-7988c5b747-2zxjb
```

* expose a pod to the public internet - it creates a service using a loadbalancer

```bash
kubectl expose deployment hello-node --type=LoadBalancer --port=8080
```

__Note__: In a cloud provider, an external IP should be created, with minikube
one needs to run the following command `minikube service hello-node` to
generate it.

* list services

```bash
kubectl get services
```

* clean up the service

```bash
kubectl delete service hello-node
```

* clean up the deployment

```bash
kubectl delete deployment hello-node
```

* update a pod when a docker image is updated in a docker registry
  * `Solution 1`: Manually delete pods to get the deployment to recreate them with the latest version
    * Very dangerous in production! one can delete all the wrong pods
  * `Solution 2`: Tag built images with a real version number and specify that version in a config file
    * Adds an extra step to the deployment process
  * `Solution 3`: Use an imperative command to udpate the image version
    * Very similar the former solution
    * It is imperative... :(
    * It is the best of the bad solutions

```bash
docker image build -t chouffe/multi-client:git-sha .
# kubectl set image <object-type>/<object-name> <container-name>=<new-image-to-use>
kubectl set image deployment/client-deployment client=chouffe/multi-client:v1
```

### kube-apiserver

* Monitor the pods and objects to make sure it does what it is supposed to do

## Configuration file for pod

* Any config file is parsed and evaluated by `kubectl` to create `Objects`
  * `StatefulSet`
  * `ReplicaController`
  * `Deployment`: Maintains a set of identical pods, ensuring that they have
  the correct config and that the right number exists
    * Monitors the state of each pod
    * Good for dev and production
  * `Pod`: run a container - smallest thing to create to run a container
    * Good for dev purposes - Rarely used in production
    * Group containers with very similar purpose
    * Containers that must function together
    * Eg. `postgres + logger + backup-manager` in one pod
  * `Service`: setup some networking inside of the k8s cluster
    * `ClusterIP`
    * `NodePort`: expose a container to the external world - only good for dev purposes
    * `LoadBalancer`: legacy way of getting network traffic into a cluster
    * `Ingress`: exposes a set of services to the outside world
* `apiVersion`: limit the scope of different object types
  * Eg. `v1`, `apps/v1`, ...
* `kind`: type of object to make

```client-pod.yml
apiVersion: v1
kind: Pod
metadata:
  name: client-pod
  labels:
    component: web
spec:
  containers:
    - name: client
      image: chouffe/multi-client
      ports:
        - containerPort: 3000
```

```client-node-port.yml
apiVersion: v1
kind: Service
metadata:
  name: client-node-port
spec:
  type: NodePort
  ports:
    - ports: 3050
      targetPort: 3000
      nodePort: 31515
  selector:
    component: web
```

### Pod

```yml
apiVersion: v1
kind: Pod
metadata:
  name: client-pod
  labels:
    component: web
spec:
  containers:
    - name: client
      image: chouffe/multi-worker
      ports:
        - containerPort: 3000
```

### Deployment

```yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: cient-deployment
spec:
  replicas: 1
  selector:
    matchLabels:
      component: web
  template:
    metadata:
      labels:
        component: web
    spec:
      containers:
        - name: client
          image: chouffe/multi-client
          ports:
            - containerPort: 3000
```

### Ingress

* Exposes a set of services to the outside world
* `Kubernetes ingress`: is also a project led by the company `nginx` for Kubernetes
* `Nginx Ingress`: particular implementation of Ingress - `ingress-nginx`
  * Setup is dependent on environment (local, GC, AWS, Azure)
  * Similar to a deployment: The `Ingress Controller` constantly works to make
  sure the desired state is reached

```yml
apiVersion: extensions/b1beta1
kind: Ingress
metadata:
  name: ingress-service
  annotations:
    kubernetes.io/ingress.class: nginx
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
    - http:
        paths:
          - path: /
            backend:
              serviceName: client-cluster-ip-service
              servicePort: 3000
          - path: /api/
            backend:
              serviceName: server-cluster-ip-service
              servicePort: 5000
```

### NodePort

* Only good for development
* No declaration to the `pod` directly
* It uses the `selector` and `labels components` scheme to link objects together
* `ports`: mapping of arrays
  * `containerPort` and `targetPort` must match
  * `port`: another pod can use this to connect to it
  * `nodePort`: (random between 30000 and 32767 if not assigned) to access the
  container from the host

```client-node-port.yml
apiVersion: v1
kind: Service
metadata:
  name: client-node-port
spec:
  type: NodePort
  ports:
    - ports: 3050
      targetPort: 3000
      nodePort: 31515
  selector:
    component: web
```

### ClusterIP

* Exposes a set of pods to other objects inside the cluster

### Misc

* Collocate config files

```
config1
---
config2
```

## Volumes, Persistent Volumes and PVCs

* `Volume`: An object that allows a container to store data at the pod level -
not exactly the same thing as a Docker volume
  * If the pod is replaced by k8s or dies, data is lost...
  * A volume is not appropriate for storing data for a database
* `Persistent Volume`
  * Lives outside the Pod
  * Data survives
* `PVC`: Persistent Volume Claim
  * Like a billboard advertisement for k8s to use and provision
  * Can provision dynamically `Persistent Volume`

### PVC

* `AccessModes`
  * `ReadWriteOnce`: can be used by a single node
  * `ReadOnlyMany`: multiple nodes can read from this
  * `ReadWriteMany`: can be read and written to by many nodes
* Need to define the type of storage class - default is minikube on local
* `storageClassName`
  * AWS Block Store
  * Google Cloud Persistent Disk
  * AzureFile
  * ...

```yml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: database-persistent-volume-claim
spec:
  accessMode:
    - ReadWriteOnce
  resources:
    requests:
      storage: 2Gi
```

```bash
apiVersion: apps/v1
kind: Deployment
metadata:
  name: postgres-deployment
spec:
  replicas: 1
  selector:
    matchLabels:
      component: postgres
  template:
    metadata:
      labels:
        component: postgres
    spec:
      volumes:
        - name: postgres-storage
          persistentVolumeClaim:
            claimName: database-persistent-volume-claim
      containers:
        - name: postgres
          image: postgres
          ports:
            - containerPort: 5432
          volumeMounts:
            - name: postgres-storage
              mountPath: /var/lib/postgresql/data
              subPath: postgres
```

## Environment Variables

* Host values: url to connect to redis/postgres
  * Name of the cluster IP service can be used
* For passwords and secrets
  * Can not be sticked into the config file!
* Environment variable should be passed as strings

```yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: worker-deployment
spec:
  replicas: 1
  selector:
    matchLabels:
      component: worker
  template:
    metadata:
      labels:
        component: worker
    spec:
      containers:
        - name: worker
          image: chouffe/multi-worker
          env:
            - name: REDIS_HOST
              value: redis-cluster-ip-service
            - name: REDIS_PORT
              value: 6379
```

### Encoded Environment Variables

* Object Type: `Secrets`
* Securely stores a piece of information in the cluster, such as database password
* Can be done with an imperative command

```sh
kubectl create secret generic <secret-name> --from-literal key=value

# Real example
kubectl create secret generic pgpassword --from-literal PGPASSWORD=password12345
```

* Can store TLS keys and docker-registry

```yml
...
          env:
            - name: PGPASSWORD
              valueFrom:
                secretKeyRef:
                name: pgpassword
                key: PGPASSWORD
```

```yml
...
      containers:
        - name: postgres
          image: postgres
          env:
            - name: PGPASSWORD
              valueFrom:
                secretKeyRef:
                  name: pgpassword
                  key: PGPASSWORD
```

## Path to Production

* Config files for each service and deployment
* Local test on minikube
* Create a Github/Travis flow to build images and deploy
* Deploy to a cloud provider

## Resources

- [Deployments](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/)
- [Services](https://kubernetes.io/docs/concepts/services-networking/service/)
- [Deploying Applications](https://kubernetes.io/docs/tasks/run-application/run-stateless-application-deployment/)

## Tools

* [k9s](https://github.com/derailed/k9s)
* [kubectx: switch context fast](https://github.com/ahmetb/kubectx)
