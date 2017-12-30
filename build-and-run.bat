@echo off
title Simple way to build and run oogway, enjoy a coffee while you're waiting...

echo Let's build oogway from directory %cd%

set skip-tests=%1
if "%skip-tests%" == "skip" (
    set maven-build=mvn clean install -DskipTests
) else (
    set maven-build=mvn clean install
)

rem  build the parent pom
start /wait cmd.exe /c "%maven-build%"

call just-run.bat 

cd spectre rem run spectre from its own directory
call just-run.bat
