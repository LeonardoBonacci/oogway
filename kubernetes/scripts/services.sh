#!/bin/bash

echo '> Script to setup services and deployments'

cd ..
echo "Running scripts from: " $(pwd)

#kubectl.exe apply -f oracle.yaml	
sleep 1

#kubectl.exe apply -f lumberjack.yaml	
sleep 1

kubectl.exe delete deployment doorway	
kubectl.exe apply -f doorway.yaml	
sleep 1

#kubectl.exe apply -f sannyas.yaml	
sleep 1

echo "OK"
