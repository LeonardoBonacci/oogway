#!/bin/bash
##
# Boot script
##

echo "don't forget to run encryption-keys.sh first!"
sleep 2

/bin/bash roles.sh

/bin/bash context-maps.sh

/bin/bash services.sh

/bin/bash deployments.sh

echo "As a polite reminder: did you run encryption-keys.sh?"
sleep 3
