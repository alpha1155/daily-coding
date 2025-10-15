### Java 基础

面向对象编程的三大特性

- 封装
- 继承
- 多态

1. 请简述 Java 中的四大访问修饰符及其作用范围。

   - public
     - 当前类、同一包、不同包子类、其他包
   - protected
     - 当前类、同一包、不同包子类
   - default
     - 当前类、同一包
   - private
     - 当前类
2. 谈谈你对 Java 多态的理解，它是如何实现的，在实际项目中有怎样的应用？

   - 允许**同一个接口使用不同的实例执行不同的操作**。
   - ## 多态的实现机制


     1. ### 方法重写（Override）
     2. ### 向上转型（Upcasting）

        - ```java
          public class AreaCalculator {
              // 多态方法：可以接受任何Shape子类
              public static void printArea(Shape shape) {
                  System.out.println("面积: " + shape.getArea());
              }

              public static void main(String[] args) {
                  Shape circle = new Circle(5.0);    // 向上转型
                  Shape rectangle = new Rectangle(4.0, 6.0);

                  printArea(circle);     // 输出: 面积: 78.53981633974483
                  printArea(rectangle);  // 输出: 面积: 24.0
              }
          }
          ```
     3. ### 动态绑定（运行时多态）

        - Java 在运行时根据对象的实际类型来确定调用哪个方法，而不是根据引用类型。
   - 应用

     1. 支付系统
     2. 日志
     3. ### 数据访问层（DAO模式）
   - ## 多态的优势


     1. ### 代码可扩展性
     2. ### 代码可维护性
     3. ### 松耦合设计

        - ```java
          // 高层模块不依赖于低层模块的具体实现
          class ReportGenerator {
              private DataExporter exporter;

              public ReportGenerator(DataExporter exporter) {
                  this.exporter = exporter; // 依赖抽象，而不是具体实现
              }

              public void generateReport() {
                  // 生成报告
                  exporter.export(data);
              }
          }

          interface DataExporter {
              void export(Object data);
          }

          class PdfExporter implements DataExporter { /* 实现 */ }
          class ExcelExporter implements DataExporter { /* 实现 */ }
          class CsvExporter implements DataExporter { /* 实现 */ }
          ```
