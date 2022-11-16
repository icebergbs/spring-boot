#!/bin/bash

current_dir=$(
   cd `dirname $0`
   echo `pwd`
)

function log() {
   message="[Redis Log]: $1 "
   echo -e "${message}" 2>&1 | tee -a ${current_dir}/install.log
}
echo -e "======================= 开始安装 =======================" 2>&1 | tee -a ${current_dir}/install.log
log "拷贝配置文件  -> /config"
mkdir -p /config
cp -r ./config/install.conf /config/install.conf
log "设置变量"
set -a
source  /config/install.conf
set +a

mkdir -p /data/redis/data

log "创建网络"
ipaas_network=$(
   docker network ls | grep ipass-network | awk '{print $2}'
)
if [[ ${ipaas_network} = "ipass-network" ]] ; then
  log "网络已创建"
else
  docker network create --driver=bridge --subnet=172.20.0.0/16  ipass-network
fi

#reids部署
log "拉取镜像"
docker pull redis:6.2
log "启动服务"
docker run --name redis \
-p ${ipaas_redis_port}:6379 \
-v /data/redis/data:/data \
--network ipass-network --ip 172.20.0.6 \
--restart=always \
-d redis:6.2 \
redis-server --appendonly yes --requirepass "${ipaas_redis_password}"

for b in {1..10}
do
   sleep 5
   http_code=`curl -sILw "%{http_code}\n" http://localhost:6379 -o /dev/null`
   if [[ $http_code == 000 ]];then
      log "服务启动中，请稍候 ..."
   elif [[ $http_code == 200 ]];then
      log "服务启动成功!"
      break;
   else
      log "服务启动出错!"
      exit 1
   fi
done

if [[ $http_code != 200 ]];then
   log "【错误】服务在等待时间内未完全启动！"
fi