#!/bin/bash


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

docker run --name zeebe \
-e ZEEBE_LOG_LEVEL=debug \
-e JAVA_TOOL_OPTIONS="-Xms1024m -Xmx1024m" \
-e ZEEBE_PARTITIONS_COUNT="1" \
-e ZEEBE_CLUSTER_SIZE="1" \
-e ZEEBE_REPLICATION_FACTOR="1" \
-v /config/zeebe/application.yaml:/usr/local/zeebe/config/application.yaml \
-v /config/zeebe/startup.sh:/usr/local/bin/startup.sh \
-p 26500-26502:26500-26502 \
--network ipass-network --ip 172.20.0.9 \
--restart=always \
-d registry.cn-hangzhou.aliyuncs.com/mixlink/zeebe:1.1.0