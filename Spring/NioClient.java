package Spring;

// NioClient.java
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1", 8081));
        sc.configureBlocking(false);

        System.out.println("NIO 客户端已连接，输入文字后回车发送（quit 退出）");

        ByteBuffer writeBuf = ByteBuffer.allocate(1024);
        ByteBuffer readBuf = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if ("quit".equalsIgnoreCase(line))
                break;

            // 发送
            writeBuf.clear();
            writeBuf.put((line + "\n").getBytes(StandardCharsets.UTF_8));
            writeBuf.flip();
            while (writeBuf.hasRemaining())
                sc.write(writeBuf);

            // 接收回显
            readBuf.clear();
            int len;
            while ((len = sc.read(readBuf)) == 0) {
                /* 空转等待 */ }
            if (len < 0)
                break;

            readBuf.flip();
            byte[] bytes = new byte[readBuf.limit()];
            readBuf.get(bytes);
            System.out.println("回显: " + new String(bytes, StandardCharsets.UTF_8).trim());
        }

        sc.close();
        System.out.println("客户端关闭");
    }
}
