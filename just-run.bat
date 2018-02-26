@echo off
title Run oogway...

rem set github configuration branch name as environment variable
rem no value defaults to master branch 
set CLOUD_CONFIG_FIND_LABEL=master

start cmd.exe /k "cd config & java -jar target\config.jar"

TIMEOUT /T 15

start cmd.exe /k "cd orchestration & java -jar target\orchestration-service.jar"

start cmd.exe /k "cd auth & java -jar target\auth-service.jar"

start cmd.exe /k "cd oracle\oracle-service & java -jar target\oracle-service.jar"
start cmd.exe /k "cd sannyas\sannyas-service & java -jar target\sannyas-service.jar"
start cmd.exe /k "cd web & java -jar target\web-service.jar"

start cmd.exe /k "cd jobs & java -jar target\job-service.jar"

start cmd.exe /k "cd heimdall & java -jar target\heimdall.jar"

TIMEOUT /T 30

start cmd.exe /k "cd zipkin & java -jar target\zipkin.jar"
