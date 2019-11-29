#!/bin/bash

VERSION=${1:-1.0.0}

(cd rpc-service && ./build.sh "$VERSION" && ./push.sh "$VERSION" && ./deploy-v1-1.sh && ./deploy-v1-2.sh)
(cd web-service && ./build.sh "$VERSION" && ./push.sh "$VERSION" && ./deploy-v1-1.sh)
