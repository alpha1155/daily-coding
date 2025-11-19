public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("线程 " + getName() + " 正在运行");
        try {
            Thread.sleep(1000); // 模拟耗时操作
        } catch (InterruptedException e) {
            System.out.println("线程 " + getName() + " 被中断");
        }
    }

    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();
        thread1.setName("Thread-1");
        thread2.setName("Thread-2");
        thread1.start(); // 启动线程
        thread2.start();
        try {
            thread1.join(); // 等待 thread1 完成
            thread2.join(); // 等待 thread2 完成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束");
    }
}