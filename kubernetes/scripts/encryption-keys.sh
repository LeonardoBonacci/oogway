#!/bin/bash

echo '> Script to setup encryption-keys'

mip=`eval minikube ip`
echo "minikube available on $mip"

echo "Copying private key"
scp -i ~/.minikube/machines/minikube/id_rsa ../../private.key docker@$mip:/home/docker

echo "Copying public key"
scp -i ~/.minikube/machines/minikube/id_rsa ../../public.key docker@$mip:/home/docker

echo "OK"
sleep 2

