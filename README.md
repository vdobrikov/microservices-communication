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
2. Blocking hello
```
http://<EXTERNAL_IP>:60000/api/hello-blocking
```
3. Async hello
```
http://<EXTERNAL_IP>:60000/api/hello-async
```

## Delete Cluster
```shell script
$ ./delete-gcloud-k8s-cluster.sh
```
