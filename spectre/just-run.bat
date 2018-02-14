@echo off
title Run spectre...

start cmd.exe /k "cd local-timer & java -jar target\local-timer.jar"
start cmd.exe /k "cd sentiment & java -jar target\sentiment.jar"
start cmd.exe /k "cd weather & java -jar target\weather.jar"
start cmd.exe /k "cd money & java -jar target\money.jar"

