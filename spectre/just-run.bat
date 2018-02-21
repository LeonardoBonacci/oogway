@echo off
title Run spectre...

start cmd.exe /k "cd local-timer & java -jar target\localtimer-service.jar"
start cmd.exe /k "cd sentiment & java -jar target\sentiment-service.jar"
start cmd.exe /k "cd weather & java -jar target\weather-service.jar"
start cmd.exe /k "cd money & java -jar target\money-service.jar"

