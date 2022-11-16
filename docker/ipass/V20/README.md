 #部署问题   
 1. install.sh部署支持场景    
    第一次部署      
    某个服务出现问题，紧急更新(版本不升级)      
    某个服务迭代升级，发布新版本(版本升级)      
    部署到一台、两台、三台等不同机器      
    客户提供mysql、redis可以直接使用(符合版本要求), 配置文件配置客户提供的地址，不安装服务  
      
 2. Mysql、Es、Redis
    install.sh不支持重新部署;  
    如果要重新部署，先手动删除镜像，然后使用install.sh安装;  
    
 3. 数据持久化位置  
    /data/es/data  
    /data/mysql/data  
    /data/redis/data  
    /data/zeebe  
    /data/zeebe-monitor/logs  
    
 4. install.conf
    地址配置，使用主机Ip    
          
 5. 配置文件    
    不同客户的配置文件留档(install.conf)   
    
 #部署使用  
 1. 修改 config/install.conf  
    根据客户提供的服务地址，修改文件中的 (服务地址、账密配置)  
 2. 使用 install.sh 安装服务