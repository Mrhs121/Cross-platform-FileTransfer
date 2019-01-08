package com.hs.test.run;

import com.hs.test.NetworkServer.ReceiveUDP;
import com.hs.test.NetworkServer.SendUDP;
import com.hs.test.utils.Utils;

import java.net.InetAddress;

public class Main {

    public static void main(String[] args) throws Exception {
        // 通过参数决定启动什么服务
//        if(args.length < 1){
//            System.out.println("usgae : [send filepath destination] \n [receive savepath]");
//            return;
//        }
        Utils.getLocalHostLANAddress();
        //return;
        // usgae : [send filepath destination]
        //         [receive savepath]
//        SendUDP sendUDP = new SendUDP();
//        sendUDP.send();
//        ReceiveUDP receiveUDP = new ReceiveUDP();
//        receiveUDP.run();
    }

}
