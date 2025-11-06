import java.util.concurrent.ThreadPoolExecutor;

public class Runnable1 implements Runnable {
    @Override
    public void run() {
        System.out.println("线程 " + Thread.currentThread().getName() + " 正在运行");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("线程 " + Thread.currentThread().getName() + " 被中断");
        }
    }

    public static void main(String[] args) {
        Runnable1 runnable = new Runnable1();
        Thread thread1 = new Thread(runnable, "Runnable-Thread-1");
        Thread thread2 = new Thread(runnable, "Runnable-Thread-2");
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束");
    }
}
