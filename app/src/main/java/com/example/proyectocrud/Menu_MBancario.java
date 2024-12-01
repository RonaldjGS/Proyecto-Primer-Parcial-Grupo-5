package com.example.proyectocrud;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Menu_MBancario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_mbancario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        // Encuentra el TextView y configura el listener
        TextView txtAgregarCuenta = findViewById(R.id.txtAgregarCuenta);
        txtAgregarCuenta.setOnClickListener(view -> { // Inicia la actividad AgregarCuentaBanco

            Intent intent = new Intent(Menu_MBancario.this, AgregarCuentaBanco.class);
            startActivity(intent);
        });

    }




}