package bader.ode5_client;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HelloApplication extends Application {

    OutputStream os;
    InputStream is;

    PrintWriter p_OUT;
    BufferedReader p_IN;

    Socket connectionToServer;

    ObservableList<String> txt_received;

    private void connect(){
        try {
            this.connectionToServer = new Socket("localhost", 4711);
            this.os = connectionToServer.getOutputStream();
            this.p_OUT = new PrintWriter(os, true);
            this.is = this.connectionToServer.getInputStream();
            this.p_IN = new BufferedReader(new InputStreamReader(this.is));
        } catch (IOException f) {
            f.printStackTrace();
        }

    }

    public void start(Stage primaryStage) throws Exception {

        HBox righthbox = new HBox();
        HBox lefthbox = new HBox();
        VBox centerbox = new VBox();
        VBox receivedmsg = new VBox();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(centerbox, 800, 600);
        righthbox.setPadding(new Insets(10));
        lefthbox.setPadding(new Insets(10));


        //MSG TEXT
        TextField txt_message = new TextField();
        txt_message.setPromptText("Enter Message");
        txt_message.setPrefSize(800,50);
        this.txt_received = FXCollections.observableArrayList();
        ListView<String> recievedListivew = new ListView<>(txt_received);




        //BUTTONS
        Button btn_send = new Button("Send");
        btn_send.setPrefWidth(200);
        Button btn_term = new Button("Close Program");
        btn_term.setPrefWidth(200);
        Button btn_connect = new Button("Connect");
        btn_connect.setPrefWidth(200);

        //FILLING BOXES
        primaryStage.setTitle("Chat Client");
        righthbox.getChildren().addAll(btn_connect, btn_term, btn_send);
        lefthbox.getChildren().addAll(txt_message);
        receivedmsg.getChildren().addAll(recievedListivew);
        centerbox.getChildren().addAll(lefthbox,righthbox,receivedmsg);
        primaryStage.setScene(scene);
        primaryStage.show();

        btn_connect.setOnAction(e -> connect());

        btn_send.setOnAction(e -> {
            String message = txt_message.getText();
            try {
                p_OUT.println(message);
                /*String line;
                StringBuilder content = new StringBuilder();
                while ((line = p_IN.readLine()) != null) {
                    if (line.contains("**TERM**")) {
                        this.is.close();
                        this.connectionToServer.close();
                        return;
                    }
                    content.append(line);
                    content.append(System.lineSeparator());
                }
                this.txt_received.add(content.toString());
                content.setLength(0);*/
            }finally {

            }
        });

        btn_term.setOnAction(e -> {
            try {
                p_OUT.println("**TERM**");
                p_OUT.close();
                os.close();
                connectionToServer.close();
            } catch (IOException f) {
                f.printStackTrace();
            }
        });


    }

    public static void main(String[] args) {
        launch();
    }
}