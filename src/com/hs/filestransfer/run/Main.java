package com.hs.filestransfer.run;

import com.hs.filestransfer.utils.Utils;

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
