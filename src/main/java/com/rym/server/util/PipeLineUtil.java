package com.rym.server.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.rym.protocol.PipeLineProtocol;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PipeLineUtil {

	private static String getCrc(byte[] data) {
		int high;
		int flag;
		// 16位寄存器，所有数位均为1
		int wcrc = 0xffff;
		for (int i = 0; i < data.length; i++) {
			// 16 位寄存器的高位字节
			high = wcrc >> 8;
			// 取被校验串的一个字节与 16 位寄存器的高位字节进行“异或”运算
			wcrc = high ^ data[i];
			for (int j = 0; j < 8; j++) {
				flag = wcrc & 0x0001;
				// 把这个 16 寄存器向右移一位
				wcrc = wcrc >> 1;
				// 若向右(标记位)移出的数位是 1,则生成多项式 1010 0000 0000 0001 和这个寄存器进行“异或”运算
				if (flag == 1)
					wcrc ^= 0xa001;
			}
		}
		return Integer.toHexString(wcrc);
	}

	public static byte[] getEncrypByte(String content) {
		System.out.println("内容："+content);
		byte[] cb = content.getBytes();
		Long time = DateUtil.getNow().getTime() / 1000;
		System.out.println("时间："+time);
		byte[] tb = ByteUtil.toHH(time.intValue());
		System.out.println(ByteUtil.bytesToInt2(tb, 0));
		byte[] s = addBytes(cb, tb);
		int crc16 = CRC16.calcCrc16(s);
		byte[] crc16b = new byte[1];
		crc16b[0] = (byte)crc16;
		return addBytes(s, crc16b);
	}
	
	/**
	 * @param data1
	 * @param data2
	 * @return data1 与 data2拼接的结果
	 */
	public static byte[] addBytes(byte[] data1, byte[] data2) {
		byte[] data3 = new byte[data1.length + data2.length];
		System.arraycopy(data1, 0, data3, 0, data1.length);
		System.arraycopy(data2, 0, data3, data1.length, data2.length);
		return data3;
	}
	
	public static void send(String content, Socket client) throws IOException {
		sendNotEncry(content, client);
	}
	
	public static byte[] getSentBytes(byte[] cb) {
		byte[] pb = PipeLineProtocol.DOWN_PREFIX.getBytes();
		byte[] eb = PipeLineProtocol.DOWN_ENDFIX.getBytes();
		return addBytes(addBytes(pb, cb), eb);
	}
	public static void sendNotEncry(String content, Socket client) throws IOException {
		if(content==null) {
			content = "";
		}
		log.info("发送数据：{}", content);
		OutputStream out = client.getOutputStream();
		out.write(getSentBytes(content.getBytes()));
		out.flush();
	}
	
	public static void sendEncry(String content, Socket client) throws IOException {
		if(content==null) {
			content = "";
		}
		log.info("发送数据：{}", content);
		String encripContent = Crypto.encrypt(content, "ISDKJE0012300123");
		OutputStream out = client.getOutputStream();
		out.write(getEncrypByte(encripContent));
		out.flush();
	}
}
