package com.example.androidapi_5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ConnectionHandler connectionHandler = new ConnectionHandler();


    List<Coin> coinList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        coinList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);


        //lv = findViewById(R.id.listView1);

        GetData getData = new GetData();
        getData.execute();


    }


    private class GetData extends AsyncTask<String, String, ArrayList<Coin>> {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        ArrayList<Coin> lista_datos = new ArrayList<Coin>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected ArrayList<Coin> doInBackground(String... strings) {
            HttpsURLConnection conn = connectionHandler.makeConnection();
            lista_datos = connectionHandler.getJsonData(conn);
            return lista_datos;
        }

        @Override
        protected void onPostExecute(ArrayList<Coin> s) {
            super.onPostExecute(s);

            PutDataIntoRecyclerView(lista_datos);


        }
    }

    private void PutDataIntoRecyclerView(List<Coin> listaDatos){
        CoinAdapter coinAdapter = new CoinAdapter(this, listaDatos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(coinAdapter);
    }
}