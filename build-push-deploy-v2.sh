#!/bin/bash

VERSION=${1:-2.0.0}

(cd rpc-service && ./build.sh "$VERSION" && ./push.sh "$VERSION" && ./deploy-v2-2.sh)
(cd web-service && ./build.sh "$VERSION" && ./push.sh "$VERSION" && ./deploy-v2-2.sh)
