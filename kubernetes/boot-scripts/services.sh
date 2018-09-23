#!/bin/bash
##
# Script to create all services
##

cd ..
echo "Running scripts from: " $(pwd)

echo "es"
kubectl.exe apply -f es-svc.yaml	
sleep 1

echo "oracle"
kubectl.exe apply -f oracle-svc.yaml	
sleep 1

echo "auth"
kubectl.exe apply -f auth-svc.yaml	
sleep 1

echo "lumberjack"
kubectl.exe apply -f lumberjack-svc.yaml	
sleep 1

echo "doorway"
kubectl.exe apply -f doorway-svc.yaml	
sleep 1

echo "jobs"
kubectl.exe apply -f job-svc.yaml	
sleep 1

echo "services updated."

sleep 4

