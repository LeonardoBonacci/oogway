@echo off
title Simple way to build and run oogway, enjoy a coffee while you're waiting...

echo Let's build oogway from directory %cd%

set version=1.0.0-SNAPSHOT
set persistence=oracle
set registration=eureka
set oracle=oracle-service
set sannya=sannya
set web=web

set skip-tests=%1
if "%skip-tests%" == "skip" (
    set maven-build=mvn clean install -DskipTests
) else (
    set maven-build=mvn clean install
)

rem quickly build the parent pom
start /wait cmd.exe /c "%maven-build%"
 
start cmd.exe /k "cd %registration% & java -jar target\\%registration%-%version%.jar""
start cmd.exe /k "cd oracle\\%oracle% & java -jar target\\%oracle%-%version%.jar""
start cmd.exe /k "cd %sannya% & java -jar target\\%sannya%-%version%.jar""
start cmd.exe /k "cd %web% & java -jar target\\%web%-%version%.jar""

