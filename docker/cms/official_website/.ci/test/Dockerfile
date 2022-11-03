FROM registry.cn-hangzhou.aliyuncs.com/mixlink/nginx:1.16-alpine
MAINTAINER mixlink
VOLUME /tmp
ENV LANG en_US.UTF-8

ENV TZ=Asia/Shanghai
COPY .ci/test/nginx.conf /etc/nginx/conf.d/default.conf
COPY dist /usr/share/nginx/html