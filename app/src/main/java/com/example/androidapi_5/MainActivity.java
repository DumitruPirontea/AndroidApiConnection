package com.example.androidapi_5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ConnectionHandler connectionHandler = new ConnectionHandler();
    GetData data;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listView1);

        new GetData().execute();


    }


    private class GetData extends AsyncTask<Void,Void,Void> {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        ArrayList<Coin> lista_datos = new ArrayList<Coin>();


        public GetData() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpsURLConnection conn = connectionHandler.makeConnection();
            lista_datos = connectionHandler.getJsonData(conn);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ArrayAdapter<Coin> adapter = new ArrayAdapter<Coin>(MainActivity.this, android.R.layout.simple_list_item_1, lista_datos);
            lv.setAdapter(adapter);


        }
    }
}