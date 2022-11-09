#!/bin/bash

current_dir=$(
   cd `dirname $0`
   echo `pwd`
)

function log() {
   message="[Es Log]: $1 "
   echo -e "${message}" 2>&1 | tee -a ${current_dir}/install.log
}
echo -e "======================= 开始安装 =======================" 2>&1 | tee -a ${current_dir}/install.log

log "拷贝配置文件模板文件  -> /config/es"
mkdir -p /data/es
chmod 777 /data
chmod 777 /data/es
mkdir -p /config/kibana
cp -r ./config/kibana/* /config/kibana/

#es部署 7.16.2
docker pull elasticsearch:7.16.2
docker run -d --name=es \
-p 9200:9200 -p 9300:9300 \
-v /data/es:/usr/share/elasticsearch/data \
-e "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms512m -Xmx1024m" \
--network linkerNetwork --ip 172.18.0.2 \
--restart=always \
elasticsearch:7.16.2

#kibana 部署 7.16.2
docker pull kibana:7.16.2
docker run --name kibana \
-e ELASTICSEARCH_HOSTS=http://172.18.0.2:9200 \
-v /config/kibana/kibana.yml:/usr/share/kibana/config/kibana.yml \
-p 5601:5601 \
--network linkerNetwork \
--ip 172.18.0.3 \
--restart=always \
-d kibana:7.16.2

echo -e "======================= 安装完成 =======================\n" 2>&1 | tee -a ${current_dir}/install.log