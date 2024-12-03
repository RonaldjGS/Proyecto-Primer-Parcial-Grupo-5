package com.example.proyectocrud;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AgregarCredito extends AppCompatActivity {

    private ModuloCrediticioHelper crediticioHelper;

    private EditText etMonto, etTasaInteres, etPlazo, etCliente;
    private Button btnGuardar, btnCancelar;

    private int idCredito = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_credito);

        crediticioHelper = new ModuloCrediticioHelper(this);

        // Referencias a los elementos del layout
        etMonto = findViewById(R.id.etMonto);
        etTasaInteres = findViewById(R.id.etTasaInteres);
        etPlazo = findViewById(R.id.etPlazo);
        etCliente = findViewById(R.id.etCliente);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Verificar si estamos editando (ID pasado por Intent)
        Intent intent = getIntent();
        if (intent.hasExtra("id_credito")) {
            idCredito = intent.getIntExtra("id_credito", -1);
            cargarDatosCredito(idCredito); // Cargar los datos para edición
        }

        // Listener para el botón Guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar campos vacíos
                if (etMonto.getText().toString().trim().isEmpty() ||
                        etTasaInteres.getText().toString().trim().isEmpty() ||
                        etPlazo.getText().toString().trim().isEmpty() ||
                        etCliente.getText().toString().trim().isEmpty()) {
                    Toast.makeText(AgregarCredito.this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Obtener valores de los campos
                    double monto = Double.parseDouble(etMonto.getText().toString());
                    double tasaInteres = Double.parseDouble(etTasaInteres.getText().toString());
                    int plazo = Integer.parseInt(etPlazo.getText().toString());
                    String cliente = etCliente.getText().toString();

                    if (idCredito == -1) {
                        agregarCredito(monto, tasaInteres, plazo, cliente);
                    } else {
                        editarCredito(idCredito, monto, tasaInteres, plazo, cliente);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(AgregarCredito.this, "Por favor, ingresa datos válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Listener para el botón Cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cerrar la actividad
            }
        });
    }

    private void cargarDatosCredito(int id) {
        Cursor cursor = crediticioHelper.obtenerCreditoPorId(id);
        if (cursor != null && cursor.moveToFirst()) {
            try {
                // Verificar si las columnas existen en el cursor
                int montoIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_MONTO);
                int tasaInteresIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TASA_INTERES);
                int plazoIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLAZO);
                int clienteIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CLIENTE);

                // Rellenar los campos con los datos del crédito
                etMonto.setText(String.valueOf(cursor.getDouble(montoIndex)));
                etTasaInteres.setText(String.valueOf(cursor.getDouble(tasaInteresIndex)));
                etPlazo.setText(String.valueOf(cursor.getInt(plazoIndex)));
                etCliente.setText(cursor.getString(clienteIndex));
            } catch (Exception e) {
                Toast.makeText(this, "Error al cargar los datos del crédito.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                cursor.close(); // Cerrar el cursor
            }
        } else {
            Toast.makeText(this, "No se encontraron datos para este crédito.", Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarCredito(double monto, double tasaInteres, int plazo, String cliente) {
        if (crediticioHelper.agregarCredito(monto, tasaInteres, plazo, cliente)) {
            Toast.makeText(this, "Crédito agregado correctamente.", Toast.LENGTH_SHORT).show();
            finish(); // Finalizar la actividad
        } else {
            Toast.makeText(this, "Error al agregar el crédito.", Toast.LENGTH_SHORT).show();
        }
    }

    private void editarCredito(int id, double monto, double tasaInteres, int plazo, String cliente) {
        if (crediticioHelper.actualizarCredito(id, monto, tasaInteres, plazo, cliente)) {
            Toast.makeText(this, "Crédito actualizado correctamente.", Toast.LENGTH_SHORT).show();
            finish(); // Finalizar la actividad
        } else {
            Toast.makeText(this, "Error al actualizar el crédito.", Toast.LENGTH_SHORT).show();
        }
    }
}