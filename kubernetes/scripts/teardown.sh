#!/bin/bash

echo "Tearing down"

echo "context-maps"
kubectl.exe delete cm --all	
sleep 2

echo "deployments"
kubectl.exe delete deployments --all	
sleep 2

echo "services"
kubectl.exe delete services --all	
sleep 2

echo "roles"
kubectl.exe delete rolebindings --all	
kubectl.exe delete roles --all	
sleep 2

echo "daemons"
kubectl.exe delete ds --all	
sleep 2

echo "service accounts"
kubectl.exe delete sa --all
sleep 2

echo "ingress"
kubectl.exe delete ingress --all	
sleep 2

echo "pods"
kubectl.exe delete pods --all	
sleep 2


echo "OK"
