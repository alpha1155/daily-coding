package Spring;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int CAPACITY = 10;
    private final Object lock = new Object(); // 监视器对象

    // 生产者
    public void produce(int item) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() == CAPACITY) { // 必须用 while，防止虚假唤醒！
                System.out.println("队列满，生产者等待");
                lock.wait(); // 释放锁，进入等待
            }
            queue.offer(item);
            System.out.println("生产: " + item + "，队列大小: " + queue.size());
            lock.notifyAll(); // 唤醒消费者（用 notifyAll 更安全）
        }
    }

    // 消费者
    public int consume() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                System.out.println("队列空，消费者等待");
                lock.wait();
            }
            int item = queue.poll();
            System.out.println("消费: " + item + "，队列大小: " + queue.size());
            lock.notifyAll(); // 唤醒生产者
            return item;
        }
    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();

        // 生产者线程
        new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                try {
                    pc.produce(i);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        // 消费者线程
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    pc.consume();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
