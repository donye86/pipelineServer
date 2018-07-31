package com.rym.server.util;

import java.util.Base64;

public class Crypto {
	static byte[] iv = "I can't tell YOU".getBytes();

	/**
	 * 
	 * @param text
	 *            明文
	 * @param miyao
	 *            秘钥:16位字符串
	 * @return 加密好的字符串
	 */
	public static String encrypt(String text, String miyao) {
		byte[] pswd = miyao.getBytes();
		byte[] buf = text.getBytes();
		int len = text.length();
		byte[] pswd_iv = new byte[16];
		byte[] result = new byte[len + 1];
		byte pswd_sum = 0;
		int i;

		for (i = 0; i < 16; i++) {
			if ((i % 2) == 0)
				pswd_sum += (byte) (pswd[i] + iv[i]);
			else
				pswd_sum -= (byte) (pswd[i] - iv[i]);
		}

		for (i = 0; i < 16; i++) {
			pswd_iv[i] = (byte) ((pswd[i] & 0xaa) | (pswd[15 - i] & 0x55));
			pswd_iv[i] = (byte) (pswd_iv[i] + (iv[15 - i] + i));
			if ((i % 2) == 0)
				pswd_iv[i] = (byte) (~pswd_iv[i] + pswd_sum);
			else
				pswd_iv[i] = (byte) (pswd_iv[i] - ~pswd_sum);
		}

		result[len] = 0;
		for (i = 0; i < len; i++) {
			switch (i % 4) {
			case 0:
				result[len] += (byte) ~((buf[i] << 1) | ((~(buf[i] >> 7)) & 0xf));
				break;
			case 1:
				result[len] -= (byte) ((buf[i] << 4) | ((~(buf[i] >> 4)) & 0xf));
				break;
			case 2:
				result[len] += (byte) ((buf[i] << 6) | ((~(buf[i] >> 2)) & 0x3f));
				break;
			default:
				result[len] -= (byte) ~((buf[i] << 3) | ((~(buf[i] >> 5)) & 0xf));
				break;
			}
		}
		result[len] = (byte) (result[len] + ~pswd_sum);

		for (i = 0; i < len; i++) {
			result[i] = (byte) ((buf[i] & 0x72) | (buf[len - 1 - i] & 0x8d));
			result[i] = (byte) ~result[i];
			if ((i % 2) == 0)
				result[i] += (byte) (pswd_iv[i % 16] - result[len]);
			else
				result[i] -= (byte) (pswd_iv[i % 16] + result[len]);
		}

		return new String(Base64.getEncoder().encode(result)).replaceAll("[\\t\\n\\r]", "");
	}

	/**
	 * 
	 * @param text
	 *            密文：字符串
	 * @param miyao
	 *            秘钥：16位字符串
	 * @return 空字符串或解密出的字符串
	 */

	public static String decrypt(String text, String miyao) {
		byte[] buf = null;
		try {
			buf = Base64.getDecoder().decode(text);
		} catch (Exception e) {
			return "";
		}
		int len = buf.length;
		byte[] pswd_iv = new byte[16];
		byte[] result_temp = new byte[len - 1];
		byte[] result = new byte[len - 1];
		byte pswd_sum = 0;
		byte sum_temp = 0;
		byte[] pswd = miyao.getBytes();
		int i;

		if (len < 2) {
			return "";
		}

		for (i = 0; i < 16; i++) {
			if ((i % 2) == 0)
				pswd_sum += (byte) (pswd[i] + iv[i]);
			else
				pswd_sum -= (byte) (pswd[i] - iv[i]);
		}

		for (i = 0; i < 16; i++) {
			pswd_iv[i] = (byte) ((pswd[i] & 0xaa) | (pswd[15 - i] & 0x55));
			pswd_iv[i] = (byte) (pswd_iv[i] + (iv[15 - i] + i));
			if ((i % 2) == 0)
				pswd_iv[i] = (byte) (~pswd_iv[i] + pswd_sum);
			else
				pswd_iv[i] = (byte) (pswd_iv[i] - ~pswd_sum);
		}

		for (i = 0; i < len - 1; i++) {
			if ((i % 2) == 0)
				result_temp[i] = (byte) (buf[i] - (pswd_iv[i % 16] - buf[len - 1]));
			else
				result_temp[i] = (byte) (buf[i] + (pswd_iv[i % 16] + buf[len - 1]));

			result_temp[i] = (byte) ~result_temp[i];
		}

		for (i = 0; i < len - 1; i++) {
			result[i] = (byte) ((result_temp[i] & 0x72) | (result_temp[len - 2 - i] & 0x8d));
		}

		for (i = 0; i < len - 1; i++) {
			switch (i % 4) {
			case 0:
				sum_temp += (byte) ~((result[i] << 1) | ((~(result[i] >> 7)) & 0xf));
				break;
			case 1:
				sum_temp -= (byte) ((result[i] << 4) | ((~(result[i] >> 4)) & 0xf));
				break;
			case 2:
				sum_temp += (byte) ((result[i] << 6) | ((~(result[i] >> 2)) & 0x3f));
				break;
			default:
				sum_temp -= (byte) ~((result[i] << 3) | ((~(result[i] >> 5)) & 0xf));
				break;
			}
		}
		sum_temp = (byte) (sum_temp + ~pswd_sum);

		if (sum_temp != buf[len - 1]) {
			return "";
		}

		// test
		// richTextBox_jiemi.Text = new String(result);
		// test end

		return new String(result);
	}

}
