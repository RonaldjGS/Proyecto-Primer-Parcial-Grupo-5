package com.example.proyectocrud;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrarPrestamo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_prestamo);

        DatabaseHelper dbHelper = new DatabaseHelper(this);


        EditText etCantidad = findViewById(R.id.txtCantPrest);
        EditText etTasa = findViewById(R.id.txtTasaInteres);
        EditText etTiempo = findViewById(R.id.txtTiempo);
        Spinner spiTipoPrestamo = findViewById(R.id.spiTipoPrestamo);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnCancelar = findViewById(R.id.btnCa);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tiposPrestamos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiTipoPrestamo.setAdapter(adapter);

        btnGuardar.setOnClickListener(v -> {
            try {
                // Obtener los datos del formulario
                String cantidad = etCantidad.getText().toString();
                String tasa = etTasa.getText().toString();
                String tiempo = etTiempo.getText().toString();
                String tipoPrestamo = spiTipoPrestamo.getSelectedItem().toString();

                // Validar que todos los campos estén completos
                if (cantidad.isEmpty() || tasa.isEmpty() || tiempo.isEmpty()) {
                    Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convertir los valores a los tipos correctos
                double cantidadValor = Double.parseDouble(cantidad);
                double tasaValor = Double.parseDouble(tasa);
                int tiempoValor = Integer.parseInt(tiempo);

                // Guardar los datos en la base de datos
                boolean success = dbHelper.agregarPrestamo(cantidadValor, tasaValor, tiempoValor, tipoPrestamo);

                if (success) {
                    Toast.makeText(this, "Préstamo registrado exitosamente", Toast.LENGTH_SHORT).show();
                    // Redirigir al menú principal de préstamos
                    Intent intent = new Intent(RegistrarPrestamo.this, Menu_MPrestamosHipotecas.class);
                    startActivity(intent);
                    finish(); // Finalizar la actividad actual
                } else {
                    Toast.makeText(this, "Error al registrar el préstamo. Inténtelo nuevamente.", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Por favor, ingrese valores válidos para los campos numéricos.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelar.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrarPrestamo.this, Menu_MPrestamosHipotecas.class);
            startActivity(intent);
            finish();
        });

    }


}