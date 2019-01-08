package com.hs.filestransfer.NetworkServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

// 接受udp的探测消息，并回复
// 用NIO重构一遍
public class ReceiveUDP extends Thread{

    @Override
    public void run() {
        int listenPort = 9999;
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        @SuppressWarnings("resource")
        DatagramSocket responseSocket = null;
        try {
            responseSocket = new DatagramSocket(listenPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("Server started, Listen port: " + listenPort);
        while (true) {
            try {
                responseSocket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String rcvd = "Received "
                    + new String(packet.getData(), 0, packet.getLength())
                    + " from address: " + packet.getSocketAddress();
            System.out.println(rcvd);

            // Send a response packet to sender
            String backData = "DCBA";
            byte[] data = backData.getBytes();
            System.out.println("Send " + backData + " to " + packet.getSocketAddress());
            DatagramPacket backPacket = new DatagramPacket(data, 0,
                    data.length, packet.getSocketAddress());
            try {
                responseSocket.send(backPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void receive() throws Exception {

    }
}