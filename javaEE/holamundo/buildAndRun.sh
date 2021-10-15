#!/bin/sh
if [ $(docker ps -a -f name=holamundo | grep -w holamundo | wc -l) -eq 1 ]; then
  docker rm -f holamundo
fi
mvn clean package && docker build -t com.helalubo/holamundo .
docker run -d -p 9080:9080 -p 9443:9443 --name holamundo com.helalubo/holamundo
