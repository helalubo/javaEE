@echo off
call mvn clean package
call docker build -t com.helalubo/holamundo .
call docker rm -f holamundo
call docker run -d -p 9080:9080 -p 9443:9443 --name holamundo com.helalubo/holamundo