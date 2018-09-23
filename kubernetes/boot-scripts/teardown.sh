#!/bin/bash

echo "Tearing down"

echo "context maps"
kubectl.exe delete cm --all	
sleep 2

echo "deployments"
kubectl.exe delete deployments --all	
sleep 6

echo "services"
kubectl.exe delete services --all	
sleep 2
