#!/bin/bash
#项目名
PROJECT=$1
#模块名
MODULE_SUFFIX=$2 #authority-server or zuul-server or file-server
#项目端口
PORT=$3 # 8764 or 8760 or 8768 or 8765
MODULE=$PROJECT-$MODULE_SUFFIX
#请填写服务器上nacos 的ip 端口命名空间等信息
NACOS_IP=172.16.34.57
NACOS_PORT=8848
NACOS_ID=ecb2fdd5-b0cd-4fb2-8ef7-2dc918f641da

pwd
cd /data
cd ./target_jar/${PROJECT}/$MODULE_SUFFIX/
pwd
cp -rf /data/shells/Dockerfile .
tar -zxvf $MODULE.tgz

echo "先删除后构建新镜像"
docker stop $MODULE
docker rm $MODULE
docker images|grep $MODULE|awk '{print $3}'|xargs docker rmi

TIME=`date "+%Y%m%d%H%M"`
IMAGE_NAME=${MODULE}:${TIME}
docker build --build-arg JAR_FILE=target/$MODULE.jar -t ${IMAGE_NAME} .

echo "重启容器"
docker run -idt --name $MODULE --restart=always --net=host \
                -v /data/projects/:/data/projects \
                -p $PORT:$PORT \
                -e NACOS_IP=$NACOS_IP \
                -e NACOS_PORT=$NACOS_PORT \
                -e NACOS_ID=$NACOS_ID \
                $IMAGE_NAME