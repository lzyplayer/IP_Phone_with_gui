package voice_phone;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.IOException;
import java.net.*;

/**
 * Created by vicky on 2017/4/11.
 */
public class UdpSender implements Runnable {

    private String ip_address;
    private int  port;

    public UdpSender(String ip_address, int port) {
        this.ip_address = ip_address;
        this.port = port;
    }

    public  void run() {
        int pack_size=1024;
        DatagramSocket mysocket = null;
        try {
            mysocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        AudioFormat audioFormat = new AudioFormat(8000, 16, 2, true, true);

        TargetDataLine dataLine = null;
        try {
            dataLine = AudioSystem.getTargetDataLine(audioFormat);
            dataLine.open(audioFormat, pack_size);//打开可以录音的数据流
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        dataLine.start();//允许录音到缓冲区
       System.out.println("recording ready");
        System.out.println("dataLine_Active : " + dataLine.isActive());//是否在进行数据
        byte[] storage = new byte[pack_size];//接收缓存区数据用

        int bytes_Already_Read;
        while (true) {

//            sleep(100);
            System.out.println("recording... ");
            bytes_Already_Read = dataLine.read(storage, 0, pack_size);//要读取的字节数必须表示整数形式的样本帧数   开始读取数据

            System.out.println("bytes_Already_Read: " + bytes_Already_Read);

            DatagramPacket mypacket =new DatagramPacket(storage,storage.length,new InetSocketAddress(ip_address,port));
            try {
                mysocket.send(mypacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("UDPPacket packed up, delivered to hostname: "+ip_address+" port: "+port);
        }
//        dataLine.stop();
//        dataLine.close();


    }

}


//        for (int i = 0; i <= 20; i++) {
//            System.out.println(Integer.toBinaryString(storage[i]));
//        }

//        String text = "Hello receicer!";
//        DatagramPacket mypacket=new DatagramPacket(text.getBytes(),text.getBytes().length,new InetSocketAddress("127.0.0.1",20022));
//        mysocket.send(mypacket);
//        if (!mysocket.isClosed())mysocket.close();