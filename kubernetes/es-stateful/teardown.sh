#!/bin/bash

echo "Tearing down ES and more"

echo "deployments"
kubectl.exe delete deployments --all	
sleep 2

echo "services"
kubectl.exe delete services --all	
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


echo "OK"
