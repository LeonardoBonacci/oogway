@echo off
title Setup spectres environment...

set LOGSTASH_HOME=C:\Tools\logstash-5.5.0

start cmd.exe /k "cd %LOGSTASH_HOME% & bin\logstash -f C:\Users\Leo\git\oogway\spectre\spectre-pipeline.conf"

