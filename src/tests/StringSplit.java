package tests;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by vicky on 2017/6/13.
 */
public class StringSplit {
    public static void main(String[] args) throws IOException {
//        Object[] tom = new Object[10];
//        tom[0]=1;
//        tom[2]=true;
//        boolean result =(boolean)(tom[2]);
//        System.out.println(InetAddress.getLocalHost().getHostAddress());
//        String tommmy ="tom tommy";
//        String[] jim= tommmy.split(" ");
//        System.out.println(jim[0]+"    damn       "+jim[1]);
        FileInputStream filea = new FileInputStream("src\\tcp_control\\ring.wav");
        AudioStream as = new AudioStream(filea);
        AudioPlayer.player.start(as);
    }
}
