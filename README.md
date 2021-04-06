# microservices-communication

Education project to learn different types of microservices communications

## Build
1. Create google service cluster
```shell script
$ ./create-gcloud-k8s-cluster.sh
```
2. Build, push'n'deploy
```shell script
$ ./build-push-deploy-v1.sh
```

## Usage
1. Healthcheck
```
http://<EXTERNAL_IP>:60000/health`
```
2. Hello (async) with optional name
```
http://<EXTERNAL_IP>:60000/api/hello?name=Neo
```

## Delete Cluster
```shell script
$ ./delete-gcloud-k8s-cluster.sh
```
