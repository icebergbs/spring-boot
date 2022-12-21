

Linux Alpine安装 Nginx
安装需要编译Nginx的扩展
apk add wget gcc g++ make
安装Nginx URL重定向，正则表达式模块pcre
Pcre 源码下载地址：https://ftp.pcre.org/pub/pcre
Nginx 源码下载地址：http://nginx.org/download/

1. 先安装下载pcre模块，选择需要的pcre版本，这里选择8.44版本

cd /home # 安装到Home目录中
wget https://ftp.pcre.org/pub/pcre/pcre-8.44.tar.gz && \
wget https://nchc.dl.sourceforge.net/project/pcre/pcre/8.44/pcre-8.44.tar.gz && \
tar xvf pcre-8.44.tar.gz
> 注意：nginx安装pcre，不需要对pcre模块进行编译安装


2. 下载需要的nginx版本，解压

cd /home && \
wget "http://nginx.org/download/nginx-1.21.0.tar.gz" && \
tar xvf nginx-1.21.0.tar.gz
2. 编译安装nginx，安装到/usr/local/nginx目录中

cd /home/nginx-1.18.0 && \
./configure --prefix=/usr/local/nginx --with-pcre=/home/pcre-8.44 --without-http_gzip_module && \
make && make install && \
ln -s /usr/local/nginx/sbin/nginx /usr/sbin/ && \
mkdir -p /usr/local/nginx/conf/vhost/
3.这里编译安装pcre模块，--with-pcre指定的是pcre下载的源码地址，而不是编译后的pcre安装地址，如有的安装在/usr/local/pcre，不是这个安装地址，而是刚wget后，tar解压后的地址，这里是/home/pcre-8.44

4. 执行：nginx启动nginx

5. 访问地址：localhost，出现如下页面