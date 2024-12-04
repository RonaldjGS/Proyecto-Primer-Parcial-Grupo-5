package com.example.proyectocrud;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ListaPrestamo extends AppCompatActivity {

    private LinearLayout contenedorPrestamos; // Contenedor donde se agregarán las filas dinámicamente
    private DatabaseHelper databaseHelper; // Conexión a la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prestamo);

        // Cambia al contenedor adecuado
        contenedorPrestamos = findViewById(R.id.contenedorPrestamos);
        databaseHelper = new DatabaseHelper(this);

        cargarPrestamos();
    }

    private void cargarPrestamos() {
        Cursor cursor = databaseHelper.obtenerPrestamos(); // Obtener datos de la tabla 'prestamos'

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Inflar el diseño de la fila
                View fila = LayoutInflater.from(this).inflate(R.layout.activity_lista_prestamo, contenedorPrestamos, false);

                // Obtener referencias a los elementos de la fila
                TextView tvCantidad = fila.findViewById(R.id.tvCantidad);
                TextView tvTasa = fila.findViewById(R.id.tvTasa);
                TextView tvTiempo = fila.findViewById(R.id.tvTiempo);
                Button btnEliminar = fila.findViewById(R.id.btnEliminar);

                // Llenar los datos
                final int idPrestamo = cursor.getInt(cursor.getColumnIndexOrThrow("id_prestamo"));
                tvCantidad.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("cantidad"))));
                tvTasa.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("tasa"))));
                tvTiempo.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("tiempo"))));

                // Configurar botón eliminar
                btnEliminar.setOnClickListener(view -> eliminarPrestamo(idPrestamo));

                // Agregar la fila al contenedor
                contenedorPrestamos.addView(fila);
            } while (cursor.moveToNext());

            // Cerrar el cursor
            cursor.close();
        }
    }

    private void eliminarPrestamo(int idPrestamo) {
        boolean eliminado = databaseHelper.eliminarPrestamo(idPrestamo);

        if (eliminado) {
            Toast.makeText(this, "Préstamo eliminado.", Toast.LENGTH_SHORT).show();
            contenedorPrestamos.removeAllViews();
            cargarPrestamos();
        } else {
            Toast.makeText(this, "Error al eliminar el préstamo.", Toast.LENGTH_SHORT).show();
        }
    }
}