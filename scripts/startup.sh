#!/bin/bash

set -e

# start up docker container with ElasticSearch and Kibana
cd docker
docker compose up -d
cd ..

