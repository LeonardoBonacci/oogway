@echo off
title Run spectre...

set version=2.1-SNAPSHOT

start cmd.exe /k "cd local-timer & java -jar target\local-timer-%version%.jar""
start cmd.exe /k "cd sentiment & java -jar target\sentiment-%version%.jar""
start cmd.exe /k "cd weather & java -jar target\weather-%version%.jar""
start cmd.exe /k "cd money & java -jar target\money-%version%.jar""

