package tests;

import tcp_control.TcpClient;
import tcp_control.TcpServer;

import java.io.IOException;

/**
 * Created by vicky on 2017/6/13.
 */
public class TcpTestMain  {

    public static void main(String[] args) throws IOException, InterruptedException {
        TcpServer tcpServer = new TcpServer(27104);
        new Thread(tcpServer).start();
        TcpClient tcpClient1= new TcpClient();
        TcpClient tcpClient2= new TcpClient();
//        new Thread(tcpClient1).start();
//        new Thread(tcpClient2).start();
        System.out.println("tcpClient2.checkIncoming()  : "+tcpClient2.checkIncoming());
        tcpClient1.call();
        Thread.sleep(200);
        System.out.println("tcpClient2.checkIncoming()  : "+tcpClient2.checkIncoming());
        for(String element :tcpClient2.getInfo()){
            System.out.println(element);
        }
        tcpClient2.call();
        System.out.println("tcpClient1.checkIncoming()  : "+tcpClient1.checkIncoming());
        for(String element :tcpClient1.getInfo()){
            System.out.println(element);
        }
    }
}



