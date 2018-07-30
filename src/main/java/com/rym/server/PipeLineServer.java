package com.rym.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

import org.springframework.beans.factory.annotation.Value;

import com.rym.protocol.PipeLineProtocol;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Setter
@Getter
public class PipeLineServer {
	private Integer port;
	private Integer timeOut; 
	
	public void init() throws IOException {
		ServerSocket server = new ServerSocket(port);
		log.info("pipe line server port：{}", port);
		while (true) {
			Socket client = server.accept();
			log.info("client connect");
			if(timeOut!=null&&!timeOut.equals(0)) {
				client.setSoTimeout(timeOut);
				log.info("pipe line client timeout ：{}", timeOut);
			}
			new Thread(new PipeLineProtocol(client)).start();
		}
	}
}
