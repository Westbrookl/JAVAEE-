import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

public class NIOClient {

   
    private InetSocketAddress SERVER;

 
    private ByteBuffer rBuffer = ByteBuffer.allocate(1024);

   
    private ByteBuffer sBuffer = ByteBuffer.allocate(1024);

    private static Selector selector;

  
    private Charset charset = Charset.forName("UTF-8");

    public NIOClient(int port) {
        SERVER = new InetSocketAddress("localhost", port);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 初始化客户端
    private void init() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(SERVER);
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> handle(selectionKey));
            selectionKeys.clear(); 
        }
    }

   
    private void handle(SelectionKey selectionKey) {
        try {
           
            if (selectionKey.isConnectable()) {
                SocketChannel client = (SocketChannel) selectionKey.channel();
                if (client.isConnectionPending()) {
                    client.finishConnect();
                    System.out.println("success");
                    
                    new Thread() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    sBuffer.clear();
                                    Scanner scanner = new Scanner(System.in);
                                    String sendText = scanner.nextLine();
                                    System.out.println(sendText);
                                    sBuffer.put(charset.encode(sendText));
                                    sBuffer.flip();
                                    client.write(sBuffer);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }.start();
                }
              
                client.register(selector, SelectionKey.OP_READ);
            }
            
            else if (selectionKey.isReadable()) {
                SocketChannel client = (SocketChannel) selectionKey.channel();
                rBuffer.clear();
                int count = client.read(rBuffer);
                if (count > 0) {
                    String receiveText = new String(rBuffer.array(), 0, count);
                    System.out.println(receiveText);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new NIOClient(7777);
    }
}
