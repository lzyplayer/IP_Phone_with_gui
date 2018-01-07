package tcp_control;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by vicky on 2017/6/13.
 */
public class TcpClient{
    private Socket socket;
    String[] info;

    public TcpClient() {
        try {
            socket = new Socket("127.0.0.1",27104);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  call() throws IOException {                //发起电话连接   或者电话callACK
        String controlInfo = InetAddress.getLocalHost().getHostAddress()+" " + 27105;
        byte[] room;
        room = controlInfo.getBytes();
        socket.getOutputStream().write(room);
//        socket.getOutputStream().write(room);
        socket.getOutputStream().flush();
    }
    public boolean checkIncoming() throws IOException {     //检查是否有来电
        return socket.getInputStream().available()!=0;
    }
    public void ring() throws IOException {
        FileInputStream filea = new FileInputStream("src\\tcp_control\\ring.wav");
        AudioStream as = new AudioStream(filea);
        AudioPlayer.player.start(as);
    }
    public String[] getInfo() throws IOException {

        byte[] room =new byte[256];
        socket.getInputStream().read(room);
        if (socket.getInputStream().available()==0)return info;
        info = new String(room).split(" ");
        return info;
    }

}
