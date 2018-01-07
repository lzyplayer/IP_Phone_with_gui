package tests;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Array;

import javax.sound.sampled.*;
/**
 * Created by vicky on 2017/5/6.
 */
public class SoundIn_Test {

    public SoundIn_Test() throws LineUnavailableException, IOException {



        AudioFormat audioFormat = new AudioFormat(8000, 16, 2, true, true);

        TargetDataLine dataLine = null;

        int pac_size=1024;

        dataLine = AudioSystem.getTargetDataLine(audioFormat);

        dataLine.open(audioFormat, pac_size);//打开可以录音的数据流
        dataLine.start();//允许录音到缓冲区
        System.out.println("recording started");
        System.out.println("dataLine_Active : " + dataLine.isActive());//是否在进行数据
        SourceDataLine playLine ;
        BufferedInputStream playStream;
        while (true) {

            byte[] storage = new byte[pac_size];//接收缓存区数据用
            int bytes_Already_Read;
            bytes_Already_Read = dataLine.read(storage, 0, pac_size);//要读取的字节数必须表示整数形式的样本帧数   开始读取数据


//            for (int i = 0; i <= 20; i++) {
//                System.out.println(Integer.toBinaryString(storage[i]));
//            }//看看收到的音频数据

            System.out.println("bytes_Already_Read: " + bytes_Already_Read);
//            dataLine.stop();
//            System.out.println("recording stoped");
//            dataLine.close();
//            System.out.println("recording closed");

            /**
             * 播放录音
             */

            byte[] buff = new byte[pac_size];

            playLine = AudioSystem.getSourceDataLine(audioFormat);
            playLine.open(audioFormat, pac_size);
            playStream = new BufferedInputStream(new AudioInputStream(new ByteArrayInputStream(storage), audioFormat, pac_size));
            playLine.start();
            playStream.read(buff);
            playLine.write(buff, 0, pac_size);
//            playLine.drain();
        }
//        playLine.stop();
//        playLine.close();
    }
    }


//        System.out.println("dataLine_Active : "+dataLine.isActive());//是否在进行数据
//                System.out.println("dataLine_running: "+dataLine.isRunning());//是否运行