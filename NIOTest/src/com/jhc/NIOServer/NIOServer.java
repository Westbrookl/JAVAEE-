//package com.jhc.NIOServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NIOServer {
	private int port = 8888;
	private Charset charset = Charset.forName("UTF-8");
	
	private ByteBuffer rBuffer = ByteBuffer.allocate(1024);
	
	private ByteBuffer sBuffer = ByteBuffer.allocate(1024);
	
	private Map<String,SocketChannel> clientMap = new HashMap();
	
	private static Selector selector;
	
	public NIOServer(int port){
		this.port = port;
		try{
			init();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	private void init() throws IOException{
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocket.bind(new InetSocketAddress(port));
		selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("服务器启动，端口为 "+ port);
	}
	
	public void listen(){
		while(true){
			try{
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				for(SelectionKey sk:selectionKeys){
					handle(sk);
				}
				selectionKeys.clear();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	public void handle(SelectionKey sk) throws IOException{
		try{
			if(sk.isAcceptable()){
				ServerSocketChannel server = (ServerSocketChannel)sk.channel();
				SocketChannel client = server.accept();
				client.configureBlocking(false);
				client.register(selector,SelectionKey.OP_READ);
				System.out.println(client.toString()+" 注册成功");
				clientMap.put(getClientName(client), client);
				
			}else if(sk.isReadable()){
				SocketChannel client = (SocketChannel)sk.channel();
				rBuffer.clear();
				int bytes = client.read(rBuffer);
				if(bytes>0){
					rBuffer.flip();
					String receiveText = String.valueOf(charset.decode(rBuffer));
					System.out.println(client.toString()+": "+ receiveText);
					dispatch(client,receiveText);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	private String getClientName(SocketChannel sc){
		Socket socket = sc.socket();
		return "["+socket.getInetAddress().toString().substring(1)+":"+Integer.toHexString(sc.hashCode())+"]";
	}
	private void dispatch(SocketChannel sc,String s) throws IOException{
		if(!clientMap.isEmpty()){
			for(Map.Entry<String, SocketChannel> entry:clientMap.entrySet()){
				SocketChannel temp  = entry.getValue();
				if(!sc.equals(temp)){
					sBuffer.clear();
					sBuffer.put(charset.encode(getClientName(sc)+" : "+s));
					sBuffer.flip();
					temp.write(sBuffer);
				}
			}
		}
	}
	public static void main(String[] args){
		NIOServer nio = new NIOServer(7777);
		nio.listen();
	}
}
