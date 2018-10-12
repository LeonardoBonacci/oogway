#!/bin/bash

echo '> Script to setup the rabbit'

cd ..
echo "Kub-home: " $(pwd)

kubectl.exe delete deployment rabbitmq
kubectl.exe delete service rabbit
sleep 5

kubectl create -f rabbit-svc.yaml
sleep 2
kubectl create -f rabbit.yaml
sleep 2

echo "OK"
