#!/bin/bash

source ../set-cr-name.sh

VERSION=${1:-latest}

docker push "$CR_NAME"/microservices-communication-1/web-service:"$VERSION"