#!/bin/bash

source ./set-cluster-name.sh

gcloud container clusters create "$CLUSTER_NAME" --num-nodes=3
gcloud container clusters get-credentials "$CLUSTER_NAME" # This will also update your kube config.

