package com.example.proyectocrud;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private EditText etCorreoLogin, etPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        etCorreoLogin = findViewById(R.id.etCorreoLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);

        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            try {
                String correo = etCorreoLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();

                if (correo.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (db.checkUser(correo, password)) {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    // Redirigir a la pantalla principal
                    Intent intent = new Intent(LoginActivity.this, GestionCuenta.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // Captura cualquier excepción y muestra detalles en el Logcat y en un Toast
                Log.e("LoginError", "Error en el inicio de sesión", e);
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
