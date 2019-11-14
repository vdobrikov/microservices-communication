#!/bin/bash

VERSION=${1:-latest}

#cwd=$(pwd)
(cd rpc-service && ./build-push-deploy.sh "$VERSION")
(cd web-service && ./build-push-deploy.sh "$VERSION")


