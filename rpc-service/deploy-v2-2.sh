#!/bin/bash

kubectl apply -f ./deployment-v2-2.yaml
kubectl apply -f ./service-v2-2.yaml
kubectl get service rpc-service-2