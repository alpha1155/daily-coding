https://maochunguang.github.io/java-interview/interview_topn/toutiao.html#%E6%8A%80%E6%9C%AF%E7%82%B9%E6%B1%87%E6%80%BB

## 1.进程和线程的区别？使用线程这能节约时间吗？

1. **进程是操作系统进行资源分配和保护的独立单元，而线程是程序执行和调度的基本单元，是进程内的一条执行路径。**
2. 节约时间
   1. 对于**计算密集型**任务，目标是利用多核实现**并行**来压缩计算时间。
   2. 对于**I/O密集型**任务，目标是利用**并发**来填充I/O等待时间，提高CPU利用率和系统吞吐量。

## 2.分析一下线程池的参数？线程池工作流程？四种预定义的线程池？各自的workqueue size是多少？

1. ```java
   ThreadPoolExecutor executor = new ThreadPoolExecutor(
   	int corePoolSize,
   	int maximumPoolSize,
   	long keepAliveTime,
   	TimeUnit unit,
   	BlockingQueue<Runnable> workQueue,
   	ThreadFactory threadFactory,
   	RejectedExecutionHandler handler
   );
   ```

2. 工作流程

   1. 提交任务
   2. 核心线程是否已满？
      1. 否--即使有线程空闲，也会立即创建一个新的核心线程
      2. 是，下一步
   3. 工作队列是否已满？
      1. 否，放入工作队列等待执行
      2. 是，下一步
   4. 线程数是否达到最大值
      1. 否，创建一个创建一个新的非核心线程执行任务
      2. 是，下一步
   5. 触发拒绝策略
      1. 线程和队列都已饱和，无法处理新任务，调用rejectedExecutionHandler处理这个被拒绝的任务

3. 四种预定义线程池的区别和workqueue的大小

   1. | 类型                                | workqueue size                         | 使用场景                                                     |
      | ----------------------------------- | -------------------------------------- | ------------------------------------------------------------ |
      | FixedThreadPool——固定大小线程池     | LinkdBlockingQueue——Integer.MAX_VALUE  | 线程数固定                                                   |
      | CacheThreadPool——可缓存线程池       | SynchronousQueue                       | **容量 0**，不存储任务 <br />**生产者-消费者直接交换**，put() 必须等 take() <br />**动态线程**：无空闲线程就创建，最多 Integer.MAX_VALUE<br /> **60s 回收空闲线程**<br />**优点**：响应快，适合短任务 <br />**风险**：任务突刺 → 线程爆炸 → OOM |
      | SingleThreadExecutor——单线程线程池  | LinkedBlockingQueue——Integer.MAX_VALUE | 所有任务顺序执行，有任务无线堆积的风险                       |
      | ScheduledThreadPool——定时任务线程池 | DelayedWorkQueue——无界                 | 用于执行定时或周期性任务。                                   |

   

   ## 3、JAVA怎么保持线程同步？常用的锁有什么？java锁升级是怎么样的？

   1. Java 通过 synchronized、Lock（ReentrantLock）、原子类（CAS）、并发容器、线程安全工具等实现线程同步；常用锁有偏向锁、轻量级锁、重量级锁；锁升级路径为：无锁 → 偏向锁 → 轻量级锁 → 重量级锁，JVM 自动优化，减少系统调用。

   

   ## 4、synchonized和lock的区别？synchonized优化

   1. | 特性       | synchronized                                            | lock                                                         |
      | ---------- | ------------------------------------------------------- | ------------------------------------------------------------ |
      | 定义       | JAVA关键字，JVM层面，自动加锁、释放锁                   | 接口，调用lock(),  unlock()                                  |
      | 灵活       | 不灵活                                                  | **非常灵活**。可以跨方法加锁和解锁；<br />可以尝试非阻塞地获取锁（`tryLock`）；可以响应中断。 |
      | 等待可中断 | 不可                                                    | 可以，调用lockInterruptibly()                                |
      | 公平锁     | 仅非公平锁                                              | both，公平锁- new ReentrantLock(true)                        |
      | 条件队列   | 单一，通过wait(), notify(), notifyAll()操作一个等待队列 | 多个，通过 `newCondition()`可以创建多个条件变量（`Condition`对象） |

   2. 无锁 → 偏向锁 → 轻量级锁 → 重量级锁，JVM 自动优化，减少系统调用。

   

   ## 5、hashmap同步问题，扩容机制，怎么扩容的过程？哈希冲突哪有哪些解决？

   1. 开放地址法
      1. `ThreadLocal`内部的 `ThreadLocalMap`就使用了线性探测法。
   2. 再哈希法
      1. 准备多个不同的哈希函数。
   3. 建立公共溢出区
      1. 基本表和溢出表。所有冲突的元素都放入溢出表中。
   4. 链地址法
      1. **核心思想**：**将数组的每一个元素视为一个桶（bucket）或一个链表的头节点**。所有哈希到同一索引的键值对，都会被放入这个桶对应的链表中。

   

