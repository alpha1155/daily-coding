package Spring;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerWithCondition {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int CAPACITY = 10;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition(); // 队列不满条件
    private final Condition notEmpty = lock.newCondition(); // 队列不空条件

    // 生产者
    public void produce(int item) throws InterruptedException {
        lock.lock();
        try {
            // 判断条件：队列满 → 等待
            while (queue.size() == CAPACITY) {
                System.out.println("队列满，生产者等待");
                notFull.await(); // 释放锁，等待 notFull 条件成立
            }

            queue.offer(item);
            System.out.println("生产: " + item + "，队列大小: " + queue.size());

            notEmpty.signalAll(); // 唤醒等待“队列不空”的消费者
        } finally {
            lock.unlock();
        }
    }

    // 消费者
    public int consume() throws InterruptedException {
        lock.lock();
        try {
            // 判断条件：队列空 → 等待
            while (queue.isEmpty()) {
                System.out.println("队列空，消费者等待");
                notEmpty.await(); // 等待 notEmpty 条件成立
            }

            int item = queue.poll();
            System.out.println("消费: " + item + "，队列大小: " + queue.size());

            notFull.signalAll(); // 唤醒等待“队列不满”的生产者
            return item;
        } finally {
            lock.unlock();
        }
    }
}
