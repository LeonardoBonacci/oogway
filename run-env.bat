@echo off
title Setup oogways environment...

set HOME=%cd%
echo We're working from directory %HOME%

set KIBANA_HOME=C:\Tools\kibana-4.6.3-windows-x86
set TOR_HOME=C:\Tools\Tor Browser\Browser\TorBrowser\Tor

docker run -d -it -p 9200:9200 -p 9300:9300 elasticsearch:2.4.6
docker run -d -p 4369:4369 -p 5672:5672 -p 15672:15672 -p 25672:25672 rabbitmq:3.6-management
rem start cmd.exe /k "cd %KIBANA_HOME% & bin\kibana"
rem start cmd.exe /k "cd %TOR_HOME% & tor.exe"

rem run spectre from its own directory
cd spectre 
call run-env.bat
cd.. 