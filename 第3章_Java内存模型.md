# 第3章 Java 内存模型

### Volatile的内存语义：
1. volatile特性
   可见性：对一个volatile变量的读，总是能看到（任意线程）对这个volatile变量最后的写入。
   原子性：对任意单个volatile变量的读/写具有原子性，但类似于volatile++这种复合操作不具有原子性。
2. volatile写-读建立的happens-before关系
   从JSR-133开始（JDK1.5开始），volatile变量的写-读可以实现线程之间的通信。
   * volatile的写-读与锁的释放-获取有相同的内存效果
   * volatile写和锁的释放有相同的内存语义
   * volatile读与锁的获取有相同的内存语义
3. volatile写-读的内存语义
   * volatile写的内存语义：当写一个volatile变量时，JMM会把该线程对应的本地内存中的共享变量值刷新到主内存。
   * volatile读的内存语义：当读一个volatile变量时，JMM会把该线程对应的本地内存置为无效，线程接下来将从主内存中读取共享变量。

### Volatile内存语义的实现：

<table>
    <tr>
       <td colspan="4" align="center" >volatile重排序规则</td>    
    </tr>
    <tr>
        <th>是否能重排</th>
        <th colspan="3">第二个操作</th>
    </tr>
    <tr>
        <td>第一个操作</td>
        <td>普通读 / 写</td>
        <td>volatile读</td>
        <td>volatile写</td>
    </tr>
    <tr>
        <td>普通读 / 写</td>
        <td colspan="2"></td>
        <td>NO</td>
    </tr>
    <tr>
        <td>volatile 读</td>
        <td>NO</td>
        <td>NO</td>
        <td>NO</td>
    </tr> 
    <tr>
        <td>volatile 写</td>
        <td></td>
        <td>NO</td>
        <td>NO</td>
    </tr>
</table>

* 当第二个操作是volatile写时，不管第一个操作是什么，都不能重排序
* 当第一个操作是volatile读时，不管第二个操作是什么，都不能重排序
* 当第一个操作是volatile写时，第二个操作是volatile读时，不能重排序

**JMM内存屏蔽插入策略：**
* 在每个volatile写操作的前面插入一个StoreStore屏障
* 在每个volatile写操作的后面插入一个StoreLoad屏障
* 在每个volatile读操作的后面插入一个LoadLoad屏障
* 在每个volatile读操作的后面插入一个LoadStore屏障

### 锁的内存语义：

锁的释放-获取建立的happens-before关系 - 锁除了让临界区互斥执行外，还可以让释放锁的线程向获取同一锁的线程发送消息。

### 锁的释放和获取的内存语义：
* 锁的释放与volatile写有相同的内存语义
* 锁的获取与volatile读有相同内存语义

### 锁内存语义的实现：
> 公平锁\非公平锁内存语义 
* 公平锁\非公平锁释放时，最后都要写一个volatile变量state 
* 公平锁获取时，首先会去读volatile变量 
* 非公平锁获取时，首先会用CAS更新volatile变理，这个操作同时具有volatile读和volatile写的内存语义。  
> 锁的释放-获取的内存语义的实现至少有以下两种方式 
* 利用volatile变量写-读所具有的内存语义 
* 利用CAS所附带的volatile读和volatile写的内存语义 

### concurrent包实现：
1. 首先声明共享变量
2. 然后使用CAS的原子条件更新来实现线程之间的同步
3. 同时，配合以volatile的读/写和CAS所具有的volatile读写的内存语义来实现线程之间的通信。

### final域的内存语义：
1. final域的重排序规则
* 在构造函数内对一个final域的写入，与随后把这个被构造对象的引用域赋值给一个引用变量，这两个操作之间不能重排序
* 初次读一个包含final域的对象的引用，与随后初次读这个final域，这两个操作之间不能重排序
2. 写final域的重排序规则
 1. JMM禁止编译器把final域的写重排序到构造函数之外
 2. 编译器会在final域的写之后，构造函数return之前，插入一个storestore屏障。这个屏障禁止处理器把final域的写重排序到构造函数之外。
3. 读final域的重排序规则：在一个线程中，初次读对象引用与初次读该对象包含的final域，JMM禁止处理器重排序这两个操作。编译器会在读final域操作的前面插入一个LoadLoad屏障。
4. final域为引用类型：
对于引用类型，写final域的重排序规则对编译器和处理器加了如下约束：在构造函数内对一个final引用的对象的成员域的写入，与随后在构造函数外把这个被构造对象的引用赋值给一个引用变量，这两个操作之间不能重排序。
5. 为什么final引用不能从构造函数内“溢出”
写final域的重排序规则可以确保：在引用变量为任意线程可见之前，该引用变更指向的对象的final域已经在构造函数中被正确初始化过了。其实，要得到这个效果，还需要一个保证：在构造函数内部，不能让这个被构造对象的引用为其他线程所见，也就是对象引用不能在构造函数中“逸出”。
6. JSR-133增强final语义：
只要对象是正确构造的（被构造对象的引用在构造函数中没有“逸出”），那么不需要使用同步（指lock和volatile的使用）就可以保证任意线程都能看到这个final域在构造函数中被初始化之后的值。 

### happens-before:
1. JMM的设计 
   * JMM把happens-before要求禁止的重排序分为  
       > 会改变程序执行结果的重排序：JMM要求编译器和处理器必须禁止这种重排 

       > 不会改变程序执行结果的重排序：JMM对编译器和处理器不做要求（允许重排） 

2. happens-before的定义 
    * JSR-133使用happens-before的概念来指定两个操作之间的执行顺序。由于这两个操作可以在一个线程之内，也可以是在不同线程之间，因此，JMM可以通过happens-before关系向程序员提供跨线程的内存可见性保证。

    * JSR-133对happens-before关系的定义如下
      > 如果一个操作happens-before另一个操作，那么第一个操作的执行结果将对第二个操作可见，而且第一个操作的执行顺序排在第二个操作之前。 
      
      > 两个操作之间存在happens-before关系，并不意味着java平台的具体实现必须要按照happens-before指定的顺序来执行。如果重排序之后的执行结果，与按happens-before关系来执行的结果一致，那么这种重排序并不非法。
      
    * happens-before关系本质和as-if-serial语义是一回事
      > as-if-serial语义保证单线程内程序的执行结果不被改变，happens-before关系保证正确同步的多线程程序的执行结果不被改变。
      
      > as-if-serial语义给编写单线程程序的程序员创造了一个幻境：单线程程序是按程序的顺序来执行的。happens-before关系给编写正确同步的多线程程序创造一个幻境：正确同步的多线程程序是按happens-before指定顺序来执行。
3. happens-before规则
   * 程序顺序规则：一个线程中的每个操作，happens-before于该线程中的任意后续操作 
   * 监视器锁规则：对一个锁的解锁，happens-before于随后对这个锁的加锁
   * volatile变量规则：对一个volatile域的写，happens-before于任意后续对这个volatile域的读
   * 传递性：如果A happens-before B,且 B happens-before C，那么A happen-before C.
   * start()规则：如果线程A执行操作ThreadB.start()，那么A线程的ThreadB.start()操作happens-before于线程B中的任意操作。
   * join()规则：如果线程A执行操作ThreadB.join()并成功返回，那么线程B中的任意操作happens-before于线程A从ThreadB.join()操作成功返回。
