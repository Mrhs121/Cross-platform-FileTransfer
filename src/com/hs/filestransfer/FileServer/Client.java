package com.hs.filestransfer.FileServer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;


public class Client {

	public static Socket client;
	public static FileInputStream fileInputStream;
	public static DataOutputStream dataOutputStream;

	public Client connect(String hostname, int port) {
		try {
			client = new Socket(hostname, port);
			System.out.println("连接成功");
		} catch (IOException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}
		return this;
	}

	public void sendFile(String filePath) {
		File file = new File(filePath);
		try {

			fileInputStream = new FileInputStream(file);
			dataOutputStream = new DataOutputStream(client.getOutputStream());
			if (file.exists()) {
				System.out.println("开始传输文件");
				byte[] Buffer = new byte[1024];
				int length = -1;
				
				dataOutputStream.writeUTF(file.getName());
				dataOutputStream.writeLong(file.length());
				dataOutputStream.flush();
				
				while ((length = fileInputStream.read(Buffer)) != -1) {
					dataOutputStream.write(Buffer, 0, length);
					dataOutputStream.flush();
				}
				Thread.sleep(1000);
				client.close();
			}
		} catch (Exception e) {
			System.out.println("传输失败");
		}
	}

	public void send(String[] args) {
		if(args.length < 2){
			System.out.println("usage: destination [filepath]");
		}
		System.out.println("start send file to server");
		this.connect(args[1],7777).sendFile(args[0]);
	}
}