3. 解释一下 Java 中的垃圾回收机制，常见的垃圾回收算法有哪些？

   Java 的垃圾回收（Garbage Collection, GC）是**自动内存管理机制**，它负责回收不再使用的对象所占用的内存空间，防止内存泄漏。

   - 垃圾对象判定算法

     1. 引用计数法（Reference Counting）

     ```
     // 简单但Java不采用的方法
     class ReferenceCounting {
         private int count = 0;

         public void addReference() { count++; }
         public void removeReference() { 
             count--; 
             if (count == 0) {
                 // 可以回收
             }
         }
     }
     ```

     **缺点**：无法解决循环引用问题

     2. 可达性分析算法（Reachability Analysis）✅ **Java采用**

     ```java
     // GC Roots 包括：
     // 1. 虚拟机栈中的局部变量
     // 2. 方法区中的静态变量
     // 3. 方法区中的常量
     // 4. 本地方法栈中的变量

     public class ReachabilityExample {
         private static Object staticObj;  // GC Root
         private final Object finalObj;    // GC Root

         public void method() {
             Object localVar = new Object();  // GC Root
             // 方法执行期间localVar是根对象
         }
     }
     ```
   - ### 标记-清除算法（Mark-Sweep）
   - ### 复制算法（Copying）
   - ### 标记-整理算法（Mark-Compact）
   - ### 分代收集算法（Generational Collection）✅ **Java主要采用**
   - # Java 垃圾回收机制详解

     ## 一、垃圾回收概述

     Java 的垃圾回收（Garbage Collection, GC）是**自动内存管理机制**，它负责回收不再使用的对象所占用的内存空间，防止内存泄漏。

     ### 核心概念

     java

     Download

     Copy

     Run


     ```
     public class GCDemo {
         public void createObjects() {
             // obj1 和 obj2 是局部变量，在栈上分配
             Object obj1 = new Object();  // 对象在堆上分配
             Object obj2 = new Object();

             obj1 = null;  // obj1 不再引用对象，成为垃圾
             // 对象可以被GC回收
         }
     }
     ```

     ## 二、垃圾对象判定算法

     ### 1. 引用计数法（Reference Counting）

     java

     Download

     Copy

     Run

     ```
     // 简单但Java不采用的方法
     class ReferenceCounting {
         private int count = 0;

         public void addReference() { count++; }
         public void removeReference() { 
             count--; 
             if (count == 0) {
                 // 可以回收
             }
         }
     }
     ```

     **缺点**：无法解决循环引用问题

     ### 2. 可达性分析算法（Reachability Analysis）✅ **Java采用**

     java

     Download

     Copy

     Run

     ```
     // GC Roots 包括：
     // 1. 虚拟机栈中的局部变量
     // 2. 方法区中的静态变量
     // 3. 方法区中的常量
     // 4. 本地方法栈中的变量

     public class ReachabilityExample {
         private static Object staticObj;  // GC Root
         private final Object finalObj;    // GC Root

         public void method() {
             Object localVar = new Object();  // GC Root
             // 方法执行期间localVar是根对象
         }
     }
     ```

     ## 三、常见的垃圾回收算法

     ### 1. 标记-清除算法（Mark-Sweep）

     java

     Download

     Copy

     Run

     ```
     // 分为两个阶段：
     // 1. 标记：从GC Roots开始标记所有可达对象
     // 2. 清除：回收未标记的对象

     // 伪代码实现
     class MarkSweepGC {
         void collect() {
             markPhase();    // 标记存活对象
             sweepPhase();   // 清理未标记对象
         }

         void markPhase() {
             for (Object root : gcRoots) {
                 markRecursively(root);
             }
         }

         void sweepPhase() {
             for (Object obj : heapObjects) {
                 if (!obj.isMarked()) {
                     freeMemory(obj);
                 }
             }
         }
     }
     ```

     **优点**：简单直接

     **缺点**：产生内存碎片

     ### 2. 复制算法（Copying）

     java

     Download

     Copy

     Run

     ```
     // 将内存分为两块，只使用其中一块
     // GC时将存活对象复制到另一块，然后清空当前块

     class CopyingGC {
         private MemorySpace fromSpace;
         private MemorySpace toSpace;

         void collect() {
             // 交换空间
             swapSpaces();

             // 复制存活对象
             for (Object root : gcRoots) {
                 copyRecursively(root);
             }

             // 清空原空间
             fromSpace.clear();
         }
     }
     ```

     **优点**：无内存碎片

     **缺点**：内存利用率只有50%

     ### 3. 标记-整理算法（Mark-Compact）

     java

     Download

     Copy

     Run

     ```
     // 结合标记-清除和复制算法的优点
     // 1. 标记存活对象
     // 2. 将存活对象向一端移动
     // 3. 清理边界外的内存

     class MarkCompactGC {
         void collect() {
             markPhase();     // 标记
             compactPhase();  // 整理
         }

         void compactPhase() {
             int newAddress = 0;
             for (Object obj : heapObjects) {
                 if (obj.isMarked()) {
                     moveObject(obj, newAddress);
                     newAddress += obj.size();
                 }
             }
             // 清理剩余空间
         }
     }
     ```

     **优点**：无内存碎片，内存利用率高

     **缺点**：移动对象开销大

     ### 4. 分代收集算法（Generational Collection）✅ **Java主要采用**

     java

     Download

     Copy

     Run

     ```
     // 根据对象生命周期将堆分为不同代
     class GenerationalGC {
         // 年轻代（Young Generation）
         private MemorySpace eden;       // 新对象分配区
         private MemorySpace survivor0;  // 存活对象区
         private MemorySpace survivor1;  // 存活对象区

         // 老年代（Old Generation）
         private MemorySpace tenured;    // 长期存活对象

         // 永久代/元空间（PermGen/Metaspace）
         private MemorySpace metaspace;  // 类元数据
     }
     ```

     ## 四、Java中的垃圾收集器

     ### 1. 串行收集器（Serial GC）

     ```java
     // 单线程收集器，适合客户端应用
     // 启动参数：-XX:+UseSerialGC
     public class SerialGCExample {
         // 适合小内存、单CPU环境
     }
     ```

     ### 2. 并行收集器（Parallel GC / Throughput Collector）

     ```java
     // 多线程收集器，注重吞吐量
     // 启动参数：-XX:+UseParallelGC
     public class ParallelGCExample {
         // 适合后台处理、计算密集型应用
     }
     ```

     ### 3. CMS收集器（Concurrent Mark-Sweep）

     ```java
     // 并发低停顿收集器
     // 启动参数：-XX:+UseConcMarkSweepGC
     public class CMSGCExample {
         // 过程：初始标记 -> 并发标记 -> 重新标记 -> 并发清除
         // 适合响应时间敏感的应用
     }
     ```

     ### 4. G1收集器（Garbage-First）✅ **推荐使用**

     ```java
     // 面向服务端的收集器，兼顾吞吐量和停顿时间
     // 启动参数：-XX:+UseG1GC
     public class G1GCExample {
         // 将堆划分为多个Region
         // 优先回收垃圾最多的Region
     }
     ```

     ### 5. ZGC和Shenandoah（低延迟收集器）

     ```java
     // 超低停顿时间的收集器
     // 启动参数：-XX:+UseZGC 或 -XX:+UseShenandoahGC
     public class LowLatencyGC {
         // 适合大内存、要求低延迟的应用
     }
     ```
