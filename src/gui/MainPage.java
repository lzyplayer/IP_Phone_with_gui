package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tcp_control.TcpClient;
import voice_phone.UdpReceiver;
import voice_phone.UdpSender;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * Created by vicky on 2017/5/17.
 */
public class MainPage extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        ScheduledExecutorService executorService=  Executors.newScheduledThreadPool(5);
        TcpClient client =new TcpClient();
        boolean isCaller=false;
        //主界面，拨打电话界面
        GridPane pane =new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.add(new Label("server_IP_address:"),0,0);
        pane.add(new Label("server_Port:"),0,1);
        TextField textField_ip=new TextField();
        TextField textField_port =new TextField();
        pane.add(textField_ip,1,0);
        pane.add(textField_port,1,1);
        Button caller =new Button("   call   ");
        pane.add(caller,0,2);
        Button check =new Button("   check  ");
        pane.add(check,1,2);

        //来电界面
        GridPane paneIncoming=new GridPane();
        paneIncoming.setAlignment(Pos.CENTER);
        paneIncoming.setHgap(10);
        paneIncoming.setVgap(10);
        paneIncoming.add(new Label("Source_IP_address:"),0,0);
        paneIncoming.add(new Label("Source_Port:"),0,1);
        TextField Source_ip=new TextField();
        TextField Source_port =new TextField();
        paneIncoming.add(Source_ip,1,0);
        paneIncoming.add(Source_port,1,1);
        Button answer =new Button("  answer  ");
        paneIncoming.add(answer,0,2);
        Button refuse =new Button("  refuse  ");
        paneIncoming.add(refuse,1,2);

        Scene sceneIncoming =new Scene(paneIncoming);
        Scene scene =new Scene(pane);

        Stage stage=new Stage();
        stage.setHeight(200);
        stage.setWidth(400);
        stage.setScene(scene);
        stage.setTitle("IP_phone :-)");
        stage.show();


        check.setOnAction((e) ->{

            try {
                if (client.checkIncoming()){
                    stage.setScene(sceneIncoming);
                    stage.setTitle("Someone is calling in !!");
                    String[] info=client.getInfo();
                    Source_ip.setText(info[0]);
                    Source_port.setText(info[1]);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } );
        caller.setOnAction((ActionEvent e) ->{
                    try {

                        stage.setTitle("已与服务器建立连接！");
                        Thread.sleep(500);
                        client.call();
                        executorService.shutdown();//关闭监听是否有人打进来
                        stage.setTitle("请求已转发:-)");
                        String[] info=client.getInfo();
                        stage.setTitle("已经接通;-)");
                        UdpReceiver receiver =new UdpReceiver(Integer.parseInt(info[1].trim()));
                        new Thread(receiver).start();

                    } catch (IOException | InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
        );

        answer.setOnAction((e) ->{
            try {
                client.call();
                stage.setTitle("已经接通:—）");
                String[] info=client.getInfo();
                System.out.println(info[1]);
                UdpSender sender =new UdpSender(info[0],Integer.parseInt(info[1].trim()));
                new Thread(sender).start();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } );

        ScheduledFuture scheduledFuture = executorService.scheduleAtFixedRate(
                () -> {
                    try {
                        if (client.checkIncoming()){
                            System.out.println("Incoming! Ring!");
                            client.ring();
//                            System.out.println("reached!2");


                            executorService.shutdown();


                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                ,0,1,TimeUnit.SECONDS
        );


    }
    public static void run(){
        Application.launch();
    }
}
