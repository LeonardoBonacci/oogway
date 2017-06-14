@echo off
title Simple way to build and run oogway, enjoy a coffee while you're waiting...
echo Let's build oogway
cd..\..\..
echo Its home directory is %cd%

set version="0.0.1-SNAPSHOT"
set persistence="oracle"
set registration="eureka"
set crawler="sannya"
set web="web"

set skip-tests=%1
if "%skip-tests%" == "skip" (
    set maven-build=mvn clean install -DskipTests
) else (
    set maven-build=mvn clean install
)
    
rem wait until the persistence library is built
start /wait cmd.exe /c "cd %persistence% & %maven-build% & java -jar target\\%persistence%-%version%.jar"
rem to then build and run the eureka registration service
start cmd.exe /k "cd %registration% & %maven-build% & java -jar target\\%registration%-%version%.jar"
rem and the sannyas crawler service
start cmd.exe /k "cd %crawler% & %maven-build% & java -jar target\\%crawler%-%version%.jar"
rem and the web service
start cmd.exe /k "cd %web% & %maven-build% & java -jar target\\%web%-%version%.jar"

rem and return to the scripts directory
cd src\main\scripts
pause