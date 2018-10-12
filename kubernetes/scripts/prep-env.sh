#!/bin/bash

echo '> Script to setup environment'

/bin/bash roles.sh

/bin/bash context-maps.sh

/bin/bash ingress.sh

echo "OK"
