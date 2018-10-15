#!/bin/bash

echo '> Script to setup spectre pods'

cd ..
echo "Running scripts from: " $(pwd)

kubectl.exe apply -f sentiment.yaml	
sleep 1

#kubectl.exe apply -f localtimer.yaml	
sleep 1

#kubectl.exe apply -f money.yaml	
sleep 1

#kubectl.exe apply -f weather.yaml	
sleep 1

echo "OK"
