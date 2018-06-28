# 第2章 Java 并发机制的底层实现

### volatile
  * volatile定义：Java编程语言允许线程访问共享变量，为了确保共享变量能被准确和一致地更新，线程应该确保通过排他锁单独获得这个变量。 
  * volatile修饰的变量进行写操作时会有Lock汇编指令： 
    1. 将当前处理器缓存行的数据写回到系统内存
    2. 这个写回内存的操作会使在其它CPU里缓存了该内存地址的数据无效

### synchronized的实现原理与应用
 * synchronized实现锁3种形式：
    1. 对于普通同步方法，锁是当前实例对象
    2. 对于静态同步方法，锁是当前类的Class对象
    3. 对于同步方法块，锁是Synchronized括号里配置的对象
    
    
    
 
 
 
 
 [Go back to README](README.md)
