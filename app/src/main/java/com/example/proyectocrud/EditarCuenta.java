package com.example.proyectocrud;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditarCuenta extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText  editNombre, editApellido, editEdad, editFechaNac,editbanco, editSaldo, editCorreo, editPassword;
    private int cuentaId; // ID de la cuenta que se está editando

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cuenta);

        dbHelper = new DatabaseHelper(this);

        // Inicializar los campos de texto
        editNombre = findViewById(R.id.editNombre);
        editApellido = findViewById(R.id.editApellido);
        editEdad = findViewById(R.id.editEdad);
        editFechaNac = findViewById(R.id.editFechaNac);
        editbanco = findViewById(R.id.editbanco);
        editSaldo = findViewById(R.id.editSaldo);
        editCorreo = findViewById(R.id.editCorreo);
        editPassword = findViewById(R.id.editPassword);

        // Botón Cancelar
        Button btnCancelar = findViewById(R.id.btneditCancelar);
        btnCancelar.setOnClickListener(v -> {
            Intent intent = new Intent(EditarCuenta.this, GestionCuenta.class);
            startActivity(intent);
            finish();
        });

        // Obtener el ID de la cuenta a editar
        Intent intent = getIntent();
        cuentaId = intent.getIntExtra("cuentaId", -1);

        if (cuentaId != -1) {
            // Cargar los datos actuales de la cuenta en los campos
            cargarDatosCuenta(cuentaId);
        }

        // Configurar el botón de guardar cambios
        Button btnGuardar = findViewById(R.id.btnEditar);
        btnGuardar.setOnClickListener(v -> guardarCambios());
    }

    // Método para cargar los datos de la cuenta
    private void cargarDatosCuenta(int id) {
        // Obtener los datos de la base de datos usando el id de la cuenta
        Cursor cursor = dbHelper.obtenerCuentaPorIdEdit(id);

        if (cursor != null && cursor.moveToFirst()) {
            // Verificar y obtener cada columna
            int nombresIndex = cursor.getColumnIndex("nombres");
            int apellidosIndex = cursor.getColumnIndex("apellidos");
            int edadIndex = cursor.getColumnIndex("edad");
            int fechaNacIndex = cursor.getColumnIndex("fecha_nacimiento"); // Asegúrate de que coincide con el nombre real de la columna
            int bancoIndex = cursor.getColumnIndex("banco");
            int saldoIndex = cursor.getColumnIndex("saldo");
            int correoIndex = cursor.getColumnIndex("correo");
            int passwordIndex = cursor.getColumnIndex("contraseña"); // Asegúrate de que coincide con el nombre real de la columna

            // Verificar que las columnas existan antes de usarlas
            if (nombresIndex != -1) {
                editNombre.setText(cursor.getString(nombresIndex));
            }
            if (apellidosIndex != -1) {
                editApellido.setText(cursor.getString(apellidosIndex));
            }
            if (edadIndex != -1) {
                editEdad.setText(String.valueOf(cursor.getInt(edadIndex)));
            }
            if (fechaNacIndex != -1) {
                editFechaNac.setText(cursor.getString(fechaNacIndex));
            }
            if (bancoIndex != -1) {
                editbanco.setText(cursor.getString(bancoIndex));
            }
            if (saldoIndex != -1) {
                editSaldo.setText(String.valueOf(cursor.getDouble(saldoIndex)));
            }
            if (correoIndex != -1) {
                editCorreo.setText(cursor.getString(correoIndex));
            }
            if (passwordIndex != -1) {
                editPassword.setText(cursor.getString(passwordIndex));
            }
        } else {
            Log.e("DatabaseError", "Cursor es nulo o está vacío. No se encontraron datos para el ID: " + id);
        }

        if (cursor != null) {
            cursor.close();
        }
    }


    // Método para guardar los cambios
    private void guardarCambios() {
        // Obtener los nuevos datos del formulario
        String nombres = editNombre.getText().toString();
        String apellidos = editApellido.getText().toString();
        int edad = Integer.parseInt(editEdad.getText().toString());
        String fechaNacimiento = editFechaNac.getText().toString();
        String banco = editbanco.getText().toString();
        double saldo = Double.parseDouble(editSaldo.getText().toString());
        String correo = editCorreo.getText().toString();
        String contraseña = editPassword.getText().toString();

        // Actualizar la cuenta en la base de datos
        boolean isUpdated = dbHelper.actualizarCuenta(cuentaId, nombres, apellidos, edad, fechaNacimiento, banco, saldo, correo, contraseña);

        if (isUpdated) {
            Toast.makeText(this, "Cuenta actualizada exitosamente", Toast.LENGTH_SHORT).show();
            // Redirigir al usuario a la pantalla de gestión de cuentas
            Intent intent = new Intent(EditarCuenta.this, GestionCuenta.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar la cuenta", Toast.LENGTH_SHORT).show();
        }
    }
}