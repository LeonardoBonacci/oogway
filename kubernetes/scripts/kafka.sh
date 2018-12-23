#!/bin/bash

echo 'Script to setup service that forwards to kafka'

cd ..
echo "Kub-home: " $(pwd)

kubectl.exe delete service kafka
kubectl.exe delete endpoints kafka
sleep 5

kubectl create -f kafka.yaml
sleep 2

echo "OK"