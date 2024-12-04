package com.example.proyectocrud;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListadoCreditos extends AppCompatActivity {

    private ModuloCrediticioHelper crediticioHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_creditos);

        crediticioHelper = new ModuloCrediticioHelper(this);
        LinearLayout linearLayoutCreditos = findViewById(R.id.linearLayoutCreditos);
        TextView noDataMessage = findViewById(R.id.noDataMessage);

        // Consultar todos los créditos
        Cursor cursor = crediticioHelper.obtenerCreditos();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // Obtener los datos del crédito
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                double monto = cursor.getDouble(cursor.getColumnIndexOrThrow("monto"));
                double tasaInteres = cursor.getDouble(cursor.getColumnIndexOrThrow("tasa_interes"));
                int plazo = cursor.getInt(cursor.getColumnIndexOrThrow("plazo"));
                String cliente = cursor.getString(cursor.getColumnIndexOrThrow("cliente"));

                // Crear un LinearLayout para cada crédito
                LinearLayout creditoItem = new LinearLayout(this);
                creditoItem.setOrientation(LinearLayout.VERTICAL);
                creditoItem.setPadding(16, 16, 16, 16);
                creditoItem.setBackgroundResource(android.R.color.white); // Fondo blanco

                // Mostrar información del crédito
                TextView infoCredito = new TextView(this);
                infoCredito.setText("Monto: $" + monto +
                        "\nTasa de interés: " + tasaInteres + "%" +
                        "\nPlazo: " + plazo + " meses" +
                        "\nCliente: " + cliente);
                creditoItem.addView(infoCredito);

                // Botón para editar crédito
                Button btnEditar = new Button(this);
                btnEditar.setText("Editar");
                btnEditar.setOnClickListener(v -> {
                    Intent intent = new Intent(this, AgregarCredito.class);
                    intent.putExtra("id_credito", id);
                    startActivity(intent);
                });
                creditoItem.addView(btnEditar);

                // Botón para eliminar crédito
                Button btnEliminar = new Button(this);
                btnEliminar.setText("Eliminar");
                btnEliminar.setOnClickListener(v -> {
                    if (crediticioHelper.eliminarCredito(id)) {
                        Toast.makeText(this, "Crédito eliminado", Toast.LENGTH_SHORT).show();
                        recreate(); // Refrescar actividad
                    } else {
                        Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                });
                creditoItem.addView(btnEliminar);

                // Botón para ver historial de pagos
                Button btnHistorialPagos = new Button(this);
                btnHistorialPagos.setText("Ver Historial de Pagos");
                btnHistorialPagos.setOnClickListener(v -> {
                    Intent intent = new Intent(this, HistorialPagos.class);
                    intent.putExtra("credito_id", id); // Usar la misma clave
                    startActivity(intent);

                });
                creditoItem.addView(btnHistorialPagos);

                // Botón para registrar un pago
                Button btnRegistrarPago = new Button(this);
                btnRegistrarPago.setText("Registrar Pago");
                btnRegistrarPago.setOnClickListener(v -> {
                    Intent intent = new Intent(this, RegistrarPagoMC.class);
                    intent.putExtra("credito_id", id); // Enviar ID de crédito
                    startActivity(intent);
                });
                creditoItem.addView(btnRegistrarPago);

                // Agregar el crédito al layout
                linearLayoutCreditos.addView(creditoItem);
            }
            cursor.close();
        } else {
            noDataMessage.setVisibility(View.VISIBLE); // Mostrar mensaje de que no hay datos
        }
    }

}