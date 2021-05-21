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
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton btnFlotante;
    private static List<Coin> coinList;
    private static List<Coin> filteredCoins;

    private List<Coin> filteredCoinsMint;
    private List<Coin> filteredCoinsMaterial;
    private List<Coin> filteredCoinsDenomination;


    private CoinAdapter coinAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        coinList = new ArrayList<>();
        filteredCoins = new ArrayList<>();

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
    public void onResume() {
        super.onResume();
        // codigo aqui..
        Toast.makeText(this, "OnResume Cargado", Toast.LENGTH_SHORT).show();
        // aqui hacer que se actualice la lista de monedas cuando se devuelva un filtro.

        String selectedDenomination = FilterActivity.getSelectedDenominacion();
        String selectedMaterial = FilterActivity.getSelectedMaterial();
        String selectedMint = FilterActivity.getSelectedMint();

        Log.d("Mint>>>>", selectedMint+"");
        Log.d("Material>>>>", selectedMaterial+"");
        Log.d("Denomination>>>>", selectedDenomination+"");

/*
        filteredCoinsMint = new ArrayList<>();
        filteredCoinsMaterial = new ArrayList<>();
        filteredCoinsDenomination = new ArrayList<>();


        filteredCoins.clear();

        for(int i = 0; i < coinList.size(); i++){
            if(coinList.get(i).getMint().equals(selectedMint)){
                filteredCoinsMint.add(coinList.get(i));
            } else  if(coinList.get(i).getMaterial().equals(selectedMaterial)){
                filteredCoinsMaterial.add(coinList.get(i));
            } else  if(coinList.get(i).getDenomination().equals(selectedDenomination)){
                filteredCoinsDenomination.add(coinList.get(i));
            }

        }


        for (int i = 0; i < filteredCoinsMint.size(); i++){
            //Log.d("ListaMint filtrado = _>", String.valueOf(filteredCoinsMint.get(i)));
            filteredCoins.add(filteredCoinsMint.get(i));
        }


        for (int i = 0; i < filteredCoinsMaterial.size(); i++){
            //Log.d("ListaMaterial filtrado = _>", String.valueOf(filteredCoinsMaterial.get(i)));
            filteredCoins.add(filteredCoinsMaterial.get(i));
        }

        for (int i = 0; i < filteredCoinsDenomination.size(); i++){
            //Log.d("ListaDenomination filtrado = _>", String.valueOf(filteredCoinsDenomination.get(i)));
            filteredCoins.add(filteredCoinsDenomination.get(i));
        }

        for(int i = 0; i <filteredCoins.size(); i++){
            Log.d("Monedas filtradas---> ", filteredCoins.get(i).toString());
        }

        if (Objects.isNull(coinAdapter)) {
            coinAdapter = new CoinAdapter(this, new ArrayList<>());
        }

        Log.d("tamaño mint-_> ", String.valueOf(filteredCoinsMint.size()));
        Log.d("tamaño material-_> ", String.valueOf(filteredCoinsMaterial.size()));
        Log.d("tamaño denomination-_> ", String.valueOf(filteredCoinsDenomination.size()));
        Log.d("tamaño monedas filtradas-_> ", String.valueOf(filteredCoins.size()));

 */

        Predicate<Coin> denominationPredicate = Objects.isNull(selectedDenomination) ? coin -> true : coin -> coin.getDenomination().equals(selectedDenomination);
        Predicate<Coin> mintPredicate = Objects.isNull(selectedMint) ? coin -> true : coin -> coin.getMint().equals(selectedMint);
        Predicate<Coin> materialPredicate = Objects.isNull(selectedMaterial) ? coin -> true : coin -> coin.getMaterial().equals(selectedMaterial);
        if (Objects.isNull(selectedDenomination) && Objects.isNull(selectedMaterial) && Objects.isNull(selectedMint)) {
            filteredCoins.addAll(coinList);
        } else {
            filteredCoins.clear();
            filteredCoins.addAll(coinList.stream()
                    .filter(denominationPredicate.and(mintPredicate).and(materialPredicate))
                    .collect(Collectors.toList()));
        }
        if (Objects.isNull(coinAdapter)) {
            coinAdapter = new CoinAdapter(this, new ArrayList<>());
        }


        Log.d("tamaño monedas filtradas-_> ", String.valueOf(filteredCoins.size()));

        //coinAdapter.refresh(filteredCoins);
        PutDataIntoRecyclerView(filteredCoins);








    }


    private class Thread1 extends Thread {
        ConnectionHandler connectionHandler = new ConnectionHandler();

        @Override
        public void run() {
            try {
                ConnectionFilter connectionFilter = new ConnectionFilter();

                connectionFilter.setDedalo_get("records");
                connectionFilter.setTable("coins");
                connectionFilter.setCode("654asdiKrhdTetQksluoQaW2");
                connectionFilter.setDb_name("web_numisdata_mib"); // utilizar esta: web_numisdata_mib
                connectionFilter.setLang("lg-eng");
                connectionFilter.setLimit("50");

                HttpsURLConnection conn = connectionHandler.makeConnection(connectionFilter);
                coinList = connectionHandler.getJsonData(conn);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PutDataIntoRecyclerView(coinList);
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public static List<Coin> getCoinList() {
        return coinList;
    }


    private void PutDataIntoRecyclerView(List<Coin> listaDatos) {
        CoinAdapter coinAdapter = new CoinAdapter(this, listaDatos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(coinAdapter);
    }

    private void goToFilters() {
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