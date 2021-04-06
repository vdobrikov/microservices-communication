#!/bin/bash

kubectl apply -f ./deployment-v1-2.yaml
kubectl apply -f ./service-v1-2.yaml
kubectl get service rpc-service-2