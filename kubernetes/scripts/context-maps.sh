#!/bin/bash

echo '> Script to setup context-maps'

cd ../..
echo "Home: " $(pwd)

kubectl.exe delete cm oracle
kubectl.exe delete cm doorway
kubectl.exe delete cm lumberjack
kubectl.exe delete cm sannyas

kubectl create configmap oracle --from-file=oracle/src/main/resources/application.yml
kubectl create configmap doorway --from-file=heimdall/doorway/src/main/resources/application.yml
kubectl create configmap lumberjack --from-file=heimdall/lumberjack/src/main/resources/application.yml
kubectl create configmap sannyas --from-file=sannyas/src/main/resources/application.yml

echo "OK"
sleep 2

