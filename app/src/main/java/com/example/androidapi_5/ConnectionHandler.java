package com.example.androidapi_5;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class ConnectionHandler {

    ArrayList<Coin> lista_datos = new ArrayList<Coin>(); //       <<<-----
    Coin coin;

    //---------------------Construcotr---------------
    public ConnectionHandler() {

    }


    //--------------------Meotodo para la conexion-----------------------------
    public HttpsURLConnection makeConnection() {

        HttpsURLConnection conn = null;

        try {
            URL url = new URL("https://monedaiberica.org/dedalo/lib/dedalo/publication/server_api/v1/json/");
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            Log.d("Prueba 1 --->", "Todo OK");

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("dedalo_get", "records");
            jsonParam.put("table", "coins");
            jsonParam.put("code", "654asdiKrhdTetQksluoQaW2");
            jsonParam.put("db_name", "web_numisdata_mib");
            jsonParam.put("lang", "lg-eng");
            jsonParam.put("limit", 10);

            Log.d("Prueba 2 --->", "Todo OK");

            Log.d("JSON", jsonParam.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            //----------------Prueba------------
            String urlHost = url.getHost();
            String urlProtocolo = url.getProtocol();

            Log.d("PROTOCOLO --->",urlProtocolo);
            Log.d("HOST --->",urlHost);


            Log.d("HHHH----->",urlProtocolo + ":"+urlHost);

            //---------------Prueba--------------

        } catch (Exception ex) {
            Log.d("Exception 1 --> ", ex.toString());
        }



        return conn;
    }

    //-------------------------Metodo para obtener los datos---------------------------------------
    public ArrayList<Coin> getJsonData(HttpsURLConnection conn) {
        try {
            if (conn.getResponseCode() == 200) {

                InputStream in = new BufferedInputStream(conn.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();

                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("Prueba 3 --->", "Todo OK");
                //-------------------
                String jsonStr = sb.toString();

                Log.d("RESPUESTA", "Response from url: " + jsonStr);
                if (jsonStr != null) {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    Log.d("JSON", String.valueOf(jsonObj));


                    // Getting JSON Array node
                    JSONArray datos = jsonObj.getJSONArray("result");


                    // looping through All Contacts
                    for (int i = 0; i < datos.length(); i++) {
                        coin = new Coin();
                        JSONObject objetoJson = datos.getJSONObject(i);

                        coin.setNumber(objetoJson.getString("number"));
                        coin.setMint(objetoJson.getString("mint"));
                        coin.setImage_obverse("https:monedaiberica.org"+objetoJson.getString("image_obverse"));
                        coin.setImage_reverse("https:monedaiberica.org"+objetoJson.getString("image_reverse"));
                        coin.setDate_in(objetoJson.getString("date_in"));
                        coin.setDate_out(objetoJson.getString("date_out"));
                        coin.setMaterial(objetoJson.getString("material"));
                        coin.setDenomination(objetoJson.getString("denomination"));

                        lista_datos.add(coin);


                        Log.d("tabla", coin.getNumber() + " " + coin.getMint() + " " + coin.getImage_obverse() + " " + coin.getImage_reverse()
                                + " " + coin.getDate_in() + " " + coin.getDate_out() + " " + coin.getMaterial() + " " + coin.getDenomination());

                    }
                }
                Log.d("Prueba 4 --->", "Todo OK");

            }


        } catch (Exception ex) {
            Log.d("Exception 2--> ", ex.toString());
        }
        return lista_datos;
    }

}
