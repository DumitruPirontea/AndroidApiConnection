package com.example.androidapi_5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private List<Coin> coinList;
    private RecyclerView recyclerView;
    private FloatingActionButton btnFlotante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        coinList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        btnFlotante = findViewById(R.id.floatingActionButton);

        /*
        GetData getData = new GetData();
        getData.execute();

         */

        new Thread1().start();

        btnFlotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFilters();
            }
        });


    }

    @Override
    public void onResume(){
        super.onResume();
        // codigo aqui..
        Toast.makeText(this, "OnResume Cargado", Toast.LENGTH_SHORT).show();
        // aqui hacer que se actualice la lista de monedas cuando se devuelva un filtro.

    }



    private class Thread1 extends Thread{
        ConnectionHandler connectionHandler = new ConnectionHandler();
        ArrayList<Coin> lista_datos = new ArrayList<Coin>();

        @Override
        public void run(){
            try{
                ConnectionFilter connectionFilter = new ConnectionFilter();

                connectionFilter.setDedalo_get("records");
                connectionFilter.setTable("coins");
                connectionFilter.setCode("654asdiKrhdTetQksluoQaW2");
                connectionFilter.setDb_name("web_numisdata_mib");
                connectionFilter.setLang("lg-eng");
                connectionFilter.setLimit("20");

                HttpsURLConnection conn =connectionHandler.makeConnection(connectionFilter);
                lista_datos = connectionHandler.getJsonData(conn);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PutDataIntoRecyclerView(lista_datos);
                    }
                });

            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }




    private void PutDataIntoRecyclerView(List<Coin> listaDatos){
        CoinAdapter coinAdapter = new CoinAdapter(this, listaDatos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(coinAdapter);
    }

    private void goToFilters(){
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);
    }




    /*
    private class GetData extends AsyncTask<String, String, ArrayList<Coin>> {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        ArrayList<Coin> lista_datos = new ArrayList<Coin>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected ArrayList<Coin> doInBackground(String... strings) {
            ConnectionFilter connectionFilter = new ConnectionFilter();

            connectionFilter.setDedalo_get("records");
            connectionFilter.setTable("coins");
            connectionFilter.setCode("654asdiKrhdTetQksluoQaW2");
            connectionFilter.setDb_name("web_numisdata_mib");
            connectionFilter.setLang("lg-eng");
            connectionFilter.setLimit("20");



           HttpsURLConnection conn = connectionHandler.makeConnection(connectionFilter);
            Log.d("------> 1; ","1");

            lista_datos = connectionHandler.getJsonData(conn);
            Log.d("-------->1;" ,"2");
            return lista_datos;
        }

        @Override
        protected void onPostExecute(ArrayList<Coin> s) {
            super.onPostExecute(s);

            PutDataIntoRecyclerView(lista_datos);


        }
    }

     */


}