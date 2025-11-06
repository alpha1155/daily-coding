package Spring;

import java.util.*;
import java.util.concurrent.locks.*;

public class MyBlockingQueue<E> {
    private final Queue<E> queue;
    private final int capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    public MyBlockingQueue(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    public void put(E e) throws InterruptedException {
        if (e == null)
            throw new NullPointerException();
        final Lock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (queue.size() == capacity) {
                System.out.println(
                        Thread.currentThread().getName() + "Full queue, waiting to put...");
                notFull.await();
            }
            queue.offer(e);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        final Lock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (queue.isEmpty()) {
                System.out.println(
                        Thread.currentThread().getName() + "Empty queue,waiting to take...");
            }
            E e = queue.poll();
            notFull.signal();
            return e;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        final Lock lock = this.lock;
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}
