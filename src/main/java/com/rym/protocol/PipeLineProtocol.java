package com.rym.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

import com.rym.server.util.PipeLineUtil;
import com.rym.stack.ClientKeepStack;

import lombok.extern.log4j.Log4j2;

/**
 * 管线机协议
 * @author zhengqy
 *
 */
@Log4j2
public class PipeLineProtocol  implements Runnable{
	private Socket client;
	private String clientKey;
	private int NH = 0;//空心跳连续上报次数
	private final static int MAX_NH = 3;//最大空心跳次数
	private final static String UP_PREFIX = "{";
	private final static String UP_ENDFIX = "}";
	public final static String DOWN_PREFIX = "[";
	public final static String DOWN_ENDFIX = "]";
	public final static String regex = ",";
	public PipeLineProtocol(Socket client) throws SocketException {
		client.setKeepAlive(true);
		this.client=client;
	}
	
	private boolean validProtocol(String tcpBody) {
		if(!tcpBody.startsWith(UP_PREFIX)) {
			return false;
		}else {
			if(!tcpBody.endsWith(UP_ENDFIX)) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean validCode(String code) {
    	String codesStr = "01,04,06,08,10,12,14,16,31,33,35,51,53,55,57,59,61,63,67,69,98";
    	String[] codes = codesStr.split(regex); 
    	return Arrays.asList(codes).contains(code);
    }
	
	private void closeClient() throws IOException {
		client.close();
		ClientKeepStack.removeOne(clientKey);
	}

	@Override
	public void run() {
		while (true) {
			InputStream input = null;
			try {
				// 读取客户端数据
				input = client.getInputStream();
				if(input!=null) {
					byte[] buff=new byte[100];
					int len=0;
					StringBuilder builder=new StringBuilder();
					len=input.read(buff);
					if(len>0) {
						builder.append(new String(buff, 0,len));
						// 处理客户端数据
						String mess=builder.toString();
						log.info("设备<{}>响应：{}",clientKey,mess);
						if(!validProtocol(mess)) {
							closeClient();
							log.error("非法连接，自动退出，client {}",client.getInetAddress().getHostAddress());
						}else {
							String body = mess.replace(UP_PREFIX, "").replace(UP_ENDFIX, "") ;
							if(body.length()>0) {
								String[] ms = body.split(",");
								if(ms.length>0) {
									String devCode = ms[0];
									if(clientKey!=null&&!devCode.equals(clientKey)) {
										log.info("当前连接由<{}>切换成<{}>",clientKey,devCode);
										ClientKeepStack.removeOne(clientKey);
									}
									clientKey = devCode;
									ClientKeepStack.newOne(devCode, client);
									if(ms.length==1) {//心跳
										log.info("设备<{}>连接正常，当前连接设备数量：{}",clientKey,ClientKeepStack.getClientSize());
										PipeLineUtil.send(null,client);
									}else {//处理相关业务
										String cmdCode = ms[1];
										if(!validCode(cmdCode)) {
											closeClient();
											log.error("非法上行控制码，自动退出，client {}",client.getInetAddress().getHostAddress());
										}
										if(cmdCode.equals("63")) {//订单上报
											StringBuffer command = new StringBuffer();
											command.append(devCode).append(PipeLineProtocol.regex)
											.append("64").append(PipeLineProtocol.regex)
											.append(ms[2]);
											PipeLineUtil.send(command.toString(),client);
										}
									}
								}
								NH=0;
							}else {
								NH++;
								PipeLineUtil.send(null,client);
								if(NH>MAX_NH) {
									closeClient();
									log.info("连续空心跳超过{}次，自动断开连接，client {}",MAX_NH,client.getInetAddress().getHostAddress());
								}
							}
						}
					}else {
						closeClient();
						log.info("客户端断开连接，client {}",client.getInetAddress().getHostAddress());
						return;
					}
				}
			} catch (Exception e) {
				log.error("服务器 run 异常: " + e.getMessage());
				ClientKeepStack.removeOne(clientKey);
				if (!client.isClosed()) {
					try {
						if(input!=null)
							input.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				break;
			} 
		}
	}


}
