@echo off
title Run oogway...

set version=2.1-SNAPSHOT

start cmd.exe /k "cd orchestration & java -jar target\orchestration-%version%.jar""
start cmd.exe /k "cd oracle\oracle-service & java -jar target\oracle-service-%version%.jar""
start cmd.exe /k "cd sannya\sannya-service & java -jar target\sannya-service-%version%.jar""
start cmd.exe /k "cd web & java -jar target\web-%version%.jar""
start cmd.exe /k "cd jobs & java -jar target\jobs-%version%.jar""

