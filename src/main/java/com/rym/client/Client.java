package com.rym.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client{  
    public static final String IP_ADDR = "localhost";//服务器地址   
    public static final int PORT = 20033;//服务器端口号    
    
//    public static final String IP_ADDR = "192.168.1.28";//服务器地址   
//    public static final int PORT = 8041;//服务器端口号    
      
    public static void main(String[] args) {   
    	Client c = new Client();
    	c.init();
    }
    
    public void init() {
    	for (int i = 0; i < 3; i++) {
			try {
				new ClientThread();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    
    private class ClientThread implements Runnable {
    	private Socket socket;
		public ClientThread() throws UnknownHostException, IOException {
			System.out.println("客户端启动...");    
			socket = new Socket(IP_ADDR, PORT);    
			new Thread(this).start();
		}
		@Override
		public void run() {
			try {
		        OutputStream out = null;
		        InputStream input = null;
		        while (true) {    
		            try { 
		            	long heartbeatTime = 3000;
		            	Thread.sleep(heartbeatTime);
		                //向服务器端发送数据    
		                out = socket.getOutputStream();  
//		                System.out.println("向服务端发送心跳数据");
		                out.write("{}".getBytes()); 
		                //读取服务器端数据  
		                input = socket.getInputStream();  
		             // 读取客户端数据
						if(input!=null) {
							byte[] buff=new byte[100];
							int len=0;
							StringBuilder builder=new StringBuilder();
							len=input.read(buff);
							if(len>0) {
								builder.append(new String(buff, 0,len));
								// 处理客户端数据
								String mess=builder.toString();
//								System.out.println("客户端收到消息："+mess);
							}
						}
		                
		            } catch (Exception e) {  
		                System.out.println("客户端异常:" + e.getMessage());   
						if (!socket.isClosed()) {
							try {
								out.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						break;
		            } 
		        }    
	    	}catch (Exception e) {
	    		System.out.println("客户端异常:" + e.getMessage());   
			}
		}
    	
    }
    
   
    
}