4. 说说 Java 中集合框架（如 List、Set、Map）的主要接口和实现类，以及它们的区别和适用场景。

   ```
   // Collection 接口层次
   Collection
   ├── List        // 有序、可重复
   ├── Set         // 无序、不可重复
   └── Queue       // 队列

   // Map 接口层次（独立于Collection）
   Map
   ├── HashMap
   ├── TreeMap
   └── LinkedHashMap
   ```

   1. List

      1. ArrayList
         1. 特点：查询快O(1)，增删慢O(n)* *// 适用场景：读多写少，需要随机访问*
      2. LinkedList
         1. 特点：增删快O(1)，查询慢O(n)* *// 适用场景：频繁在头尾操作，实现队列/栈*
      3. Vector
         1. 线程安全的ArrayList，但性能差
      4. CopyOnWriteArrayList
         1. *// 线程安全，写时复制*
         2. *// 适用场景：读多写少的并发场景*
   2. Set

      1. HashSet
      2. LinkedHashSet
      3. #### TreeSet
   3. Map

      1. HashMap
      2. LinkedHashMap

         1. *// 遍历顺序：Banana, Apple* *// 适用场景：需要保持插入或访问顺序*
      3. TreeMap

         1. ```java
            // 基于红黑树，键有序
            Map<String, Integer> treeMap = new TreeMap<>();
            treeMap.put("Banana", 2);
            treeMap.put("Apple", 1);
            // 遍历顺序：Apple, Banana

            // 特点：键有序，操作O(log n)
            // 适用场景：需要有序的键值对
            ```
      4. #### ConcurrentHashMap


         1. ```
            // 线程安全的HashMap
            Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
            // 分段锁机制，高并发性能好
            ```
         2. 在Java 7及之前版本中，`ConcurrentHashMap`的实现方式是：

            1. **将整个哈希表分成多个段(Segment)**，默认16个段
            2. **每个段独立加锁**，相当于一个小的HashMap
            3. **不同段可以并发操作**，只有相同段的操作才会竞争锁
         3. 在Java 8中，`ConcurrentHashMap`进行了重大改进：

            1. **废弃分段锁**，改用**CAS + synchronized**实现
            2. **锁粒度更细**：只锁定单个哈希桶(bucket)
            3. **红黑树优化**：当链表长度超过8时转为红黑树
         4. ## 适用场景


            1. **高并发读写**：如缓存系统
            2. **频繁更新**：实时统计计数
            3. **大规模数据**：需要线程安全的键值存储
