package com.example.proyectocrud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Menu_ModuloCrediticio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_modulo_crediticio);

        // Asignar botones desde el layout
        Button btnAgCred = findViewById(R.id.btnAgregarCredito);
        Button btnVerCred = findViewById(R.id.btnVerCredito);
        Button btnRetro;
        btnRetro = findViewById(R.id.btnRegresar);

        // Listener para botón "Agregar Crédito"
        btnAgCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_ModuloCrediticio.this, AgregarCredito.class);
                startActivity(intent);
            }
        });

        // Listener para botón "Ver Crédito"
        btnVerCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_ModuloCrediticio.this, ListadoCreditos.class);
                startActivity(intent);
            }
        });

        // Listener para botón "Regresar"
        btnRetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual
            }
        });

        // Ajustar insets para ventanas EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}