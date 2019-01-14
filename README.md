# batchLearn
springboot2.0中使用java配置来实现springbatch
### 如何运行
将application.properties中的数据库修改为自己的数据库配置  
新建sql文件夹中的10张表
### 代码说明
CsvBatchConfig是自动执行跑批任务的配置文件，需修改application.properties中的spring.batch.job.enabled属性，在CsvBatchConfig文件上添加@Configuration和@EnableBatchProcessing注解，项目运行即自动执行跑批任务  

###### 参考书籍
《Spring Batch批处理框架》  
[springbatch3.X中文文档](https://www.bookstack.cn/read/SpringBatchReferenceCN/README.md)