## 6、concurrentHashmap的工作原理，数据结构？

1. 通过 分段锁 + CAS + 无锁读 + 红黑树优化，实现 高并发读写不阻塞。

## 7、泛型是什么？怎么实现的？

1. 泛型是JAVA编译器类型安全机制，JVM本身并不支持泛型，而是通过类型擦除在编译阶段实现。运行时不支持泛型，通过编译器擦除+signature属性+桥接方法实现编译期类型安全和运行时兼容
2. List<String> 和 List<Integer> 运行时没有区别，都是List，区别只在编译期
3. 为什么不能new  T()？
   - T是未知类型，JVM无法分配内存
4. 泛型数组可以创建吗?\
   - 不可以，new T[10]编译失败，需要用object[]强制类型转换

## 8、怎么理解面向对象？简单聊聊封装、多态、继承

1. 面向对象是模拟现实世界的一种编程范式，通过封装、继承、多态实现高内聚低耦合。
2. 封装
   - 把属性私有化，方法控制访问，保护对象的一致性。
3. 继承
   - 子类复用父类代码，避免重复。
4. 多态
   - 统一接口，不同实现，运行时决定调用谁。



## 9、Integer和Int的区别？什么时候用Integer？new Integer(1)会不会从缓存中取？

1. int是基础类型，Integer是它的包装类，核心区别在于

   1. int有默认值，Integer可为null
   2. Integer在-128~127有缓存池

2. new Integer(1) 每次创建新对象，Integer.valueOf(1) 会命中缓存。

3. | 追问                                            | 回答要点                                          |
   | ----------------------------------------------- | ------------------------------------------------- |
   | `Integer a = 1; Integer b = 1;` 为什么 `a==b`？ | 自动装箱调用 `valueOf()`，命中缓存                |
   | `new Integer(1) == 1` 呢？                      | 拆箱后 `int` 比较，`true`                         |
   | 缓存范围能改吗？                                | 可以：`-XX:AutoBoxCacheMax=1000`                  |
   | 为什么缓存 `-128~127`？                         | 覆盖 byte 范围，日常使用频繁                      |
   | `Integer` 是线程安全的吗？                      | 不可变（`final` + `private final int`），线程安全 |



## 10、List为什么只能用Integer 不能用int的原因是什么？

List 只能用 Integer，不能用 int，是因为 Java 泛型只接受引用类型，int 是基本类型，编译器不认识。



## 11、介绍下NIO，NIO中channel的作用？

| 代号    | 全称                 | 线程模型                | 阻塞性   | 底层系统调用        | 典型场景       |
| ------- | -------------------- | ----------------------- | -------- | ------------------- | -------------- |
| **BIO** | **B**locking I/O     | 一连接一线程            | 阻塞     | `accept()`/`read()` | 连接数 < 1000  |
| **NIO** | **N**on-blocking I/O | **1 个线程管 N 个连接** | 非阻塞   | `epoll`             | 10w+ 长连接    |
| **AIO** | **A**synchronous I/O | 回调/ Future            | 完全异步 | `IOCP`              | Windows 高吞吐 |



## 12、什么是乐观锁、悲观锁？区别是什么呢？原理呢？

**乐观锁**：读不锁、写带版本，提交再比对，撞了重试。

**悲观锁**：读就上排他锁，事务没结束别人只能排队。

- **悲观锁**：数据库 **行锁（row lock）**，InnoDB 的 **next-key lock**
- **乐观锁**：**CAS 算法**（Compare And Swap），CPU 指令 cmpxchg





