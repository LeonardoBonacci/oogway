@echo off
title Simple way to build and run oogway, enjoy a coffee while you're waiting...

echo Let's build oogway from directory %cd%

set version=2.1-SNAPSHOT

set orchestration=orchestration
set oracle=oracle-service
set sannya=sannya-service
set web=web
set localtimer=local-timer
set sentiment=sentiment

set skip-tests=%1
if "%skip-tests%" == "skip" (
    set maven-build=mvn clean install -DskipTests
) else (
    set maven-build=mvn clean install
)

rem quickly build the parent pom
start /wait cmd.exe /c "%maven-build%"
 
start cmd.exe /k "cd %orchestration% & java -jar target\%orchestration%-%version%.jar""
start cmd.exe /k "cd oracle\%oracle% & java -jar target\%oracle%-%version%.jar""
start cmd.exe /k "cd sannya\%sannya% & java -jar target\%sannya%-%version%.jar""
start cmd.exe /k "cd %web% & java -jar target\%web%-%version%.jar""
start cmd.exe /k "cd spectre\%localtimer% & java -jar target\%localtimer%-%version%.jar""
start cmd.exe /k "cd spectre\%sentiment% & java -jar target\%sentiment%-%version%.jar""

