package Spring;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyCallable implements Callable<String> {
    private String name;

    public MyCallable(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        System.out.println("线程 " + name + " 正在运行");
        try {
            Thread.sleep(1000); // 模拟耗时操作
        } catch (InterruptedException e) {
            System.out.println("线程 " + name + " 被中断");
        }
        return "任务 " + name + " 完成";
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        MyCallable callable1 = new MyCallable("Callable-1");
        MyCallable callable2 = new MyCallable("Callable-2");
        Future<String> future1 = executor.submit(callable1);
        Future<String> future2 = executor.submit(callable2);
        try {
            System.out.println(future1.get()); // 获取任务1的结果
            System.out.println(future2.get()); // 获取任务2的结果
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // 关闭线程池
        }
        System.out.println("主线程结束");

        Runnable task = () -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程 " + Thread.currentThread().getName() + " 正在运行");
            } catch (InterruptedException e) {
                System.out.println("线程 " + Thread.currentThread().getName() + " 被中断");
            }
        };

        Thread thread1 = new Thread(task, "Lambda-Thread-1");
        Thread thread2 = new Thread(task, "Lambda-Thread-2");
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束");

        // executorService
        ExecutorService executor1 = Executors.newFixedThreadPool(2); // 固定 2 个线程的线程池

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executor1.submit(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(
                            "任务 " + taskId + " 由线程 " + Thread.currentThread().getName() + " 执行");
                } catch (InterruptedException e) {
                    System.out.println("任务 " + taskId + " 被中断");
                }
            });
        }

        executor1.shutdown(); // 任务提交完成后关闭线程池
        System.out.println("wait");
        try {
            if (!executor1.awaitTermination(5, TimeUnit.SECONDS)) {
                executor1.shutdownNow(); // 超时强制关闭
            }
        } catch (InterruptedException e) {
            executor1.shutdownNow();
        }
        System.out.println("主线程结束");


        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return "异步任务 2 完成";
            } catch (InterruptedException e) {
                return "异步任务 2 被中断";
            }
        }).thenAccept(result -> System.out.println(result));
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return "异步任务 1 完成";
            } catch (InterruptedException e) {
                return "异步任务 1 被中断";
            }
        }).thenAccept(result -> System.out.println(result));

        // 主线程等待异步任务完成
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束");

        Counter counter = new Counter();
        Runnable task1 = () -> {
            for (int i = 0; i < 5; i++) {
                counter.incrementWithSynchronized(); // 或使用 incrementWithLock()
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread11 = new Thread(task1, "Thread-1");
        Thread thread22 = new Thread(task1, "Thread-2");
        thread11.start();
        thread22.start();
        try {
            thread11.join();
            thread22.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束");
    }

    private static class Counter {
        private int count = 0;
        private final Lock lock = new ReentrantLock();

        public synchronized void incrementWithSynchronized() {
            count++;
            System.out.println(Thread.currentThread().getName() + "增加计数");
        }

        // 使用 ReentrantLock 同步
        public void incrementWithLock() {
            lock.lock();
            try {
                count++;
                System.out.println(Thread.currentThread().getName() + " 增加计数: " + count);
            } finally {
                lock.unlock();
            }
        }
    }
}

