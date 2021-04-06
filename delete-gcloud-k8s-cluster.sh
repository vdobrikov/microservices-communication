#!/bin/bash

source ./set-cluster-name.sh

gcloud container clusters delete "$CLUSTER_NAME"

