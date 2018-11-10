#!/bin/bash

echo "Tearing down Rabbit and more"

echo "deployments"
kubectl.exe delete deployments --all	
sleep 2

echo "services"
kubectl.exe delete services --all	
sleep 2

echo "service accounts"
kubectl.exe delete serviceaccounts --all	
sleep 2

echo "statefulsets"
kubectl.exe delete statefulsets --all	
sleep 2

echo "volumes"
kubectl.exe delete pvc --all
sleep 2

echo "pods"
kubectl.exe delete pods --all	
sleep 2

echo "roles"
kubectl.exe delete rolebindings endpoint-reader
kubectl.exe delete roles endpoint-reader	
sleep 2

echo "OK"
