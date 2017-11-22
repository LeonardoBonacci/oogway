@echo off
title Setup oogways environment...

set HOME=%cd%
echo We're working from directory %HOME%

set ES_HOME=C:\Tools\elasticsearch-2.4.4
set RABBIT_HOME=C:\Program Files\RabbitMQ Server
rem set KIBANA_HOME=C:\Tools\kibana-4.6.3-windows-x86

start cmd.exe /k "cd %ES_HOME% & bin\elasticsearch"
start cmd.exe /k "cd %RABBIT_HOME% & rabbitmq_server-3.6.12\sbin\rabbitmq-server.bat"
rem start cmd.exe /k "cd %KIBANA_HOME% & bin\kibana"
