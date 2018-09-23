#!/bin/bash

# Check for password argument
if [[ $# -eq 0 ]] ; then
    echo 'You must provide one argument for vm's ip address. Run 'minikube status'
    echo '  Usage:  encryption-keys.sh 192.168.178.111'
    echo
	sleep 4
    exit 1
fi

echo "Copying private key"
scp -i ~/.minikube/machines/minikube/id_rsa ../../private.key docker@${1}:/home/docker

echo "Copying public key"
scp -i ~/.minikube/machines/minikube/id_rsa ../../public.key docker@${1}:/home/docker

echo "Keys copied"
sleep 4

