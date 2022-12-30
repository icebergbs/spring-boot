#!/bin/bash
cd /usr/local/nginx/sbin
./nginx
cd /platform-web/dist
# pm2-runtime pm2为解决Docker 前台运行
pm2-runtime start npm --name "platform-web" -- run start