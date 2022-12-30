#!/bin/bash
cd /usr/local/nginx/sbin
./nginx
cd /platform-web/dist
# pm2专为在后台发送或运行应用程序的正常使用而设计.
# pm2-runtime 是为Docker容器设计的，它将应用程序置于前台，从而使容器保持运行状态，
pm2-runtime start npm --name "platform-web" -- run start