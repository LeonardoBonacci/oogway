#!/bin/bash
##
# Script to setup logstash
##

cd ..
echo "Kub-home: " $(pwd)

echo "clean first"
kubectl.exe delete cm logging-configmap
kubectl.exe delete deployment logstash
kubectl.exe delete service logstash-service
sleep 5

echo "create config map"
kubectl create -f logging-configmap.yaml
sleep 2

echo "create service and deployment"
kubectl create -f logstash.yaml
sleep 2
