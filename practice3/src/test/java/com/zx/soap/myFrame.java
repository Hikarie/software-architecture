package com.zx.soap;

import javax.wsdl.WSDLException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class myFrame {
    private Frame frame;
    private myClient client;
    public void init() {
        frame = new Frame("Send Email");
        frame.setSize(350, 120);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());

        final TextField tf_url = new TextField("",30);
        frame.add(tf_url);
        tf_url.setEditable(true);

        Button btn_validate = new Button("valid");
        frame.add(btn_validate);

        final TextField tf_payload = new TextField("",30);
        frame.add(tf_payload);
        tf_payload.setEditable(true);

        Button btn_send = new Button("send");
        frame.add(btn_send);

        final Label label = new Label("Please enter the url and payload");
        frame.add(label);

        client = new myClient();

        try{
            client.create();
        }catch (Exception e){
            System.out.println(e.fillInStackTrace());
            label.setText("server is not occur");
        }


        btn_validate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                client.method="validateEmailAddress";
                client.url = tf_url.getText();
                if(client.url.equals("")){
                    label.setText("the url is empty!");
                }
                else {
                    String res = client.invoke();
                    label.setText(res);
                }
            }
        });

        btn_send.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                client.method = "sendEmail";
                client.url = tf_url.getText();
                client.payload = tf_payload.getText();
                if(client.url.equals("")){
                    label.setText("the url is empty!");
                }
                else if(client.payload.equals("")){
                    label.setText("the payload is empty!");
                }
                else {
                    String res = client.invoke();
                    label.setText(res);
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args){
        myFrame f = new myFrame();
        f.init();
    }
}
