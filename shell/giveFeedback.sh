#!/bin/sh
curl -vX POST http://localhost:1026/v1/contextEntities -d @./orion_contexts/feedback.json \
--header "Content-Type: application/json" \
--header "Fiware-Service: whirlpool" \
--header "Fiware-ServicePath: /cassinetta"