#!/bin/bash
cd /usr/local/nginx/sbin
./nginx
cd /platform-web/dist
pm2-runtime start npm --name "platform-web" -- run start