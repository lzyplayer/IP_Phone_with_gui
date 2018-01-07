package tests;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.sound.sampled.*;


public class SoundCapture extends Thread{

    private DatagramSocket ds;
    private String ip;

    SoundCapture(DatagramSocket ds,String ip){
        this.ds = ds;
        this.ip = ip;
    }

    public void run() {
        // TODO Auto-generated method stub
        AudioFormat format =new AudioFormat(8000,16,2,true,true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class,format);

        TargetDataLine line = null;

        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format, line.getBufferSize());//打开具有format格式的行，缓冲大小为Line所系数据的最大可能
        } catch (LineUnavailableException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        byte[] data = new byte[1024];
        int numBytesRead=0;
        line.start();

        while(this !=null){
            numBytesRead = line.read(data, 0,128);//录音

            try {
                DatagramPacket dp = new DatagramPacket(data,numBytesRead,InetAddress.getByName(ip),10001);
                ds.send(dp);
                //System.out.println(numBytesRead+" send "+data);
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        line.stop();
        line.close();
        line = null;
    }

}