## 13、线程有几种实现方式？有什么状态？

```java
// 1. 继承 Thread  
Thread → new MyThread().start()

// 2. 实现 Runnable
public class Demo2 {
    public static void main(String[] args) {
        Thread t = new Thread(new MyRunnable());
        t.start();
        System.out.println("main 线程");
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("方式2：实现 Runnable");
    }
}

// 3. 带返回值 Callable
FutureTask<String> task = new FutureTask<>(() -> "OK");
new Thread(task).start();
System.out.println(task.get());

// 4. 线程池（推荐！）
Executors.newCachedThreadPool().execute(() -> System.out.println("Hi"));

//5.Lambda 简化
// Runnable
new Thread(() -> System.out.println("Lambda Runnable")).start();

// Callable
FutureTask<String> task = new FutureTask<>(
    () -> "Lambda Callable 返回值"
);
new Thread(task).start();
System.out.println(task.get());
```



```text
NEW
 │ start()
 ▼
RUNNABLE ←────────────────────────┐
 │   sleep(1000)      join(1000)  │
 ▼   ──────────────► TIMED_WAITING
 │   wait()  park()               │
 ▼   ──────────► WAITING          │
 │   synchronized(锁)             │
 ▼   ────────► BLOCKED ◄──────────┘
 │   run() 结束
 ▼
TERMINATED
```





## 14、finalize方法

> “finalize 是 Object 的方法，GC 前可能调用一次，用于资源清理。 但不确定、性能差、已废弃，实际开发一律不用！ 用 try-with-resources 或 Cleaner 替代。”

![image-20251106190317764](assets/java thread status-1762429072319-1.png)



## 15、说说抽象类和接口的区别。

- 抽象类可含普通属性/方法/构造器
- 接口（JDK8 前）只能含 public abstract 方法和 public static final 常量；
  - 普通方法
    - Java 8 前：不能有实现
    -  Java 8+：支持 default 方法（有实现）
    -  Java 9+：支持 private 方法
- 一个类只能继承一个抽象类，但可实现多个接口。
- 抽象类偏向“父子继承 + 代码共享”，接口偏向“行为契约 + 多实现”。
-  优先使用接口实现解耦，必要时用抽象类共享代码。

| 特性       | 抽象类              | 接口                             |
| ---------- | ------------------- | -------------------------------- |
| 继承       | `extends`（单继承） | `implements`（多实现）           |
| 方法实现   | 可有完整实现        | 默认无实现（Java 8+ 可 default） |
| 成员变量   | 可有实例变量        | 只能 public static final         |
| 构造器     | 有                  | 无                               |
| 访问修饰符 | 灵活                | 方法默认 public                  |
| 设计意图   | is-a 关系，代码复用 | can-do 能力，行为规范            |



## 16、synchonized和lock的区别？synchonized优化

> synchronized 是 JVM 内置锁，自动释放，基于 monitorenter/monitorexit；Lock 是显式锁（ReentrantLock），需手动 unlock，支持公平锁、条件等待、tryLock 和中断。



## 17、为什么线程多的时候要使用锁而不是CAS？

> 线程多时 CAS 竞争激烈，自旋重试耗 CPU + 缓存失效风暴，导致吞吐量雪崩；锁通过线程挂起（Park）让出 CPU，结合锁升级优化，整体性能更稳定。

## 18、谈一下异常，erorr和exception的区别，讲一下受检异常和非受检异常，说一下RuntimeException都有哪些，非受检异常有哪些？如何处理异常

- Error 是 JVM 级严重错误（如 OOM、StackOverflow），不可恢复不应捕获；Exception 是程序级异常，可处理，其中 RuntimeException 为非受检异常，其余为受检异常。

- 受检异常（Checked）继承自 Exception 但非 RuntimeException，编译强制处理；非受检（Unchecked）是 RuntimeException 及其子类，运行时抛出，编译不强制。

