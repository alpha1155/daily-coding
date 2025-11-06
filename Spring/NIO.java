package Spring;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIO {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(8081));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("NIO 启动 8081");

        while (true) {
            selector.select();
            for (SelectionKey key : selector.selectedKeys()) {
                if (key.isAcceptable()) {
                    // 处理连接事件
                    System.out.println("接受到连接请求");
                    SocketChannel clientChannel = serverChannel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    // 处理读事件
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    System.out.println("get client: " + clientChannel.getRemoteAddress());
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int bytesRead = clientChannel.read(buffer);
                    if (bytesRead == -1) {
                        clientChannel.close();
                    } else {
                        // buffer.flip(); —— 读写切换神器
                        buffer.flip();
                        // limit() 现在是 实际收到字节数（56）
                        byte[] data = new byte[buffer.limit()];
                        // 从 position=0 开始，把 limit 个字节全部拷贝到 data[]
                        // 读取后 position 自动变成 limit（56）
                        buffer.get(data);
                        System.out.println("Received: " + new String(data));
                        // 回写数据
                        buffer.clear();
                        buffer.put("Hello from NIO server".getBytes());
                        buffer.flip();
                        clientChannel.write(buffer);
                    }
                }
            }
            selector.selectedKeys().clear(); // 必须清除，否则下次 select 会重复处理
        }
    }
}
