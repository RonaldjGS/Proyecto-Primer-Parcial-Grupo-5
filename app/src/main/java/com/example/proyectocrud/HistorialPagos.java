package com.example.proyectocrud;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HistorialPagos extends AppCompatActivity {

    private ModuloCrediticioHelper crediticioHelper;
    private LinearLayout layoutPagos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pagos);

        crediticioHelper = new ModuloCrediticioHelper(this);
        layoutPagos = findViewById(R.id.layoutPagos);

        int creditoId = getIntent().getIntExtra("credito_id", -1);
        if (creditoId != -1) {
            cargarPagos(creditoId);
        }
    }

    private void cargarPagos(int creditoId) {
        Cursor cursor = crediticioHelper.getPagoPorCred(creditoId);
        if (cursor != null && cursor.getCount() > 0) {
            int fechaPagoIndex = cursor.getColumnIndex(DatabaseHelper.COL_FECHA_PAGO);
            int montoPagoIndex = cursor.getColumnIndex(DatabaseHelper.COL_MONTO_PAGO);
            int observacionIndex = cursor.getColumnIndex(DatabaseHelper.COL_OBSERVACION);

            if (fechaPagoIndex == -1 || montoPagoIndex == -1 || observacionIndex == -1) {
                // Si alguna columna no existe en el cursor
                Toast.makeText(this, "Error: Columnas no encontradas en la base de datos.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Si las columnas están presentes, sigue con el proceso
            while (cursor.moveToNext()) {
                String fechaPago = cursor.getString(fechaPagoIndex);
                double montoPago = cursor.getDouble(montoPagoIndex);
                String observacion = cursor.getString(observacionIndex);

                TextView pagoView = new TextView(this);
                pagoView.setText("Fecha: " + fechaPago + "\nMonto: $" + montoPago + "\nObservación: " + observacion);
                layoutPagos.addView(pagoView);
            }
        } else {
            Toast.makeText(this, "No se encontraron pagos para este crédito.", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

}
