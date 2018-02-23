@echo off
title Run oogway...

start cmd.exe /k "cd config & java -jar target\config.jar"

TIMEOUT /T 15

start cmd.exe /k "cd monitoring & java -jar target\monitoring.jar"

start cmd.exe /k "cd orchestration & java -jar target\orchestration-service.jar"
start cmd.exe /k "cd oracle\oracle-service & java -jar target\oracle-service.jar"
start cmd.exe /k "cd sannyas\sannyas-service & java -jar target\sannyas-service.jar"
start cmd.exe /k "cd web & java -jar target\web-service.jar"

start cmd.exe /k "cd jobs & java -jar target\job-service.jar"

TIMEOUT /T 30

start cmd.exe /k "cd zipkin & java -jar target\zipkin.jar"
