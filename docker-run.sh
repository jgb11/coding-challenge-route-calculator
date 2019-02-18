#!/usr/bin/env bash

export PROJECT_VERSION=0.0.1-SNAPSHOT
make -C docker tag
docker rm -f coding-challenge-route-calculator
docker run -d -p 8070:8080 \
    --name coding-challenge-route-calculator \
    --net codingchallenge \
    jgb/coding-challenge-route-calculator:${PROJECT_VERSION}
