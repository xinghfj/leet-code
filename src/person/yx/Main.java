package person.yx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Main {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\42596\\Desktop\\user.xlsx"));
        FileOutputStream outputStream = new FileOutputStream(new File("C:\\Users\\42596\\Desktop\\copy.xlsx"));
        FileChannel inChannel = inputStream.getChannel();
        FileChannel outChannel = outputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        int count = 0;
        while (true) {
            int index = inChannel.read(buffer);
            if (index == -1) {
                break;
            }
            count++;
            buffer.flip();
            outChannel.write(buffer);
            buffer.compact();
        }
        inChannel.close();
        outChannel.close();
        inputStream.close();
        outputStream.close();
        System.out.println("count: " + count);
    }
}
