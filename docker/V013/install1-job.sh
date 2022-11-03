#!/bin/bash

#docker login password
docker_pass=$1
#project name
project=xxl-job-admin

current_dir=$(
   cd `dirname $0`
   echo `pwd`
)

function log() {
   message="[XXL-job Log]: $1 "
   echo -e "${message}" 2>&1 | tee -a ${current_dir}/install.log
}
echo -e "======================= 开始安装 =======================" 2>&1 | tee -a ${current_dir}/install.log
echo "登录Docker"
echo $docker_pass | docker login --username gm_esupplychain --password-stdin registry.cn-hangzhou.aliyuncs.com

echo "先删除镜像" 2>&1 | tee -a ${current_dir}/install.log
docker stop $project
docker rm $project
docker images | grep registry.cn-hangzhou.aliyuncs.com/mixlink/$project  | awk '{print $3}' | xargs docker rmi

docker pull registry.cn-hangzhou.aliyuncs.com/mixlink/xxl-job-admin:1.0
docker logout

#start config
params="--spring.datasource.url=jdbc:mysql://${ipaas_mysql_host}:${ipaas_mysql_port}/\
${xxl_job_mysql_db}?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai \
--spring.datasource.username=${ipaas_mysql_user} \
--spring.datasource.password=${ipaas_mysql_password}"

log "PARAMS=${params}"

docker run --name $project \
--network linkerNetwork --ip 172.18.0.12 \
-v /data/xxl-job:/xxl-job-admin/logs \
-p 7006:7006 \
-e PARAMS="${params}" \
--restart=always \
-d registry.cn-hangzhou.aliyuncs.com/mixlink/xxl-job-admin:1.0

for b in {1..25}
do
   sleep 3
   http_code=`curl -sILw "%{http_code}\n" http://localhost:7006/xxl-job-admin -o /dev/null`
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

echo -e "======================= 安装完成 =======================\n" 2>&1 | tee -a ${current_dir}/install.log