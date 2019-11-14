#!/bin/bash

kubectl apply -f ./deployment-v1-1.yaml # Note you need to update deployment-1.yaml file with proper CR ($ACR_NAME.azurecr.io) if you use Azure CR
kubectl apply -f ./deployment-v1-2.yaml # Note you need to update deployment-1.yaml file with proper CR ($ACR_NAME.azurecr.io) if you use Azure CR
kubectl apply -f ./service-v1-1.yaml
kubectl apply -f ./service-v1-2.yaml
kubectl get service rpc-service-1
kubectl get service rpc-service-2