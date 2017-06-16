@echo off
title Simple way to build and run oogway, enjoy a coffee while you're waiting...
cd..\..\..\..
echo Let's build oogway from directory %cd%

set version=1.0.0-SNAPSHOT
set parent=parent
set persistence=oracle
set registration=eureka
set crawler=sannya
set web=web

set skip-tests=%1
if "%skip-tests%" == "skip" (
    set maven-build=mvn clean install -DskipTests
) else (
    set maven-build=mvn clean install
)

rem quickly build the parent pom
start /wait cmd.exe /c "%maven-build%"
 
start cmd.exe /k "cd %parent%\%registration% & java -jar target\\%registration%-%version%.jar""
start cmd.exe /k "cd %parent%\%crawler% & java -jar target\\%crawler%-%version%.jar""
start cmd.exe /k "cd %parent%\%web% & java -jar target\\%web%-%version%.jar""

rem and return to the scripts directory
cd parent\src\main\scripts
