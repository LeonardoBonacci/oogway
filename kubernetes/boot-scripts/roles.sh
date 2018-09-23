#!/bin/bash
##
# Script to create roles
##

cd ..
echo "Running scripts from: " $(pwd)

echo "roles"
kubectl.exe apply -f roles.yaml	
sleep 1

echo "role bindings"
kubectl.exe apply -f rolebindings.yaml	
sleep 1

echo "roles updated."

sleep 4

