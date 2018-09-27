#!/bin/bash

echo '> Script to setup ingress'

cd ..
echo "Kub-home: " $(pwd)

kubectl.exe delete ingress --all
sleep 5

kubectl create -f ingress.yaml

# https://www.petri.com/easily-edit-hosts-file-windows-10
mip=`eval minikube ip`

echo "-----------------------------------------"
echo "IMPORTANT MESSAGE!!!"
echo "add"
echo "$mip talk.to.oogway" 
echo "to C:\Windows\System32\Drivers\etc\hosts"
echo "-----------------------------------------"

sleep 8
echo "OK"

