package com.example.asus.socketapp;

import android.os.AsyncTask;
import android.os.Handler;
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
        mBtn = (Button)findViewById(R.id.main_button);

    }

    @Override
    protected void onStart() {
        super.onStart();

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

            socket = new Socket("192.168.2.12", 6000);  //connect to server
            printwriter = new PrintWriter(socket.getOutputStream(),true);
            printwriter.write(line);  //write the message to output stream

            printwriter.flush();
            printwriter.close();
            socket.close();   //closing the connection

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
}
