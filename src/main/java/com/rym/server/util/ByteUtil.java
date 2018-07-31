package com.rym.server.util;

public class ByteUtil {
	public static byte[] intToByteArray(int integer) {

		int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer : integer)) / 8;

		byte[] byteArray = new byte[4];

		for (int n = 0; n < byteNum; n++)

			byteArray[3 - n] = (byte) (integer >>> (n * 8));

		return byteArray;

	}

	/**
	 * 
	 * 将int转为低字节在前，高字节在后的byte数组
	 * 
	 */

	public static byte[] tolh(int n) {

		byte[] b = new byte[4];

		b[0] = (byte) (n & 0xff);

		b[1] = (byte) (n >> 8 & 0xff);

		b[2] = (byte) (n >> 16 & 0xff);

		b[3] = (byte) (n >> 24 & 0xff);

		return b;

	}

	/**
	 * 
	 * 将int转为高字节在前，低字节在后的byte数组
	 * 
	 * @param n
	 *            int
	 * 
	 * @return byte[]
	 * 
	 */

	public static byte[] toHH(int n) {

		byte[] b = new byte[4];

		b[3] = (byte) (n & 0xff);

		b[2] = (byte) (n >> 8 & 0xff);

		b[1] = (byte) (n >> 16 & 0xff);

		b[0] = (byte) (n >> 24 & 0xff);

		return b;

	}

	/**
	 * 
	 * 将byte数组转化成String
	 * 
	 */

	public static String toStr(byte[] valArr, int maxLen) {

		int index = 0;

		while (index < valArr.length && index < maxLen) {

			if (valArr[index] == 0) {

				break;

			}

			index++;

		}

		byte[] temp = new byte[index];

		System.arraycopy(valArr, 0, temp, 0, index);

		return new String(temp);

	}

	/**
	 * 
	 * 将低字节在前转为int，高字节在后的byte数组
	 * 
	 */

	public static int vtolh(byte[] bArr) {

		int n = 0;

		for (int i = 0; i < bArr.length && i < 4; i++) {

			int left = i * 8;

			n += (bArr[i] << left);

		}

		return n;

	}

	// 高前低后

	public static int bytesToInt2(byte[] src, int offset) {

		int value;

		value = (int) (((src[offset] & 0xFF) << 24)

				| ((src[offset + 1] & 0xFF) << 16)

				| ((src[offset + 2] & 0xFF) << 8)

				| (src[offset + 3] & 0xFF));

		return value;

	}

}