5. 什么是 Java 的异常处理机制，`try-catch-finally`是如何工作的，你遇到过哪些常见的异常，如何处理？

   ```java
   public void readFile(String filename) {
       FileReader reader = null;
       try {
           reader = new FileReader(filename);
           // 读取文件操作
           int data = reader.read();

       } catch (FileNotFoundException e) {
           // 处理文件不存在异常
           System.err.println("文件未找到: " + e.getMessage());
           throw new RuntimeException("文件读取失败", e); // 异常转换

       } catch (IOException e) {
           // 处理IO异常
           System.err.println("IO错误: " + e.getMessage());

       } finally {
           // 无论是否发生异常都会执行
           if (reader != null) {
               try {
                   reader.close(); // 确保资源关闭
               } catch (IOException e) {
                   System.err.println("关闭文件失败: " + e.getMessage());
               }
           }
       }
   }
   ```

   - #### 1. NullPointerException


     ```
     // 原因：调用null对象的方法或访问字段
     String str = null;
     // System.out.println(str.length()); // ❌ 抛出NPE

     // 解决方案：
     // 1. 防御性检查
     if (str != null) {
         System.out.println(str.length());
     }

     // 2. 使用Optional（Java 8+）
     Optional<String> optionalStr = Optional.ofNullable(str);
     System.out.println(optionalStr.orElse("").length());

     // 3. 使用Objects.requireNonNull
     public void processString(String input) {
         Objects.requireNonNull(input, "输入不能为null");
         // 安全使用input
     }
     ```

     #### 2. IndexOutOfBoundsException

     ```
     // 原因：数组或集合索引越界
     List<String> list = Arrays.asList("A", "B");
     // String element = list.get(2); // ❌ 索引越界

     // 解决方案：
     // 1. 检查边界
     int index = 2;
     if (index >= 0 && index < list.size()) {
         String element = list.get(index);
     }

     // 2. 使用安全的方法
     String element = list.size() > index ? list.get(index) : null;
     ```

     #### 3. IllegalArgumentException

     ```
     // 原因：传入非法参数
     public void setAge(int age) {
         if (age < 0 || age > 150) {
             throw new IllegalArgumentException("年龄必须在0-150之间");
         }
         this.age = age;
     }

     // 使用验证框架如Jakarta Validation
     public class User {
         @Min(0) @Max(150)
         private int age;
     }
     ```

     #### 4. IOException

     ```
     // 原因：IO操作失败
     public void copyFile(String source, String target) {
         try (InputStream in = new FileInputStream(source);
              OutputStream out = new FileOutputStream(target)) {

             byte[] buffer = new byte[1024];
             int bytesRead;
             while ((bytesRead = in.read(buffer)) != -1) {
                 out.write(buffer, 0, bytesRead);
             }

         } catch (IOException e) {
             // 记录日志并抛出业务异常
             log.error("文件复制失败: {} -> {}", source, target, e);
             throw new BusinessException("文件操作失败", e);
         }
     }
     ```

     #### 5. ConcurrentModificationException

     ```
     // 原因：在迭代时修改集合
     List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));

     // ❌ 错误做法：在foreach循环中修改
     for (String item : list) {
         if ("B".equals(item)) {
             list.remove(item); // 抛出异常
         }
     }

     // ✅ 正确做法：使用迭代器
     Iterator<String> iterator = list.iterator();
     while (iterator.hasNext()) {
         String item = iterator.next();
         if ("B".equals(item)) {
             iterator.remove(); // 安全删除
         }
     }

     // ✅ 使用Java 8+ removeIf
     list.removeIf(item -> "B".equals(item));
     ```

### Java 后端开发技术

