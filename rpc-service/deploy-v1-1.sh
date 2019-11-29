#!/bin/bash

kubectl apply -f ./deployment-v1-1.yaml
kubectl apply -f ./service-v1-1.yaml
kubectl get service rpc-service-1