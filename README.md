# TiDB's contributors
这个项目用于展示https://github.com/pingcap/tidb](https://github.com/pingcap/tidb) 这个repo下的所有的代码贡献者

[部署地址](http://119.3.183.144)  :fire::fire::fire::fire::fire:注：为了获得最佳体验在1080P的分辨率下需要将页面缩放至90%，如果是2K分辨率则缩放至100%，另外，因为部署的这台服务器性能实在是令人捉急，所以如果看不到数据，请不要刷新，稍微等它一会数据就显示出来了:persevere:

### 页面效果：

### 	Contributors List

![批注 2020-08-21 015355](https://edu-102.oss-cn-beijing.aliyuncs.com/TiDB/SDSA.png)
### Commit history

![image-20200821032032287](https://edu-102.oss-cn-beijing.aliyuncs.com/TiDB/image-20200821032032287.png)

![image-20200821032137416](https://edu-102.oss-cn-beijing.aliyuncs.com/TiDB/image-20200821032137416.png)

![image-20200821032257017](https://edu-102.oss-cn-beijing.aliyuncs.com/TiDB/image-20200821032257017.png)

### The top30 tontributors

![image-20200821032454663](https://edu-102.oss-cn-beijing.aliyuncs.com/TiDB/image-20200821032454663.png)

### 实现流程：

* 抓取数据

利用crawler包下的`pr_list`和`pr_detail`这两个爬虫去得到 [https://github.com/pingcap/tidb](https://github.com/pingcap/tidb) 中的所有PR以及其作者的信息。

目录结构：

```txt
 pr_detail
│   └── prdetail
│       ├── prdetail
│       │   ├── avatars									#爬取到的头像存入这里
│       │   ├── __init__.py
│       │   ├── items.py							    #定义需要爬取的字段
│       │   ├── middlewares.py							#定义被反爬时的处理办法
│       │   ├── pipelines.py						    #管道用于将得到的数据写入文件  
│       │   ├── settings.py
│       │   └── spiders
│       │       ├── __init__.py
│       │       ├── pr_detail.py						#定义要爬取的页面及需要被爬的标签
│       └── scrapy.cfg
└── pr_list
    └── prlist
        ├── prlist
        │   ├── __init__.py
        │   ├── items.py
        │   ├── middlewares.py
        │   ├── pipelines.py
        │   ├── settings.py
        │   └── spiders
        │       ├── __init__.py
        │       ├── pr_list.py
        └── scrapy.cfg

```

* 清洗得到的数据

由于爬取到的数据实际上是一个长字符串，但最后数据肯定是都要进数据库的，所以利用convertor和test包下的类来对数据进行分类和格式化并放入数据库。目录结构：

```txt
src
 ├── ContentModify.java									
 └── PathExtractor.java										 #取出每个PR的链接，为爬取PR详情提供目标

```

* 后端构建

后端用Spring Boot和MyBatis-Plus来搭建，Redis做缓存。目录结构：

```txt
src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── xonlab
│   │   │           └── contributor
│   │   │               ├── common        
│   │   │               │   ├── ResultCode.java                #定义状态码
│   │   │               │   └── R.java                         #用于封装返回结果的包装类
│   │   │               ├── config
│   │   │               │   ├── MetaObjectConfig.java          #设置在新增数据时要被初始化的字段。例如：gmt_create
│   │   │               │   ├── MybatisConfig.java             #配置MyBatis-Plus中的逻辑删除插件和分页插件
│   │   │               │   └── SwaggerConfig.java             #Swagger配置
│   │   │               ├── ContributorApplication.java        #Spring Boot的主启动类
│   │   │               ├── controller
│   │   │               │   ├── PrDetailController.java        #返回和PR详情有关的数据
│   │   │               │   └── PrListController.java		   #返回和PR作者有关的数据
│   │   │               ├── entity							   #实体类
│   │   │               │   ├── PrDetail.java				   
│   │   │               │   └── PrList.java
│   │   │               ├── mapper							   
│   │   │               │   ├── PrDetailMapper.java			
│   │   │               │   ├── PrListMapper.java
│   │   │               │   └── xml
│   │   │               │       ├── PrDetailMapper.xml	
│   │   │               │       └── PrListMapper.xml
│   │   │               ├── service
│   │   │               │   ├── impl
│   │   │               │   │   ├── PrDetailServiceImpl.java
│   │   │               │   │   └── PrListServiceImpl.java
│   │   │               │   ├── PrDetailService.java
│   │   │               │   └── PrListService.java
│   │   │               └── vo
│   │   │                   ├── HistoryVo.java
│   │   │                   ├── MostContributorVo.java
│   │   │         
│   │   └── resources
│   │       ├── application.yml							#配置文件							  
│   │       └── banner.txt
│   └── test
│       └── java
│           └── com
│               └── xonlab
│                   └── contributor
│                       ├── AuthorAvatarBatch.java		#拼接PR作者的名字以及前端的资源文件夹路径作为头像的地址来存入数据库
│                       ├── DataFormatter.java			#去除pr_detail字段中不必要的符号。例如\n
│                       ├── PrDetailDataBatch.java		#将爬到的PR详情存入数据库
│                       └── PrListDataBatch.java		#将爬到的PR列表存入数据库

```

* 前端构建

前端采用Vuetify和echarts作为基础框架

```txt
.
├── api													#定义被调用的后端接口
│   ├── prdetail.js			
│   └── prlist.js
├── App.vue
├── assets
│   ├── logo.png
│   └── logo.svg
├── components
│   ├── Footer.vue							 			#页脚
│   └── Navbar.vue										#导航栏
├── main.js
├── plugins
│   └── vuetify.js										#引入Vuetify和插件
├── router
│   └── index.js
├── utils
│   └── request.js										#封装axios
└── views												#页面文件
    ├── CommitHistory.vue
    ├── Contributors.vue										
    └── MostContributor.vue
```

