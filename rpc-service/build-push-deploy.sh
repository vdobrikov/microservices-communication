#!/bin/bash

VERSION=${1:-latest}

./build.sh "$VERSION"
./push.sh "$VERSION"
./deploy.sh