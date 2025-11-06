package Spring;



// AIO.java
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AIO {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 1. 创建异步服务端通道
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
        server.bind(new InetSocketAddress(8082));
        System.out.println("AIO 启动 8082");

        // 2. 开始异步 accept（回调模式）回调处理器
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel sc, Void att) {
                // 3. 继续接受下一个连接（递归）
                server.accept(null, this);

                ByteBuffer buf = ByteBuffer.allocate(1024);
                // 4. 异步读（读完自动回调）
                sc.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer len, ByteBuffer buffer) {
                        if (len > 0) {
                            buffer.flip(); // 读模式
                            sc.write(buffer); // 回显
                            buffer.clear(); // 准备下次读
                            sc.read(buffer, buffer, this); // 继续读
                        } else if (len < 0) {
                            close(sc);
                        }
                    }

                    @Override
                    public void failed(Throwable e, ByteBuffer b) {
                        close(sc);
                    }
                });
            }

            @Override
            public void failed(Throwable e, Void v) {
                e.printStackTrace();
            }
        });

        // 5. 主线程永不退出
        new CountDownLatch(1).await();
    }

    private static void close(AsynchronousSocketChannel sc) {
        try {
            sc.close();
        } catch (IOException ignored) {
        }
    }
}
