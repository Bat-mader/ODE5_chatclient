package bader.ode5_client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class HelloApplication extends Application {

    OutputStream os;
    PrintWriter p_OUT;

    Socket connectionToServer;

    private void connect(){
        try {
            this.connectionToServer = new Socket("localhost", 4711);
            this.os = connectionToServer.getOutputStream();
            this.p_OUT = new PrintWriter(os, true);
        } catch (IOException f) {
            f.printStackTrace();
        }

    }

    public void start(Stage primaryStage) throws Exception {

        HBox righthbox = new HBox();
        HBox lefthbox = new HBox();
        VBox centerbox = new VBox();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(centerbox, 800, 600);
        righthbox.setPadding(new Insets(10));
        lefthbox.setPadding(new Insets(10));

        //MSG TEXT
        TextField txt_message = new TextField();
        txt_message.setPromptText("Enter Message");
        txt_message.setPrefSize(800,50);

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
        centerbox.getChildren().addAll(lefthbox,righthbox);
        primaryStage.setScene(scene);
        primaryStage.show();

        btn_connect.setOnAction(e -> connect());

        btn_send.setOnAction(e -> {
            String message = txt_message.getText();
            try {
                p_OUT.println(message);
            } finally {
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