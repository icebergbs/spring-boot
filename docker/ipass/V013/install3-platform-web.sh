#!/bin/bash

#docker login password
docker_pass=$1

project=platform-web

current_dir=$(
   cd `dirname $0`
   echo `pwd`
)

function log() {
   message="[platform-web Log]: $1 "
   echo -e "${message}" 2>&1 | tee -a ${current_dir}/install.log
}
echo -e "======================= 开始安装 =======================" 2>&1 | tee -a ${current_dir}/install.log
log "拷贝配置文件模板文件  -> /config/platform-web"
mkdir -p /config/platform-web
cp -r ./config/platform-web/* /config/platform-web/
log "设置自定义环境变量"
set -a
source  /config/platform-web/nginx.conf
set +a
sed -i 's/<ipaas_platform_linker_host>/'${ipaas_platform_linker_host}'/' /config/platform-web/default.conf
sed -i 's/<ipaas_zeebe_monitor_host>/'${ipaas_zeebe_monitor_host}'/' /config/platform-web/default.conf

echo "登录Docker"
echo $docker_pass | docker login --username gm_esupplychain --password-stdin registry.cn-hangzhou.aliyuncs.com

echo "先删除镜像" 2>&1 | tee -a ${current_dir}/install.log
docker stop $project
docker rm $project
docker images | grep registry.cn-hangzhou.aliyuncs.com/mixlink/$project  | awk '{print $3}' | xargs docker rmi

docker pull registry.cn-hangzhou.aliyuncs.com/mixlink/platform-web:0.13

docker run --name platform-web \
-p 80:8082 \
--network linkerNetwork --ip 172.18.0.14 \
-v /config/platform-web/default.conf:/etc/nginx/conf.d/default.conf \
--restart=always \
-d registry.cn-hangzhou.aliyuncs.com/mixlink/platform-web:0.13

for b in {1..25}
do
   sleep 3
   http_code=`curl -sILw "%{http_code}\n" http://localhost:8082 -o /dev/null`
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