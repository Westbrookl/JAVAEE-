//package com.jhc.NIOClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Set;

public class NIOClient {
	private InetSocketAddress SERVER;
	private ByteBuffer rBuffer = ByteBuffer.allocate(1024);
	private ByteBuffer sBuffer = ByteBuffer.allocate(1024);
	private static Selector selector;
	
	private Charset charset = Charset.forName("UTF-8");
	public NIOClient(int port){
		SERVER = new InetSocketAddress("localhost",port);
		try{
			init();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void init()  throws IOException{
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		socketChannel.connect(SERVER);
		while(true){
			selector.select();
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			for(SelectionKey sk:selectionKeys){
				handle(sk);
			}
			selectionKeys.clear();
		}
	}
	public void handle(SelectionKey selectionKey)throws IOException{
		try{
			if(selectionKey.isConnectable()){
				SocketChannel sc = (SocketChannel) selectionKey.channel();
				if(sc.isConnectionPending()){
					sc.finishConnect();
					System.out.println("连接成功");
					new Thread(){
						public void run(){
							while(true){
								try{
									sBuffer.clear();
									Scanner in = new Scanner(System.in);
									String text = in.nextLine();
									System.out.println(text);
									sBuffer.put(charset.encode(text));
									sBuffer.flip();
									sc.write(sBuffer);
								}catch(IOException e){
									e.printStackTrace();
								}
							}
						}
					}.start();
					sc.register(selector, SelectionKey.OP_READ);
				}
			}else if(selectionKey.isReadable()){
				SocketChannel client = (SocketChannel)selectionKey.channel();
				rBuffer.clear();
				int count = client.read(rBuffer);
				if(count > 0){
					String text = new String(rBuffer.array(),0,count);
					System.out.println(text);
				}
				
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		new NIOClient(7777);
	}
}
