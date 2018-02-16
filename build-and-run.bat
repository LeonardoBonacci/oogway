@echo off
title Simple way to build and run oogway, enjoy a coffee while you're waiting...

echo Let's build oogway from directory %cd%

set skip-tests=%1
set run-spectre=%2

if "%skip-tests%" == "skip" (
    set maven-build=mvn clean install -DskipTests
) else (
    set maven-build=mvn clean install
)

rem build the parent pom
start /wait cmd.exe /c "%maven-build%"

rem and run oogway
call just-run.bat 

if "%run-spectre%" == "spectre" (
	rem run spectre from its own directory
	cd spectre 
	call just-run.bat

	cd .. 
)