- 常见 RuntimeException：NullPointerException、ArrayIndexOutOfBoundsException、ClassCastException、IllegalArgumentException、UnsupportedOperationException 等；所有 RuntimeException 及其子类 + Error 均为非受检异常。

  - | 异常                              | 触发场景      | 生产防御                            |
    | --------------------------------- | ------------- | ----------------------------------- |
    | `NullPointerException`            | 空对象调用    | `Objects.requireNonNull` / Optional |
    | `IndexOutOfBoundsException`       | 数组/列表越界 | `list.get(i)` 前 `checkIndex`       |
    | `ClassCastException`              | 类型转换失败  | `instanceof` 判断                   |
    | `IllegalArgumentException`        | 参数非法      | 入参校验                            |
    | `IllegalStateException`           | 状态非法      | 状态机保护                          |
    | `UnsupportedOperationException`   | 接口未实现    | `Collections.unmodifiableList()`    |
    | `ConcurrentModificationException` | 迭代中修改    | 用 `CopyOnWriteArrayList`           |
    | `ArithmeticException`             | `/0`          | 除零检查                            |

- 受检异常

  - | 异常类                      | 包                  | 常见场景                          |
    | --------------------------- | ------------------- | --------------------------------- |
    | `IOException`               | `java.io`           | 文件读写、网络 IO                 |
    | `FileNotFoundException`     | `java.io`           | 文件未找到                        |
    | `EOFException`              | `java.io`           | 文件结束异常                      |
    | `SQLException`              | `java.sql`          | 数据库操作异常                    |
    | `ClassNotFoundException`    | `java.lang`         | `Class.forName()` 找不到类        |
    | `InterruptedException`      | `java.lang`         | `Thread.sleep()`, `wait()` 被打断 |
    | `ParseException`            | `java.text`         | 日期/数字解析失败                 |
    | `MalformedURLException`     | `java.net`          | URL 格式错误                      |
    | `NoSuchMethodException`     | `java.lang.reflect` | 反射找不到方法                    |
    | `InvocationTargetException` | `java.lang.reflect` | 反射调用目标异常                  |





## 19、什么是阻塞和非阻塞，什么是同步，异步？

> **阻塞/非阻塞** 关注**线程等待状态**：阻塞 → 调用后线程挂起；非阻塞 → 立即返回，需轮询。 
>
> **同步/异步** 关注**结果获取方式**：同步 → 调用者主动等结果；异步 → 回调/未来通知结果。

| 维度     | 阻塞（Blocking）          | 非阻塞（Non-blocking）            |
| -------- | ------------------------- | --------------------------------- |
| 系统调用 | read() 直到数据到位才返回 | read() 立即返回 EAGAIN            |
| 线程状态 | WAITING / BLOCKED（Park） | RUNNABLE（轮询或事件驱动）        |
| 典型 API | InputStream.read()        | SocketChannel.read() + O_NONBLOCK |

| 维度     | 同步（Synchronous） | 异步（Asynchronous）         |
| -------- | ------------------- | ---------------------------- |
| 结果获取 | 调用者**主动等待**  | 系统**回调通知**             |
| 典型实现 | Future.get() 阻塞等 | CompletableFuture + Callback |
| 内核支持 | 无需                | 需要 io_uring / AIO          |



## 20、什么是反射？反射的用途？为什么java需要反射，c++不需要。

> 反射是 运行时动态获取类结构（如字段、方法、构造器）并操作其对象的能力；用途包括 框架自动注入（Spring）、序列化（Jackson）、动态代理（AOP）、插件系统。
>
> Java 是 **解释 + 动态加载**，类在运行时才确定，反射是框架动态操作的基石；C++ 是 **静态编译**，模板在编译期展开，依赖注入/序列化靠模板元编程，无需运行时反射。

## 21、有哪些方式可以创建一个对象？

Java 创建对象有 **5 种主流方式**：

1. new 关键字
2. Class.forName().newInstance() / 反射
3. Constructor.newInstance()
4. clone()
5. 反序列化（ObjectInputStream.readObject()） 附加：new 变种（数组、String 常量池）、工厂/Builder、动态代理、虚拟线程（JDK21）。

## 22、多线程和协程的优缺点

> 多线程：内核级，抢占式调度，适合 CPU 密集型，真并行，但上下文切换贵（1-10μs）、内存大（1MB 栈）。 协程：用户态，轻量协作式，适合 I/O 密集型，单线程高并发（10w+），切换快（<1μs）、栈小（几KB），但不能利用多核。

