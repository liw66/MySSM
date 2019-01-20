package com.myssm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

	private FTPClient ftp;

	/**
	 * 
	 * @param addr
	 *            地址
	 * @param port
	 *            端口号
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 * @throws Exception
	 */
	public boolean connect(String addr, int port, String username,
			String password) throws Exception {
		boolean result = false;
		ftp = new FTPClient();
		int reply;
		ftp.connect(addr, port);
		ftp.login(username, password);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return result;
		}
		result = true;
		return result;
	}

	/**
	 * @author
	 * @class ItemFtp
	 * @title upload
	 * @Description :
	 * @time 2013 2013-11-27
	 * @return void
	 * @exception :(Error note)
	 * @param file
	 *            上传的文件或文件夹
	 * @param path
	 *            上传的文件的路径
	 * @throws Exception
	 */
	public void upload(File file, String path) throws Exception {

		System.out.println(" file.isDirectory() : " + file.isDirectory());

		if (file.isDirectory()) {
			ftp.makeDirectory(file.getName());
			ftp.changeWorkingDirectory(file.getName());
			String[] files = file.list();
			for (int i = 0; i < files.length; i++) {
				File file1 = new File(file.getPath() + "\\" + files[i]);
				if (file1.isDirectory()) {
					upload(file1, path);
					ftp.changeToParentDirectory();
				} else {
					File file2 = new File(file.getPath() + "\\" + files[i]);
					FileInputStream input = new FileInputStream(file2);
					ftp.storeFile(file2.getName(), input);
					input.close();
				}
			}
		} else {
			File file2 = new File(file.getPath());

			System.out.println(" file.getPath() : " + file.getPath()
					+ " | file2.getName() : " + file2.getName());

			InputStream input = new FileInputStream(file2);

			ftp.changeWorkingDirectory(path);
			ftp.storeFile(file2.getName(), input);

			input.close(); // 关闭输入流
			ftp.logout(); // 退出连接
		}
	}

	/**
	 * @author
	 * @class ItemFtp
	 * @title download
	 * @Description : FPT 下载文件方法
	 * @time 2013 2013-11-27
	 * @return void
	 * @exception :(Error note)
	 * @param reomvepath
	 *            下载的文件的路径
	 * @param fileName
	 *            下载的文件名
	 * @param localPath
	 *            下载的文件本地路径
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void download(String reomvepath, String fileName, String localPath)
			throws Exception {

		ftp.changeWorkingDirectory(reomvepath);

		// 列出该目录下所有文件
		FTPFile[] fs = ftp.listFiles();
		// 遍历所有文件，找到指定的文件
		for (FTPFile ff : fs) {
			if (ff.getName().equals(fileName)) {
				// 根据绝对路径初始化文件
				File localFile = new File(localPath + "/" + ff.getName());
				// 输出流
				OutputStream is = new FileOutputStream(localFile);
				// 下载文件
				ftp.retrieveFile(ff.getName(), is);
				System.out.println("下载成功!");
				is.close();
			}
		}

		ftp.logout(); // 退出连接

	}
}