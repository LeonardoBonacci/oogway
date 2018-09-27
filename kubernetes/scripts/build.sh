#!/bin/bash

echo '> Script to build all services'

cd ../..
echo "Home: " $(pwd)

echo 'Building and dockerizing, takes a while..'
mvn clean install -DskipTests; docker-compose build;

echo "OK"
sleep 2

