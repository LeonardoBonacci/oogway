@echo off
title Run forrest...

set version=2.1-SNAPSHOT

set orchestration=orchestration
set oracle=oracle-service
set sannya=sannya-service
set web=web
set jobs=jobs

start cmd.exe /k "cd %orchestration% & java -jar target\%orchestration%-%version%.jar""
start cmd.exe /k "cd oracle\%oracle% & java -jar target\%oracle%-%version%.jar""
start cmd.exe /k "cd sannya\%sannya% & java -jar target\%sannya%-%version%.jar""
start cmd.exe /k "cd %web% & java -jar target\%web%-%version%.jar""
start cmd.exe /k "cd %jobs% & java -jar target\%jobs%-%version%.jar""

