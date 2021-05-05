package com.example.androidapi_5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ConnectionHandler connectionHandler = new ConnectionHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       HttpsURLConnection conn = connectionHandler.makeConnection();
       connectionHandler.getJsonData(conn);


    }
}