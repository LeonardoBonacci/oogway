@echo off
title Simple way to build and run oogway, enjoy a coffee while you're waiting...

echo Let's build oogway from directory %cd%

set skip-tests=%1
if "%skip-tests%" == "skip" (
    set maven-build=mvn clean install -DskipTests
) else (
    set maven-build=mvn clean install
)

rem quickly build the parent pom
start /wait cmd.exe /c "%maven-build%"

call just-run.bat 
