注意：
1、httpclient的属性文静命名必须为：httpclient.properties
该文件在类路径下，将会覆盖包路径下的默认配置
2、HttpClientComponent类可以直接注入到Spring中即可使用,静态方法创建实例
3、断线重连开关需要满足开关开启，并且重连次数大于0才执行重连
