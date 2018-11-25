#!/bin/bash

echo '> Script to setup fluentd'

cd ..
echo "Kub-home: " $(pwd)

kubectl.exe delete cm fluentd-config
kubectl.exe delete ds fluentd
sleep 3

kubectl create -f fluentd-config-map.yaml
sleep 2

kubectl create -f fluentd.yaml
sleep 2

echo "OK"

