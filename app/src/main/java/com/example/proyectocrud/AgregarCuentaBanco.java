package com.example.proyectocrud;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AgregarCuentaBanco extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cuenta_banco);

        // Inicializar la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        Button btnBRegistrar = findViewById(R.id.btnBRegistrar);
        Button btnBCancelar = findViewById(R.id.btnBCancelar);

        // Configurar el click listener para el botón
            btnBRegistrar.setOnClickListener(v -> {
                try {
                    // Obtener los datos del formulario
                    String cedula = ((EditText) findViewById(R.id.bCedula)).getText().toString();
                    String nombres = ((EditText) findViewById(R.id.bNombre)).getText().toString();
                    String apellidos = ((EditText) findViewById(R.id.bApellidos)).getText().toString();
                    int edad = Integer.parseInt(((EditText) findViewById(R.id.bEdad)).getText().toString());
                    double saldo = Double.parseDouble(((EditText) findViewById(R.id.bSaldo)).getText().toString());
                    String fechaNacimiento = ((EditText) findViewById(R.id.bFechaNac)).getText().toString();
                    String banco = ((EditText) findViewById(R.id.bNombreBanco)).getText().toString();
                    String correo = ((EditText) findViewById(R.id.bCorreo)).getText().toString();
                    String password = ((EditText) findViewById(R.id.bPassword)).getText().toString();

                    // Validar datos obligatorios
                    if (cedula.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                        Toast.makeText(this, "Por favor, complete todos los campos obligatorios.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Guardar los datos en la base de datos
                    boolean success = dbHelper.agregarCuenta(
                            cedula, nombres, apellidos, edad, fechaNacimiento, banco, saldo, correo, password);

                    if (success) {
                        Toast.makeText(this, "Registro de cuenta bancaria exitosa", Toast.LENGTH_SHORT).show();
                        // Redirigir a la actividad de gestión de cuentas
                        Intent intent = new Intent(AgregarCuentaBanco.this, GestionCuenta.class);
                        startActivity(intent);
                        finish(); // Finalizar la actividad actual
                    } else {
                        Toast.makeText(this, "Error al agregar la cuenta. Verifique los datos.", Toast.LENGTH_SHORT).show();
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Por favor, ingrese valores válidos para los campos numéricos.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        btnBCancelar.setOnClickListener(v -> {
            // Redirigir al usuario a la actividad de gestión de cuentas
            Intent intent = new Intent(AgregarCuentaBanco.this, GestionCuenta.class);
            startActivity(intent);
            finish(); // Finalizar la actividad actual
        });
    }
}
