#!/bin/bash
##
# Script to create context maps for all services
##

cd ../..
echo "Home: " $(pwd)

echo "clean first"
kubectl.exe delete cm --all

cd jobs
echo "jobs from: " $(pwd)
kubectl create configmap job --from-file=src/main/resources/application.yml
cd ..

cd auth
echo "auth from: " $(pwd)
kubectl create configmap auth --from-file=src/main/resources/application.yml
cd ..

cd oracle
echo "oracle from: " $(pwd)
kubectl create configmap oracle --from-file=src/main/resources/application.yml
cd ..

cd heimdall/doorway
echo "doorway from: " $(pwd)
kubectl create configmap doorway --from-file=src/main/resources/application.yml
cd ..

cd lumberjack
echo "lumberjack from: " $(pwd)
kubectl create configmap lumberjack --from-file=src/main/resources/application.yml

echo "(well) done"

sleep 4

