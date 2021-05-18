package com.example.androidapi_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FilterActivity extends AppCompatActivity {

    private Button btnMint;
    private Button btnMaterial;
    private Button btnDenomination;
    private Button btnCancelar;
    private Button btnAplicarFiltro;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        btnMint = findViewById(R.id.btnFiltrarPorMint);
        btnMaterial = findViewById(R.id.btnFiltrarPorMaterial);
        btnDenomination = findViewById(R.id.btnFiltrarPorDenomination);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnAplicarFiltro = findViewById(R.id.btnAplicarFiltros);

        addListenersButtons();



    }


    private void addListenersButtons(){
        btnMint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDenomination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancelar();
            }
        });

        btnAplicarFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void Cancelar(){
        super.onBackPressed();
    }

}