package tests;

import java.net.*;

/**
 * Created by vicky on 2017/4/11.
 */
public class UDP_test {
    public static void datagramSocketCreate() throws SocketException {
        SocketAddress mySAddress=new InetSocketAddress("127.0.0.1",20022);
        DatagramSocket mysocket = new DatagramSocket(mySAddress);
//        mysocket.close();
//        mysocket.bind(mySAddress);
        System.out.println(
//                "mysocket.getChannel().toString():"+mysocket.getChannel().toString()+
                    "\nmysocket.getInetAddress():" + mysocket.getInetAddress()+
                        "\nmysocket.getPort():"+mysocket.getPort()+
                        "\nmysocket.getLocalAddress:"+mysocket.getLocalAddress()+
                        "\nmysocket.getLocalPort():"+mysocket.getLocalPort());

        if (!mysocket.isClosed())
        mysocket.close();

    }
}
