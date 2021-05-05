package com.example.androidapi_5;

import android.os.AsyncTask;
import android.text.PrecomputedText;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.core.text.PrecomputedTextCompat;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.transform.Result;

public class GetData extends AsyncTask  {
    ConnectionHandler connectionHandler = new ConnectionHandler();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Toast.makeText(GetData.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

    }


    @Override
    protected Object doInBackground(Object[] objects) {
        HttpsURLConnection conn = connectionHandler.makeConnection();
        connectionHandler.getJsonData(conn);

        return null;
    }


}
