@echo off
title Setup oogways environment...

echo We're working from directory %cd%

set TOR_HOME=C:\Tools\Tor Browser\Browser\TorBrowser\Tor

docker run -d --name es -p 9200:9200 -p 9300:9300 elasticsearch:2.4
docker run -d --name rabbit -p 4369:4369 -p 5672:5672 -p 15672:15672 -p 25672:25672 rabbitmq:3.6-management
docker run -d --name kibana --link es:elasticsearch -p 5601:5601 kibana:4.6
rem start cmd.exe /k "cd %TOR_HOME% & tor.exe"

rem run spectre from its own directory
cd spectre 
call run-env.bat
cd.. 