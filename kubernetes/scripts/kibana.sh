#!/bin/bash

echo 'Script to setup all'

cd ..
echo "Kub-home: " $(pwd)

kubectl.exe delete deployment kibana
kubectl.exe delete service kibana-service
sleep 5

kubectl create -f kibana.yaml
sleep 2

echo "OK"
