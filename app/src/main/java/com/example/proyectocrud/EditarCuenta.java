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
    Cursor cursor = dbHelper.obtenerCuentaPorIdEdit(id);

    if (cursor != null && cursor.moveToFirst()) {
        try {
            editNombre.setText(cursor.getString(cursor.getColumnIndexOrThrow("nombres")));
            editApellido.setText(cursor.getString(cursor.getColumnIndexOrThrow("apellidos")));
            editEdad.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("edad"))));
            editFechaNac.setText(cursor.getString(cursor.getColumnIndexOrThrow("fecha_nacimiento")));
            editbanco.setText(cursor.getString(cursor.getColumnIndexOrThrow("banco")));
            editSaldo.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("saldo"))));
            editCorreo.setText(cursor.getString(cursor.getColumnIndexOrThrow("correo")));
            editPassword.setText(cursor.getString(cursor.getColumnIndexOrThrow("password")));
        } catch (IllegalArgumentException e) {
            Log.e("EditarCuenta", "Error al cargar datos: " + e.getMessage());
        }
    } else {
        Log.e("EditarCuenta", "No se encontraron datos para el ID: " + id);
    }

    if (cursor != null) {
        cursor.close();
    }
}


    // Método para guardar los cambios
    private void guardarCambios() {
    try {
        String nombres = editNombre.getText().toString();
        String apellidos = editApellido.getText().toString();
        int edad = Integer.parseInt(editEdad.getText().toString());
        String fechaNacimiento = editFechaNac.getText().toString();
        String banco = editbanco.getText().toString();
        double saldo = Double.parseDouble(editSaldo.getText().toString());
        String correo = editCorreo.getText().toString();
        String password = editPassword.getText().toString();

        // Validación de campos vacíos
        if (nombres.isEmpty() || apellidos.isEmpty() || banco.isEmpty() || correo.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar la cuenta en la base de datos
        boolean isUpdated = dbHelper.actualizarCuenta(cuentaId, nombres, apellidos, edad, fechaNacimiento, banco, saldo, correo, password);

        if (isUpdated) {
            Toast.makeText(this, "Cuenta actualizada exitosamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditarCuenta.this, GestionCuenta.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar la cuenta", Toast.LENGTH_SHORT).show();
        }
    } catch (NumberFormatException e) {
        Log.e("EditarCuenta", "Error de formato numérico: " + e.getMessage());
        Toast.makeText(this, "Por favor, ingrese valores numéricos válidos", Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
        // Log detallado de la excepción
        Log.e("EditarCuenta", "Error inesperado: " + e.getMessage(), e);
        Toast.makeText(this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}

}
