@echo off
title Run oogway...

start cmd.exe /k "cd config & java -jar target\config.jar"

TIMEOUT /T 5

start cmd.exe /k "cd orchestration & java -jar target\orchestration.jar"
start cmd.exe /k "cd oracle\oracle-service & java -jar target\oracle-service.jar"
start cmd.exe /k "cd sannya\sannya-service & java -jar target\sannya-service.jar"
start cmd.exe /k "cd web & java -jar target\web.jar"
start cmd.exe /k "cd jobs & java -jar target\jobs.jar""

start cmd.exe /k "cd monitoring & mvn spring-boot:run"
