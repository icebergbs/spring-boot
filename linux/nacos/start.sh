#!/bin/bash

docker stop nacos
docker rm nacos
docker run -idt --name nacos --restart=always \
        -e MODE=standalone \
        -p 8848:8848 \
        -v `pwd`/logs/:/home/nacos/logs \
        -v `pwd`/init.d/application.properties:/home/nacos/init.d/application.properties \
        nacos/nacos-server:1.3.1