package com.example.proyectocrud;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GestionCuenta extends AppCompatActivity {

    private RecyclerView recyclerViewCuentas;
    private AdaptadorCuenta adaptadorCuenta; // Declaración correcta del adaptador
    private List<CuentaBanco> listaCuenta; // Declaración correcta de la lista de cuentas
    private DatabaseHelper dbHelper;

    protected void onResume() {
        super.onResume();
        cargarCuentas();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestioncuenta);

        // Inicializar la lista de cuentas
        listaCuenta = new ArrayList<>();

        // Inicializar RecyclerView
        recyclerViewCuentas = findViewById(R.id.recyclerViewCuentas);
        recyclerViewCuentas.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar adaptador
        adaptadorCuenta = new AdaptadorCuenta(listaCuenta, new AdaptadorCuenta.OnItemClickListener() {
            @Override
            public void onEditClick(CuentaBanco cuenta) {
                // Redirigir a la actividad de editar cuenta
                Intent intent = new Intent(GestionCuenta.this, EditarCuenta.class);
                intent.putExtra("cuentaId", Integer.parseInt(cuenta.getId()));
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(CuentaBanco cuenta) {
                // Eliminar la cuenta seleccionada
                eliminarCuenta(cuenta);
            }
        });

        recyclerViewCuentas.setAdapter(adaptadorCuenta);

        // Inicializar la base de datos
        dbHelper = new DatabaseHelper(this);

        // Configurar botones
        Button btnAgregar = findViewById(R.id.btnAgregar);
        Button btnSalir = findViewById(R.id.btnSalir);

        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(GestionCuenta.this, AgregarCuentaBanco.class);
            startActivity(intent);
        });

        btnSalir.setOnClickListener(v -> {
            Intent intent = new Intent(GestionCuenta.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Cargar las cuentas en el RecyclerView
        cargarCuentas();
    }

    private void cargarCuentas() {
        listaCuenta.clear(); // Limpiar la lista antes de llenarla
        Cursor cursor = dbHelper.obtenerCuentas();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Obtener índices de las columnas
                int bancoIndex = cursor.getColumnIndex("banco");
                int nombresIndex = cursor.getColumnIndex("nombres");
                int saldoIndex = cursor.getColumnIndex("saldo");
                int idIndex = cursor.getColumnIndex("id");

                // Verificar que las columnas existan
                if (bancoIndex != -1 && nombresIndex != -1 && saldoIndex != -1 && idIndex != -1) {
                    // Obtener los valores de las columnas
                    String banco = cursor.getString(bancoIndex);
                    String nombres = cursor.getString(nombresIndex);
                    double saldo = cursor.getDouble(saldoIndex);
                    String id = cursor.getString(idIndex);

                    // Agregar cuenta a la lista
                    listaCuenta.add(new CuentaBanco(id, "", nombres, "", 0, "", saldo, banco, "", ""));
                } else {
                    Log.e("DatabaseError", "Una o más columnas no se encontraron en el cursor.");
                }
            }
            cursor.close();
        } else {
            Log.e("DatabaseError", "El cursor es nulo. La consulta falló.");
        }

        // Notificar al adaptador que los datos han cambiado
        adaptadorCuenta.actualizarLista(listaCuenta);
    }

    private void eliminarCuenta(CuentaBanco cuenta) {
        boolean isDeleted = dbHelper.eliminarCuenta(Integer.parseInt(cuenta.getId()));
        if (isDeleted) {
            // Eliminar la cuenta de la lista y actualizar el RecyclerView
            listaCuenta.remove(cuenta);
            adaptadorCuenta.actualizarLista(listaCuenta);
            Toast.makeText(this, "Cuenta eliminada exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
        }
    }
}
