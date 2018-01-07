package voice_phone;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by vicky on 2017/4/11.
 */
public class UdpReceiver implements Runnable {

    private int  port;

    public UdpReceiver(int port) {

        this.port = port;
    }

    public  void run()  {


        int pack_size=1024;

        DatagramSocket receiver_socket = null;     //声明带端口号的套接字
        try {
            receiver_socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        AudioFormat audioFormat = new AudioFormat(8000, 16, 2, true, true);//音频格式规定
        byte[] Tommy=new byte[pack_size];
        DatagramPacket receiver_packet = new DatagramPacket(Tommy,Tommy.length);        //用于接收的UDP包包

        int Bytes_Already_read;

        SourceDataLine playLine ; //声明用以输出音频的源数据行
        BufferedInputStream playStream;         //用以整合接收到的UDP包数据

        System.out.println("Voice playLine ready");
        while (true){

            try {
                receiver_socket.receive(receiver_packet);
                System.out.println("UDPPacket received");

                playStream = new BufferedInputStream(new AudioInputStream(new ByteArrayInputStream(Tommy), audioFormat, pack_size)); //整合UDP包为音频数据信息
//            playLine.start();
                byte[] buff =new byte[pack_size];
                Bytes_Already_read=playStream.read(buff);//将整合好的信息置入buff
                playLine=AudioSystem.getSourceDataLine(audioFormat);          //配置输出用源数据行
                playLine.open(audioFormat, pack_size);
                playLine.start();
                System.out.println(Bytes_Already_read);
                playLine.write(buff, 0, pack_size);//音频输出
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

            //  playLine.drain();                   //使当前音频输出完毕，如果停顿太大则可以尝试删去


        }
        //            playLine.stop();
        //        playLine.close();

    }
}

//
//        SocketAddress mySAddress=new InetSocketAddress("127.0.0.1",20022);

//            System.out.println(new String(receiver_packet.getData()));

//        String text = "Hello receicer!";
//        DatagramPacket mypacket=new DatagramPacket(text.getBytes(),text.getBytes().length,new InetSocketAddress("127.0.0.1",20022));
//        mysocket.send(mypacket);
//        if (!receiver_socket.isClosed())receiver_socket.close();