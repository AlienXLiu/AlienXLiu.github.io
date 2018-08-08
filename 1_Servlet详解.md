# Servlet 解析

### 什么是Servlet
  * 运行在服务器端的JAVA应用程序

### 怎么创建servlet
  * JavaBean 继承 HttpServlet

### servlet 怎么执行
  * 在WEB-INF/web.xml文件中配置servlet, servlet-mapping

### servlet 执行流程 
  * 生命周期
    1. Servlet 通过调用 init () 方法进行初始化。
    2. Servlet 调用 service() 方法来处理客户端的请求。
    3. Servlet 通过调用 destroy() 方法终止（结束）。
    4. 最后，Servlet 是由 JVM 的垃圾回收器进行垃圾回收的。
    
  * 执行流程：
    请求 -> web.xml -> 查询servlet -> 如果找到则service方法进行任务分发处理请求。  

    
    
    
 
 
 
 
 [Go back to README](README.md)
