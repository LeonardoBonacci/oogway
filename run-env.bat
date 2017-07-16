@echo off
title Setup oogways environment...

set HOME=%cd%
echo We're working from directory %HOME%

set ES_HOME=C:\Tools\elasticsearch-2.4.4
set AMQ_HOME=C:\Tools\apache-activemq-5.14.5
set LOGSTASH_HOME=C:\Tools\logstash-5.5.0
set KIBANA_HOME=C:\Tools\kibana-4.6.3-windows-x86

start cmd.exe /k "cd %ES_HOME% & bin\elasticsearch"
start cmd.exe /k "cd %AMQ_HOME% & bin\activemq start"
start cmd.exe /k "cd %LOGSTASH_HOME% & bin\logstash -f C:\Users\Leo\git\oogway\spectre\geo\geo-service\spectre-pipeline.conf"
start cmd.exe /k "cd %KIBANA_HOME% & bin\kibana"
