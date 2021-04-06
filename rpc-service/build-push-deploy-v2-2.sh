#!/bin/bash

VERSION=${1:-2.0.0}

./build.sh "$VERSION"
./push.sh "$VERSION"
./deploy-v2-1.sh
./deploy-v2-2.sh