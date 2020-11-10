#!/bin/sh

java -jar swagger-codegen-cli-3.0.22.jar generate -i activity-api.yaml -l java -o ../yajapi -c config-activity.json -DhideGenerationTimestamp=true

java -jar swagger-codegen-cli-3.0.22.jar generate -i market-api.yaml -l java -o ../yajapi -c config-market.json -DhideGenerationTimestamp=true

java -jar swagger-codegen-cli-3.0.22.jar generate -i payment-api.yaml -l java -o ../yajapi -c config-payment.json -DhideGenerationTimestamp=true
