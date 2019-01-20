package com.myssm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 提供一系列获取和校验MD5的方法
 * 
 */
public class MD5Util {

	/**
	 * 获取该输入流的MD5值
	 * 
	 * @param is
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String getMD5(InputStream is)
			throws NoSuchAlgorithmException, IOException {
		StringBuffer md5 = new StringBuffer();
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] dataBytes = new byte[1024];

		int nread = 0;
		while ((nread = is.read(dataBytes)) != -1) {
			md.update(dataBytes, 0, nread);
		}
		;
		byte[] mdbytes = md.digest();

		// convert the byte to hex format
		for (int i = 0; i < mdbytes.length; i++) {
			md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return md5.toString();
	}

	/**
	 * 获取该文件的MD5值
	 * 
	 * @param file
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String getMD5(File file) throws NoSuchAlgorithmException,
			IOException {
		FileInputStream fis = new FileInputStream(file);
		return getMD5(fis);
	}

	/**
	 * 获取指定路径文件的MD5值
	 * 
	 * @param path
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String getMD5(String path) throws NoSuchAlgorithmException,
			IOException {
		FileInputStream fis = new FileInputStream(path);
		return getMD5(fis);
	}

	/**
	 * 校验该输入流的MD5值
	 * 
	 * @param is
	 * @param toBeCheckSum
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static boolean md5CheckSum(InputStream is, String toBeCheckSum)
			throws NoSuchAlgorithmException, IOException {
		return getMD5(is).equals(toBeCheckSum);
	}

	/**
	 * 校验该文件的MD5值
	 * 
	 * @param file
	 * @param toBeCheckSum
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static boolean md5CheckSum(File file, String toBeCheckSum)
			throws NoSuchAlgorithmException, IOException {
		return getMD5(file).equals(toBeCheckSum);
	}

	/**
	 * 校验指定路径文件的MD5值
	 * 
	 * @param path
	 * @param toBeCheckSum
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static boolean md5CheckSum(String path, String toBeCheckSum)
			throws NoSuchAlgorithmException, IOException {
		return getMD5(path).equals(toBeCheckSum);
	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}