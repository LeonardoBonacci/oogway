#!/bin/bash

echo '> Script to setup roles'

cd ..
echo "Running scripts from: " $(pwd)

kubectl.exe apply -f roles.yaml	
sleep 1

echo "OK"