1. 请介绍一下 Spring 框架的核心特性，如依赖注入（DI）和面向切面编程（AOP），你在项目中是如何使用 Spring 的？

   依赖注入（DI）

   ```java
   @Component
   public class OrderService {

       // 1. 构造函数注入（推荐）
       private final PaymentService paymentService;
       private final InventoryService inventoryService;

       @Autowired // Spring 4.3+ 可省略
       public OrderService(PaymentService paymentService, 
                          InventoryService inventoryService) {
           this.paymentService = paymentService;
           this.inventoryService = inventoryService;
       }

       // 2. Setter注入
       private EmailService emailService;

       @Autowired
       public void setEmailService(EmailService emailService) {
           this.emailService = emailService;
       }

       // 3. 字段注入（不推荐）
       @Autowired
       private LogService logService;
   }
   // XML配置（传统方式）
   <bean id="userService" class="com.example.UserService">
       <constructor-arg ref="userRepository"/>
   </bean>

   // Java配置（现代方式）
   @Configuration
   public class AppConfig {
       @Bean
       public UserService userService(UserRepository userRepository) {
           return new UserService(userRepository);
       }
   }

   // 注解配置（最常用）
   @Service
   public class UserService {
       // 自动注入
   }
   ```

   **1. 核心思想：**

   AOP 是一种编程范式，旨在将**横切关注点** 与**核心业务逻辑**分离。

   - •**横切关注点**：指那些遍布在应用程序多个模块中的功能，例如日志记录、安全检查、事务管理、性能监控等。
   - •**核心业务逻辑**：指实现具体业务需求的代码，例如用户注册、订单处理。

   **2. 解决的问题：**

   在没有 AOP 之前，这些横切关注点的代码（如日志语句）会混杂在业务代码中，导致：

   - •**代码冗余**：相同的日志代码出现在每个方法里。
   - •**代码混乱**：业务逻辑被非核心功能代码淹没。
   - •**维护困难**：修改日志格式需要改动所有相关类。
2. 谈谈 Spring Boot 与 Spring 的区别和联系，使用 Spring Boot 有哪些优势，你在项目中是如何进行 Spring Boot 配置的？
3. 解释一下 Java 中的 Servlet 和 JSP，它们在 Web 开发中的作用和区别是什么？
4. 你有过使用 Java 进行数据库开发的经验吗，谈谈 JDBC 的使用，以及如何使用 ORM 框架（如 Hibernate 或 MyBatis）进行数据库操作，对比一下它们的优缺点。
5. 如何在 Java 中实现一个简单的 RESTful Web 服务，你使用过哪些相关的框架或工具（如 Spring MVC、Jersey 等）？

### 性能与并发

1. 谈谈你对 Java 并发的理解，`Thread`类和 `Runnable`接口有什么区别，如何创建和管理线程？
2. 解释一下 Java 中的锁机制，如 `synchronized`关键字和 `Lock`接口，它们在使用上有什么区别和适用场景？
3. 什么是 Java 中的线程池，常见的线程池实现类有哪些，如何合理配置线程池参数？
4. 当你的 Java 后端服务面临高并发场景时，你会采取哪些策略来优化性能和保证系统的稳定性？

### AI 相关（结合 Java）

1. 你了解哪些 Java 与 AI 结合的库或框架，如 Deeplearning4j，谈谈你的了解和使用经验。
2. 在 Java 后端开发中，如果要将 AI 模型的预测结果集成到系统中，你会考虑哪些步骤和技术实现？
3. 假设要开发一个基于 Java 的后端服务，为前端提供 AI 图像识别结果的接口，你会如何设计和实现？

### 项目经验与实践

1. 请详细介绍一个你使用 Java 进行后端开发的项目，包括项目的背景、目标、你承担的角色和主要工作内容，以及在项目中遇到的技术难题和解决方案。
2. 在项目开发过程中，你是如何进行代码管理和版本控制的，使用过哪些工具（如 Git），谈谈你的代码管理流程和规范。
3. 当你与团队成员合作开发一个大型 Java 项目时，如何确保代码的可维护性和可扩展性，你有哪些经验和实践？

### 学习与发展

1. Java 技术生态不断发展，你是如何保持对新技术的学习和关注的，最近学习了哪些新的 Java 相关技术或框架？
2. 对于未来的职业发展，你希望在 Java 开发领域有怎样的提升和发展，如何将 Java 开发技能与 AI 技术更好地结合？
