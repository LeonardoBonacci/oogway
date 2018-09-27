#!/bin/bash

echo '> Script to setup logstash'

cd ..
echo "Kub-home: " $(pwd)

kubectl.exe delete cm logging-configmap
kubectl.exe delete deployment logstash
kubectl.exe delete service logstash-service
sleep 3

kubectl create -f logging-configmap.yaml
sleep 2

kubectl create -f logstash.yaml
sleep 2

echo "OK"

