package com.example.proyectocrud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private EditText etCedula, etNombre, etApellidos, etEdad, etEstadoCivil, etnacionalidad, etGenero, etCorreo, etPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ocultar la ActionBar en la pantalla de login
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        db = new DatabaseHelper(this);

        etCedula = findViewById(R.id.etCedula);
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etEdad = findViewById(R.id.etEdad);
        etEstadoCivil = findViewById(R.id.etEstadoCivil);
        etnacionalidad = findViewById(R.id.etnacionalidad);
        etGenero = findViewById(R.id.etGenero);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnetRegistrar).setOnClickListener(v -> {
            try {
                String cedula = etCedula.getText().toString().trim();
                String nombres = etNombre.getText().toString().trim();
                String apellidos = etApellidos.getText().toString().trim();
                String edadStr = etEdad.getText().toString().trim();
                String estadoCivil = etEstadoCivil.getText().toString().trim();
                String nacionalidad = etnacionalidad.getText().toString().trim();
                String genero = etGenero.getText().toString().trim();
                String correo = etCorreo.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validar campos vacíos
                if (etCedula == null || etNombre == null || etApellidos == null ||
                        etEdad == null || etEstadoCivil == null || etnacionalidad == null ||
                        etGenero == null || etCorreo == null || etPassword == null) {
                    Log.e("RegisterActivity", "Algunos EditText son null. Revisa los IDs en el XML.");
                }


                // Validar conversión de edad
                int edad;
                try {
                    edad = Integer.parseInt(edadStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Edad debe ser un número válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insertar en la base de datos
                boolean isInserted = db.insertUser(cedula, nombres, apellidos, edad, estadoCivil, nacionalidad, genero, correo, password);
                if (isInserted) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

