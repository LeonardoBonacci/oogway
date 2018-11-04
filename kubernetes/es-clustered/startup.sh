#!/bin/bash

echo '> Script to setup clustered es'

kubectl create -f es-discovery-svc.yaml
kubectl create -f es-svc.yaml

kubectl create -f es-master-svc.yaml
kubectl create -f es-master-stateful.yaml
#kubectl rollout status -f es-master-stateful.yaml

kubectl create -f es-ingest-svc.yaml
kubectl create -f es-ingest.yaml
#kubectl rollout status -f es-ingest.yaml

kubectl create -f es-data-svc.yaml
kubectl create -f es-data-stateful.yaml
#kubectl rollout status -f es-data-stateful.yaml

echo "OK"
