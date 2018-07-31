package com.rym.stack;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientKeepStack {
	//储存设备的连接套接字
	public static Map<String, Socket> devClientStack=new ConcurrentHashMap<>();
	//设备状态
	public static Map<String, Integer> devState = new ConcurrentHashMap<>();
	
	//设置设备状态
	public static void setState(String clientKey,Integer state) {
		devState.put(clientKey, state);
	}
	
	//获取设备状态
	public static Integer getState(String clientKey) {
		return devState.get(clientKey);
	}
	
	//获取连接
	public static Socket client(String devCode) {
		Socket client = devClientStack.get(devCode);
		return client;
	}
	//添加新的连接套接字
	public static void newOne(String clientKey,Socket client) {
		devClientStack.put(clientKey, client);
	}
	//移除指定套接字
	public static void removeOne(String devCode) {
		if(devCode!=null&&devClientStack.containsKey(devCode)) {
			devClientStack.remove(devCode);
		}
	}
	
	public static String getClientKey() {
		String rd = UUID.randomUUID().toString().replaceAll("-", "");
		return rd;
	}
	//批量移除指定套接字
	public static void remove(List<String> devCodes) {
		for (String devCode : devCodes) {
			System.out.println("需要移除的设备连接："+devCode);
			removeOne(devCode);
		}
	}
	
	public static Integer getClientSize() {
		return devClientStack.size();
	}
	
}
