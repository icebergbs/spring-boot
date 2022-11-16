#!/bin/bash


current_dir=$(
   cd `dirname $0`
   echo `pwd`
)

function log() {
   message="[zeebe-monitor Log]: $1 "
   echo -e "${message}" 2>&1 | tee -a ${current_dir}/install.log
}
echo -e "======================= 开始安装 =======================" 2>&1 | tee -a ${current_dir}/install.log
log "拷贝配置文件模板文件  -> /config/"
mkdir -p /config/
cp -r ./config/install.conf /config/install.conf
log "设置自定义环境变量"
set -a
source  /config/install.conf
set +a


#start config
params="--spring.datasource.url=jdbc:mysql://${ipaas_mysql_host}:${ipaas_mysql_port}/${zeebe_monitor_mysql_db}?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai \
--spring.datasource.username=${ipaas_mysql_user} \
--spring.datasource.password=${ipaas_mysql_password} \
--elasticsearch.url=${ipaas_es_host} \
--zeebe.client.broker=${ipaas_zeebe_host} \
--linker.host=${ipaas_linker_boot_host}"

log "PARAMS=${params}"

docker run --name zeebe-monitor \
--network ipass-network --ip 172.20.0.10 \
-p 8085:8085 \
-e PARAMS="${params}" \
--restart=always \
-v /data/zeebe-monitor/logs:/zeebe-monitor/logs \
-d registry.cn-hangzhou.aliyuncs.com/mixlink/zeebe-monitor:${ipaas_mirror_version}

for b in {1..25}
do
   sleep 3
   http_code=`curl -sILw "%{http_code}\n" http://localhost:8085 -o /dev/null`
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