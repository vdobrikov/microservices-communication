#!/bin/bash

kubectl apply -f ./deployment-v1.yaml # Note you need to update deployment-v1.yaml file with proper CR ($ACR_NAME.azurecr.io) if you use Azure CR
kubectl apply -f ./service-v1.yaml
kubectl get service web-service