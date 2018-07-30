package com.rym.server.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.rym.protocol.PipeLineProtocol;

public class SocketUtil {
	public static void send(String content,Socket client) throws IOException {
		String msg = PipeLineProtocol.DOWN_PREFIX+content+PipeLineProtocol.DOWN_ENDFIX;
    	DataOutputStream out = new DataOutputStream(client.getOutputStream());
		out.writeUTF(msg);
		out.flush();
    }
}
