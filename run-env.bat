@echo off
title Setup oogways environment...

echo We're working from directory %cd%

docker run -d --name es -p 9200:9200 -p 9300:9300 elasticsearch:2.4
docker run -d --name rabbit -p 4369:4369 -p 5672:5672 -p 15672:15672 -p 25672:25672 rabbitmq:3.6-management
docker run -d --name kibana --link es:elasticsearch -p 5601:5601 kibana:4.6
docker run --name logstash --link es:elasticsearch --rm --link rabbit:rabbitmq -it -v C:/Users/Leo/git/oogway/spectre/logstash-settings/empty:/usr/share/logstash/pipeline/ docker.elastic.co/logstash/logstash-oss:6.2.2
rem docker run -d --name tor -p 127.0.0.1:9050:9050 osminogin/tor-simple