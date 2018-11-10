#!/bin/bash

echo '> Script to setup stateful rabbitmq'

kubectl create -f rabbitmq_rbac.yaml
sleep 10

kubectl create -f rabbitmq_statefulsets.yaml
sleep 2

#kubectl get all --namespace test-rabbitmq
#kubectl exec --namespace=test-rabbitmq rabbitmq-0 rabbitmqctl cluster_status
#http://<<minikube_ip>>:31672

echo "OK"
