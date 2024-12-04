package com.example.proyectocrud;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Menu_MPrestamosHipotecas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_mprestamos_hipotecas);


        Button btnVer = findViewById(R.id.btnVer);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        Button btnSalir = findViewById(R.id.btnSalir);

        btnVer.setOnClickListener(v -> {
            Intent intent = new Intent(Menu_MPrestamosHipotecas.this, ListaPrestamo.class);
            startActivity(intent);
        });


        btnRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(Menu_MPrestamosHipotecas.this, RegistrarPrestamo.class);
            startActivity(intent);
        });

        btnSalir.setOnClickListener(v -> {
            Intent intent = new Intent(Menu_MPrestamosHipotecas.this, Menu_principal.class);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}