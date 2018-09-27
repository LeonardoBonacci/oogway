#!/bin/bash

echo '> Script to setup es'

cd ..
echo "Kub-home: " $(pwd)

kubectl.exe delete deployment es-data
kubectl.exe delete service es
sleep 5

kubectl create -f es-svc.yaml
sleep 2
kubectl create -f es-data.yaml
sleep 2

echo "OK"
