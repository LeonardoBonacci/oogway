@echo off
title Setup spectres environment...

set LOGSTASH_HOME=C:\Tools\logstash-6.2.1

start cmd.exe /k "cd %LOGSTASH_HOME% & bin\logstash -f C:\Users\Leo\git\oogway\spectre\spectre-pipeline.conf"

