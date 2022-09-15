package com.example.servidor;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import paquete.Paquete;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Integer.parseInt;

public class HelloController implements Runnable {

    @FXML
    private Label LBL1;

    @FXML
    private Pane PANE;

    @FXML
    private ScrollPane SC;

    @FXML
    private VBox VB;

    public void initialize() {

            Thread hilo1=new Thread(this);
            hilo1.start();
    }

        public void run(){
            //Server socket pondra a la app a la escucha de un puerto
            boolean ban=true;
            try{
                ServerSocket servidor=new ServerSocket(9020);
                //ahora que acepte cualquier conexion que venga del exterior con el metodo accept

                while(ban){
                    Socket misocket=servidor.accept();//aceptara las conexiones que vengan del exterior
                    ObjectInputStream flujo_entrada=new ObjectInputStream(misocket.getInputStream());
                    Paquete data=(Paquete)flujo_entrada.readObject();
                    //que aparezca el texto en pantalla

                    //CONTENIDO CHAT
                    System.out.println(data.getMensaje());
                    Platform.runLater(()->{
                        //mensajes.setText(mensaje);
                            VB.getChildren().add(new Label(data.getMensaje()+" p1:"+data.getPuerto_emisor()+" p2"+data.getPuerto_receptor()));
                    });
                    //Reenviar informacion a un cierto puerto
                    Socket enviaReceptor=new Socket("127.0.0.1",data.getPuerto_receptor());
                    ObjectOutputStream paqueteReenvio=new ObjectOutputStream(enviaReceptor.getOutputStream());
                    paqueteReenvio.writeObject(data);
                    enviaReceptor.close();

                    misocket.close();
                }


            }
            catch(IOException|ClassNotFoundException e){
                System.out.println(e);

            }
        }
}
//| ClassNotFoundException