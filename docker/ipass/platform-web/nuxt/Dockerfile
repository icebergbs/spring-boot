FROM registry.cn-hangzhou.aliyuncs.com/mixlink/node-nginx:16.16.0

MAINTAINER mixlink
RUN mkdir -p /platform-web/dist
COPY . /platform-web/dist
WORKDIR /platform-web/dist
EXPOSE 3030
#阿里依赖源
RUN npm config set registry http://registry.npm.taobao.org
RUN npm cache clean --force
RUN npm install --no-package-lock
#构建
RUN npm run build
RUN npm start
#
RUN npm install -g pm2
RUN pm2 start npm --name "official" -- run start
CMD ["npm","start"]


ENV LANG en_US.UTF-8
ENV TZ=Asia/Shanghai
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY dist /usr/share/nginx/html