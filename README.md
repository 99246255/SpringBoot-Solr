SpringBoot + solr + webmagic
学习solr写的demo
根据https://github.com/ameizi/solrj-example改的，这个是Spring的
solrj-example的webmagic有点小问题，价格和图片获取完需额外处理，solrj-example中爬取的数据是在数据库中的
我这边改成了直接放入solr里面
原有的界面挺好的，想嵌入的，发现springboot用jsp挺麻烦的，而且对jsp不熟就自己弄了个html

chromedrive是2.25版本的，支持的Chrome版本v53-55，如果版本不一致自行替换chromedrive或修改chrome版本

本人用的solr6.5.1，使用自带的jetty，无需配置，tomcat启动需8或者更高版本，jdk1.8或以上 
windows启动  cd solr目录/bin + solr start   
linux启动  cd cd solr目录/bin + ./solr start -force 
solr windows tomcat配置参考http://blog.csdn.net/liuzhen917/article/details/70328214?utm_source=itdadao&utm_medium=referral
solr CentOS tomcat配置参考http://blog.csdn.net/l1028386804/article/details/70199983

配置core:
jetty 在\server\solr中添加product文件夹，
tomcat 在web.xml中配置的  <env-entry-value>F:\solr_home</env-entry-value> 路径F:\solr_home下创建product
复制solr目录\server\solr\configsets\basic_configs 下的conf至上面的product目录下
启动solr在core admin 中添加core  product
添加field ,把Product.java 中的属性都添加入core product中，可在页面scheme中添加或修改配置文件

运行爬虫DemoApplicationTests.main获取数据

启动DemoApplication.main 
http://localhost:8111/index 查看和搜索