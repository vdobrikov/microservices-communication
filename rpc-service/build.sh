#!/bin/bash

source ../set-cr-name.sh

VERSION=${1:-latest}
echo "Building '$VERSION' docker image version for '$CR_NAME' registry"

mvn clean package
docker build -t "$CR_NAME"/microservices-communication-1/rpc-service:"$VERSION" .