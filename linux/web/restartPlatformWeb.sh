#!/bin/bash

if [ ! -f  "/data/ipass/platform-web.tgz" ]; then
    echo "platform-web.tgz file not exist !"
fi

echo "tar file"
tar -zxvf /data/ipass/platform-web.tgz -C /data/ipass/dist/
sleep 10

echo "delete old file"
rm -rf /data/projects/platform-web/*

echo "move new file"
mv -f /data/ipass/dist/* /data/projects/platform-web/

echo "file replace sucess"