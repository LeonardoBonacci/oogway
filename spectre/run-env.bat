@echo off
title Setup spectres environment...

start cmd.exe /k "cd %LS_HOME% & bin\logstash --path.settings=%cd%\logstash-settings
