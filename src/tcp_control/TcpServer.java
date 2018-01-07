package tcp_control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by vicky on 2017/6/13.
 */
public class TcpServer implements Runnable {
    private ServerSocket serverSocket;
    private Socket[] socket= new Socket[3];
    private int socketnum = 0;
    private int port =27104;

    private String connectMsg = "这是服务器，正在为你拨号;-)";

    public TcpServer(int port) {
        this.port = port;
    }

    public TcpServer() {
    }

    public void run() {
        //与两台客户机建立TCP连接
        try {
            serverSocket = new ServerSocket(port);
            while (socketnum < 2) {
//                System.out.println("ALert????????");
                socket[socketnum] = serverSocket.accept();
//                System.out.println("ALert!!!!!!!!");
                socketnum++;
            }
            byte[] room=new byte[512];
            while (socket[0].getInputStream().available()==0){
                Thread.sleep(100);
                socket[2]=socket[0];
                socket[0]=socket[1];
                socket[1]=socket[2];
            }//看谁先打电话，则由谁开启
            socket[0].getInputStream().read(room);
//            System.out.println("date input  :"+new String (room));
            socket[1].getOutputStream().write(room);
            socket[1].getOutputStream().flush();
            //将控制信息转发,等待确认
            Thread.sleep(6000);
            room=new byte[512];
            socket[1].getInputStream().read(room);//如果接电话者点击接听，则读到数据
            socket[0].getOutputStream().write(room);
            socket[0].getOutputStream().flush();
            Thread.sleep(2000);
            socket[1].close();
            socket[0].close();
            serverSocket.close();//控制信息传输完成，关闭TCP
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
