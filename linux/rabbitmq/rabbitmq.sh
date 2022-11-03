#!/bin/bash
mkdir -p /data/docker-data/rabbitmq-data/
docker stop rabbitmq
docker rm rabbitmq
docker run -d --hostname rabbitmq --name rabbitmq --restart=always --net=host
-e RABBITMQ_DEFAULT_USER=rabbitmq -e RABBITMQ_DEFAULT_PASS=rabbitmq
-v /data/docker-data/rabbitmq-data/:/var/rabbitmq/lib
-p 15672:15672 -p 5672:5672 -p 25672:25672 -p 61613:61613 -p 1883:1883
-e TZ="Asia/Shanghai"
rabbitmq:management