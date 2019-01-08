package com.hs.filestransfer.NetworkServer;

import com.hs.filestransfer.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 发送udp探测消息，发现当前网段内的其他设备
 */
public class SendUDP {

    public static ConcurrentHashMap<String,String> drivesMap = new ConcurrentHashMap<>();
    public static ArrayList<String> drives = new ArrayList<>();

    public void send() throws Exception {
        InetAddress localIP = Utils.getLocalHostLANAddress();
        System.out.println("localhost : "+ localIP.toString());
        // Use this port to send broadcast packet
        @SuppressWarnings("resource")
        final DatagramSocket detectSocket = new DatagramSocket(8888);

        // Send packet thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Send thread started.");
               while (true) {
                    try {
                        Thread.sleep(1000);
                        byte[] buf = new byte[1024];
                        int packetPort = 9999;

                        // Broadcast address
                        InetAddress hostAddress = InetAddress.getByName("192.168.31.255");
                        BufferedReader stdin = new BufferedReader(
                                new InputStreamReader(System.in));
                        String outMessage = "scan message";

                        if (outMessage.equals("bye"))
                            break;
                        buf = outMessage.getBytes();
                        //System.out.println("Send " + outMessage + " to " + hostAddress);
                        // Send packet to hostAddress:9999, server that listen
                        // 9999 would reply this packet
                        DatagramPacket out = new DatagramPacket(buf,
                                buf.length, hostAddress, packetPort);
                        detectSocket.send(out);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e){

                    }
                    
                }
            }
        }).start();
        
        // Receive packet thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Receive thread started.");
                while(true) {
                    byte[] buf = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    try {
                        detectSocket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(drivesMap.get(packet.getSocketAddress().toString()) == null && packet.getAddress()!=localIP) {
                        drivesMap.put(packet.getSocketAddress().toString(),"yes");
                        drives.add(packet.getSocketAddress().toString());
                        String rcvd = "Received from " + packet.getAddress() + ", Data="
                                + new String(packet.getData(), 0, packet.getLength());
                        System.out.println(rcvd);
                    }

                }
            }
        }).start();
    }
}
