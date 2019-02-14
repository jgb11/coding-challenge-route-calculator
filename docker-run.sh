#!/usr/bin/env bash

mvn -f ./pom.xml clean package dockerfile:build
docker rm -f coding-challenge-route-calculator
docker run -d \
    --name coding-challenge-route-calculator \
    --net codingchallenge \
    -p 8070:8080 \
    -v ~/projects/coding-challenge/coding-challenge-route-calculator/target:/opt/lib \
    jgb/coding-challenge-route-calculator:latest
