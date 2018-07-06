# 第5章 Java中的锁

### Lock接口
 > Lock接口提供的synchronized关键字不具备的主要特性
 
 | 特性 | 描述 | 
 | :-: | :-: |
 | 尝试非阻塞的获取锁 | 当前线程尝试获取锁，如果这一时刻锁没有被其它线程获取到，则成功获取并持有锁 | 
 | 能被中断地获取锁 | 与synchronized不同，获取到锁的线程能够响应中断，当获取到锁的线程被中断时，中断异常将会被抛出，同时锁会被释放 | 
 | 超时获取锁 | 在指定的截止时间之前获取锁，如果截止时间到了仍旧无法获取锁，则返回 | 
 
 > Lock API
 
 | 方法名称 | 描述 | 
 | :-: | :-: | 
 | void lock() | 获取锁，调用该方法当前线程将会获取锁，当锁获得后，从该方法返回 | 
 | void lockInterruptibly() <br/> throws InterruptedException | 可中断的获取锁，该方法会响应中断，即在锁的获取中可以中断当前线程 |
 | boolean tryLock() | 尝试非阻塞的获取锁，调用该方法后立刻返回，如果能够获取则返回true，否则返回false | 
 | boolean tryLock(long time, TimeUnit unit) <br/> throws InterruptedException | 超时获取锁，当前线程在以下3种情况下会返回：<br/> 1. 当前线程在超时时间内获取锁 <br/> 2. 
 当前线程在超时时间内被中断 <br/> 3.  






 [Go back to README](README.md)