#!/bin/bash

#docker login password
docker_pass=$1
#project name
project=zeebe

current_dir=$(
   cd `dirname $0`
   echo `pwd`
)

function log() {
   message="[ZEEBE Log]: $1 "
   echo -e "${message}" 2>&1 | tee -a ${current_dir}/install.log
}
echo -e "======================= 开始安装 =======================" 2>&1 | tee -a ${current_dir}/install.log
log "拷贝配置文件模板文件  -> /config/zeebe"
mkdir -p /config/zeebe
cp -r ./config/zeebe/* /config/zeebe/

echo "登录Docker"
echo $docker_pass | docker login --username gm_esupplychain --password-stdin registry.cn-hangzhou.aliyuncs.com

echo "先删除镜像" 2>&1 | tee -a ${current_dir}/install.log
docker stop $project
docker rm $project
docker images | grep registry.cn-hangzhou.aliyuncs.com/mixlink/$project  | awk '{print $3}' | xargs docker rmi

echo "拉取镜像" 2>&1 | tee -a ${current_dir}/install.log
#zeebe 部署
docker pull registry.cn-hangzhou.aliyuncs.com/mixlink/zeebe:1.1.0
docker logout

echo "部署Zeebe" 2>&1 | tee -a ${current_dir}/install.log
docker run -e ZEEBE_LOG_LEVEL=debug \
-e JAVA_TOOL_OPTIONS="-Xms1024m -Xmx1024m" \
-e ZEEBE_PARTITIONS_COUNT="1" \
-e ZEEBE_CLUSTER_SIZE="1" \
-e ZEEBE_REPLICATION_FACTOR="1" \
-v /config/zeebe/application.yaml:/usr/local/zeebe/config/application.yaml \
-v /config/zeebe/startup.sh:/usr/local/bin/startup.sh \
--name $project \
-p 26500-26502:26500-26502 \
--network linkerNetwork --ip 172.18.0.9 \
--restart=always \
-d registry.cn-hangzhou.aliyuncs.com/mixlink/zeebe:1.1.0

echo -e "======================= 安装完成 =======================\n" 2>&1 | tee -a ${current_dir}/install.log