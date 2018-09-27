#!/bin/bash

echo '> Script to setup all'

/bin/bash prep-env.sh

/bin/bash logstash.sh

/bin/bash es.sh

/bin/bash kibana.sh

/bin/bash services.sh

echo "OK"
sleep 3
