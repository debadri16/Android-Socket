package com.example.asus.socketapp;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout mText;
    private TextInputLayout mIP;
    private TextInputLayout mPort;
    private Button mConnectBtn;
    private Button mBtn;
    private DataInputStream input;
    private DataOutputStream out;
    private Socket socket;
    PrintWriter printwriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        mText = (TextInputLayout) findViewById(R.id.main_text);
        mIP = (TextInputLayout) findViewById(R.id.main_ip);
        mPort = (TextInputLayout) findViewById(R.id.main_port);

        mConnectBtn = (Button) findViewById(R.id.main_connect_btn);
        mBtn = (Button)findViewById(R.id.main_button);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ipAddress = mIP.getEditText().getText().toString();
                String temp = mPort.getEditText().getText().toString();
                final int port = Integer.parseInt(temp);

                final Handler h = new Handler() {
                    public void handleMessage(Message msg) {
                        Bundle bundle = msg.getData();
                        String text = bundle.getString("key");

                        if (text.equals("c")) {
                            Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                Thread t1 = new Thread() {
                    @Override
                    public void run() {

                        Message message = h.obtainMessage();

                        Bundle bundle = new Bundle();

                        try {
                            socket = new Socket(ipAddress, port);  //connect to server

                            bundle.putString("key", "c");
                            message.setData(bundle);
                            h.sendMessage(message);

                        } catch (Exception e) {
                            bundle.putString("key", e.toString());
                            message.setData(bundle);
                            h.sendMessage(message);
                        }
                    }
                };

                t1.start();

            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = mText.getEditText().getText().toString();
                sendMessage(msg);
            }
        });
    }

    private void sendMessage(final String line) {

        //Toast.makeText(MainActivity.this, "asd",Toast.LENGTH_SHORT).show();

        try {

            printwriter = new PrintWriter(socket.getOutputStream(),true);
            printwriter.write(line);  //write the message to output stream

            printwriter.flush();
            printwriter.close();
            //if(line.equals("Over"))
                socket.close();   //closing the connection

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
}
