package Spring;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class LockDemo {
    private int count = 0;
    private final Object obj = new Object(); // 用于 synchronized

    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final ReadLock readLock = rwLock.readLock();
    private final WriteLock writeLock = rwLock.writeLock();

    // 1. synchronized（最简单）
    public synchronized void syncIncrement() {
        count++;
    }

    // 等价于 synchronized 代码块
    public void syncBlockIncrement() {
        synchronized (obj) {
            count++;
        }
    }

    // 2. ReentrantLock（推荐生产使用）
    public void reentrantIncrement() {
        reentrantLock.lock(); // 获取锁
        try {
            count++;
        } finally {
            reentrantLock.unlock(); // 必须放在 finally！
        }
    }

    // 支持中断 + 超时
    public boolean tryReentrantIncrement(long timeoutMillis) throws InterruptedException {
        if (reentrantLock.tryLock(timeoutMillis, TimeUnit.MILLISECONDS)) {
            try {
                count++;
                return true;
            } finally {
                reentrantLock.unlock();
            }
        }
        return false;
    }

    // 支持 Condition（类似 wait/notify，但更灵活）
    public void conditionDemo() throws InterruptedException {
        Condition condition = reentrantLock.newCondition();
        reentrantLock.lock();
        try {
            condition.await(); // 等待
            condition.signal(); // 唤醒
        } finally {
            reentrantLock.unlock();
        }
    }

    // 3. ReentrantReadWriteLock（读写分离，读并发最高）
    public int readCount() {
        readLock.lock(); // 多个线程可同时读
        try {
            return count;
        } finally {
            readLock.unlock();
        }
    }

    public void writeCount() {
        writeLock.lock(); // 写锁独占
        try {
            count++;
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo demo = new LockDemo();

        // 测试读写锁并发优势
        for (int i = 0; i < 10; i++) {
            new Thread(demo::readCount).start(); // 10 个读线程同时执行
        }
        new Thread(demo::writeCount).start(); // 写线程会阻塞直到所有读完成
    }
}
