package com.example.androidapi_5;

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

    ArrayList<String> lista_datos = new ArrayList<String>(); //       <<<-----

    //---------------------Construcotr---------------
    public ConnectionHandler() {

    }

    //-------------------------------------------------
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

                    Log.d("HOLA", "HOLA");

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("dedalo_get", "records");
                    jsonParam.put("table", "coins");
                    jsonParam.put("code", "654asdiKrhdTetQksluoQaW2");
                    jsonParam.put("db_name", "web_numisdata_mib");
                    jsonParam.put("lang", "lg-eng");
                    jsonParam.put("limit", 10);

                    Log.d("HOLA", "HOLA");

                    Log.d("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();


                } catch (Exception ex) {
                    Log.d("Exception 1 --> ", ex.toString());
                }
                
        return conn;
    }

    //----------------------------------------------------------------
    public ArrayList<String> getJsonData(HttpsURLConnection conn) {
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
                        //-------------------
                        String jsonStr = sb.toString();

                        Log.d("RESPUESTA", "Response from url: " + jsonStr);
                        if(jsonStr != null){
                            JSONObject jsonObj = new JSONObject(jsonStr);
                            Log.d("JSON", String.valueOf(jsonObj));


                            // Getting JSON Array node
                            JSONArray contacts = jsonObj.getJSONArray("result");


                            // looping through All Contacts
                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);

                                String number = c.getString("number");
                                String mint = c.getString("mint");
                                String image_obverse = c.getString("image_obverse");
                                String image_reverse = c.getString("image_reverse");
                                String date_in = c.getString("date_in");
                                String date_out = c.getString("date_out");
                                String material = c.getString("material");
                                String denomination = c.getString("denomination");

                                lista_datos.add(number);
                                lista_datos.add(mint);
                                lista_datos.add(image_obverse);
                                lista_datos.add(image_reverse);
                                lista_datos.add(date_in);
                                lista_datos.add(date_out);
                                lista_datos.add(material);
                                lista_datos.add(denomination);


                                Log.d("tabla", number + " " + mint + " " + image_obverse + " " + image_reverse + " " + date_in + " " +date_out + " " +material + " "+denomination);

                            }
                        }

                    }


                } catch (Exception ex) {
                    Log.d("Exception 2--> ", ex.toString());
                }
        return lista_datos;
    }

}
