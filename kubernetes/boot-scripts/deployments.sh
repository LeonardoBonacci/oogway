#!/bin/bash
##
# Script to create all deployments
##

cd ..
echo "Running scripts from: " $(pwd)

echo "es"
kubectl.exe apply -f es-data.yaml	
sleep 2

echo "auth"
kubectl.exe apply -f auth.yaml	
sleep 2

echo "lumberjack"
kubectl.exe apply -f lumberjack.yaml	
sleep 2

echo "doorway"
kubectl.exe apply -f doorway.yaml	
sleep 2

echo "jobs"
kubectl.exe apply -f job.yaml	
sleep 2

echo "oracle"
kubectl.exe apply -f oracle.yaml	
sleep 2

echo "deployments created."

sleep 4

