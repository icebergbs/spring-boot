#!/bin/bash


current_dir=$(
   cd `dirname $0`
   echo `pwd`
)

function log() {
   message="[linker-boot Log]: $1 "
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

mkdir -p /data/linker-boot/logs

#start config
params="--spring.datasource.url=jdbc:mysql://${ipaas_mysql_host}:${ipaas_mysql_port}/${linker_boot_mysql_db}?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&createDatabaseIfNotExist=true \
--spring.datasource.username=${ipaas_mysql_user} \
--spring.datasource.password=${ipaas_mysql_password} \
--spring.redis.host=${ipaas_redis_host} \
--spring.redis.port=${ipaas_redis_port} \
--spring.redis.password=${ipaas_redis_password} \
--xxl.job.admin.addresses=${ipaas_xxl_job_admin_host} "

log "PARAMS=${params}"

docker run --name linker-boot \
--network ipass-network --ip 172.20.0.7 \
-p 7004:7004 -p 7005:7005 \
-v /data/linker-boot/logs:/linker-boot/logs \
-e PARAMS="${params}" \
--restart=always \
-d registry.cn-hangzhou.aliyuncs.com/mixlink/linker-boot:${ipaas_mirror_version}

for b in {1..10}
do
   sleep 5
   http_code=`curl -sILw "%{http_code}\n" http://localhost:7004 -o /dev/null`
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
