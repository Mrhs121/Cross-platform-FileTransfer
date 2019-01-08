package com.hs.test.FileServer;

import com.hs.test.utils.Utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;


/**
 * 用 NIO 重构一遍
 */
public class Server {

	public static ServerSocket server;
	public static final int PORT = 7777;
	public static DataInputStream dataInputStream;
	public static FileOutputStream fileOutputStream;
	public static String defaultSavePath = "/Users/shenghuang/receive/";

	// 开启服务器
	public Server listenPort() {
		try {
			server = new ServerSocket(PORT);
			System.out.println("接收端开始成功");
		} catch (IOException e) {
			System.out.println("服务端开启失败");
			e.printStackTrace();
		}
		return this;
	}

	public void receiveFile() {
		try {
			Socket client = server.accept();
			System.out.println("Client IP :"+client.getInetAddress().toString());
			dataInputStream = new DataInputStream(client.getInputStream());

			byte[] buffer = new byte[1024];
			int length = -1;

			String fileName = dataInputStream.readUTF();
			System.out.println("Receive Filename: " + fileName);
			Long fileSize = dataInputStream.readLong();
			System.out.println("file size :"+fileSize);

			fileOutputStream = new FileOutputStream(new File(defaultSavePath + fileName));

			while ((length = dataInputStream.read(buffer)) != -1) {
				fileOutputStream.write(buffer, 0, length);
				fileOutputStream.flush();
			}
			System.out.println("传输完成");
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}


	public static void main(String[] args) throws Exception {
		
//		if(args.length < 1) {
//			System.out.println("usage : [receive path]");
//			return;
//		}
		
		String os = System.getProperty("os.name");
		System.out.println(os);
		if (os.toLowerCase().contains("windows")) {
			defaultSavePath = "D://";
		} 
		
		System.out.println("start receive file from client");
		Server server = new Server();
		InetAddress ip = Utils.getLocalHostLANAddress();
		System.out.println("Local IP: "+ip.toString());
		server.listenPort().receiveFile();
	}
}
