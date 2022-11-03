#!/bin/bash

#project name
PROJECT=$1
#image name
IMAGE=$2
#project port
PORT=$3

echo "先删除镜像"
echo "mixlink@docker" | docker login --username gm_esupplychain --password-stdin registry.cn-hangzhou.aliyuncs.com
docker stop $PROJECT
docker rm $PROJECT
docker images | grep registry.cn-hangzhou.aliyuncs.com/mixlink/$PROJECT  | awk '{print $3}' | xargs docker rmi

echo "重启容器"
docker run -di  --name $PROJECT --restart=always \
           -p $PORT:$PORT \
           -v /data/logs/$PROJECT/:/$PROJECT/logs \
           -e PARAMS="--spring.profiles.active=test" \
           --privileged=true \
           $IMAGE