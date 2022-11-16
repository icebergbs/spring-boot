#!/bin/bash

current_dir=$(
   cd `dirname $0`
   echo `pwd`
)

function log() {
   message="[Install Log]: $1 "
   echo -e "${message}" 2>&1 | tee -a ${current_dir}/install.log
}

# 服务名称
project_arr=("es" "mysql" "redis" "zeebe" "xxl-job-admin" "linker-boot" "platform-linker" "platform-web" "zeebe-monitor")

echo -e "======================= 开始安装 =======================\n
             0) 输入 0  安装 Es                                  \n
             1) 输入 1  安装 Mysql                               \n
             2) 输入 2  安装 Redis                               \n
             3) 输入 3  安装 Zeebe                               \n
             4) 输入 4  安装 Xxl-job-admin                       \n
             5) 输入 5  安装 linker-boot                         \n
             6) 输入 6  安装 platform-linker                     \n
             7) 输入 7  安装 platform-web                        \n
             8) 输入 8  安装 zeebe-monitor                       \n" 2>&1 | tee -a ${current_dir}/install.log
read service
log "service= ${service}"
project=${project_arr[${service}]}
log "project= ${project}"


ipaas_network=$(
   docker network ls | grep ipass-network | awk '{print $2}'
)
if [[ ${ipaas_network} = "ipass-network" ]] ; then
  log "ipass-network 网络已创建"
else
  log "新建网络 ipass-network"
  docker network create --driver=bridge --subnet=172.20.0.0/16  ipass-network
fi


case $service in
    0)
      log "Es变更需手动删除镜像"
      ;;
    1)
      log "Mysql变更需手动删除镜像"
      ;;
    2)
      log "Redis变更需手动删除镜像"
      ;;
    [3-8])
      log "登录Docker"
      log "mixlink@docker"| docker login --username gm_esupplychain --password-stdin registry.cn-hangzhou.aliyuncs.com
      log "先删除镜像" 2>&1 | tee -a ${current_dir}/install.log
      docker stop $project
      docker rm $project
      docker images | grep registry.cn-hangzhou.aliyuncs.com/mixlink/$project  | awk '{print $3}' | xargs docker rmi
      log "拉取镜像" 2>&1 | tee -a ${current_dir}/install.log
      case $service in
          3)
            docker pull registry.cn-hangzhou.aliyuncs.com/mixlink/zeebe:1.1.0
            ;;
          4)
            docker pull registry.cn-hangzhou.aliyuncs.com/mixlink/xxl-job-admin:1.0
            ;;
          [5-8])
            docker pull registry.cn-hangzhou.aliyuncs.com/mixlink/$project:${ipaas_mirror_version}
            ;;
          *)
            ;;
       esac
      docker logout
      ;;
    *)
      ;;
esac

case $service in
    0)
      /bin/bash ./install1-es.sh
      ;;
    1)
      /bin/bash ./install1-mysql.sh
      ;;
    2)
      /bin/bash ./install1-redis.sh
      ;;
    3)
      /bin/bash ./install2-zeebe.sh
      ;;
    4)
      /bin/bash ./install2-xxl-job-admin.sh
      ;;
    5)
      /bin/bash ./install3-linker-boot.sh
      ;;
    6)
      /bin/bash ./install4-platform-linker.sh
      ;;
    7)
      /bin/bash ./install4-platform-web.sh
      ;;
    8)
      /bin/bash ./install4-zeebe-monitor.sh
      ;;
    *)
      ;;
esac

echo -e "======================= 安装完成 =======================\n" 2>&1 | tee -a ${current_dir}/install.log