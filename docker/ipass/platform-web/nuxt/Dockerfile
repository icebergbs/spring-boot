FROM registry.cn-hangzhou.aliyuncs.com/mixlink/node-nginx-1:16.16.0

MAINTAINER mixlink
ENV LANG en_US.UTF-8
ENV TZ=Asia/Shanghai

RUN mkdir -p /platform-web/dist
WORKDIR /platform-web/dist
COPY ../../.nuxt /platform-web/dist/.nuxt
COPY ../../static /platform-web/dist/static
COPY ../../package.json /platform-web/dist
COPY ../../nuxt.config.js /platform-web/dist

#阿里依赖源
RUN npm config set registry http://registry.npm.taobao.org
RUN npm install

# pm2
#RUN pm2 start npm --name "platform-web" -- run start

#nginx
COPY .ci/dev/nginx.conf  /usr/local/nginx/conf/nginx.conf
ADD .ci/dev/start.sh /platform-web/dist/start.sh
RUN dos2unix /platform-web/dist/start.sh
RUN chmod +x /platform-web/dist/start.sh
CMD sh /platform-web/dist/start.sh