# 简介
SpringBoot + solr + webmagic 学习solr写的demo
## 1 概述
根据https://github.com/ameizi/solrj-example 改的，原项目是Spring,改成Springboot
solrj-example的webmagic价格和图片有点小问题，图片存取URL即可，爬取的数据从存入数据库改成直接放入solr
## 2 webmagic使用自定义Downloader 
chromedrive是2.25版本的，支持的Chrome版本v53-55，如果版本不一致自行替换chromedrive或修改chrome版本
## 3 Solr启动
本人用的solr6.5.1，使用自带的jetty，无需配置，tomcat启动需8或者更高版本，jdk1.8或以上 
windows启动  cd solr目录/bin + solr start   
linux启动  cd cd solr目录/bin + ./solr start -force 
solr windows tomcat配置参考http://blog.csdn.net/liuzhen917/article/details/70328214?utm_source=itdadao&utm_medium=referral
solr CentOS tomcat配置参考http://blog.csdn.net/l1028386804/article/details/70199983
## 4 Solr配置core:
1. jetty 在\server\solr中添加product文件夹，
    tomcat 在web.xml中配置的  <env-entry-value>F:\solr_home</env-entry-value> 路径F:\solr_home下创建product
2. 复制solr目录\server\solr\configsets\basic_configs 下的conf至上面的product目录下
3. 启动solr在core admin 中添加core  product
4. 添加field ,把Product.java 中的属性都添加入core product中，可在页面scheme中添加或修改配置文件

## 5 爬虫运行
运行爬虫DemoApplicationTests.main获取数据

## 6 SpringBoot启动后界面主页
http://localhost:8111/jd
