import tcp_control.TcpServer;
import gui.MainPage;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

//import java.net.SocketException;

public class TcpServerEstab {

    public static void main(String[] args)  throws LineUnavailableException, IOException, InterruptedException {
        System.out.println("System started");
        tcp_control.TcpServer tcpServer = new tcp_control.TcpServer(27104);//开启TCP服务器
        new Thread(tcpServer).start();
//        tests.SoundIn_Test test =new tests.SoundIn_Test();





    }
}
