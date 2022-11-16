#!/bin/bash

current_dir=$(
   cd `dirname $0`
   echo `pwd`
)

function log() {
   message="[Mysql Log]: $1 "
   echo -e "${message}" 2>&1 | tee -a ${current_dir}/install.log
}
echo -e "======================= 开始安装 =======================" 2>&1 | tee -a ${current_dir}/install.log
log "拷贝配置文件  -> /config/mysql"
mkdir -p /config/mysql
cp -r ./config/mysql/my.cnf /config/mysql/my.cnf
cp -r ./config/install.conf /config/install.conf
log "设置变量"
set -a
source  /config/install.conf
set +a

mkdir -p /data/mysql/log
mkdir -p /data/mysql/data


log "拉取镜像"
#mysql部署 5.7
docker pull mysql:5.7
log "启动服务"
docker run --name mysql \
-p ${ipaas_mysql_port}:3306 \
--network ipass-network --ip 172.20.0.5 \
-v /data/mysql/log:/var/log/mysql \
-v /data/mysql/data:/var/lib/mysql \
-v /config/mysql/my.cnf:/etc/mysql/conf.d/my.cnf \
-e MYSQL_ROOT_PASSWORD=${ipaas_mysql_password} \
--restart=always \
-d mysql:5.7

for b in {1..10}
do
   sleep 5
   http_code=`curl -sILw "%{http_code}\n" http://localhost:3306 -o /dev/null`
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
