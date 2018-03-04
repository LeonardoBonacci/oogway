@echo off
title Setup oogways environment...

set HOME=%cd%
echo We're working from directory %HOME%

set RABBIT_HOME=C:\Program Files\RabbitMQ Server
set KIBANA_HOME=C:\Tools\kibana-4.6.3-windows-x86
set TOR_HOME=C:\Tools\Tor Browser\Browser\TorBrowser\Tor

docker run -d -it -p 9200:9200 -p 9300:9300 elasticsearch:2.4.6
start cmd.exe /k "cd %RABBIT_HOME% & rabbitmq_server-3.6.12\sbin\rabbitmq-server.bat"
rem start cmd.exe /k "cd %KIBANA_HOME% & bin\kibana"
rem start cmd.exe /k "cd %TOR_HOME% & tor.exe"

rem run spectre from its own directory
cd spectre 
call run-env.bat
cd.. 