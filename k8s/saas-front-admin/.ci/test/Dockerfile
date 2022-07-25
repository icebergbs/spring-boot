FROM nginx
MAINTAINER mixlink
VOLUME /tmp
ENV LANG en_US.UTF-8
# This file is a template, and might need editing before it works on your project.
FROM nginx:1.16-alpine

ENV TZ=Asia/Shanghai
COPY .ci/test/nginx.conf /etc/nginx/conf.d/default.conf
COPY dist /usr/share/nginx/html