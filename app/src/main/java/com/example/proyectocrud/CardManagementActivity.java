package com.example.proyectocrud;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CardManagementActivity extends AppCompatActivity {
    private EditText edtCardNumber, edtCardType, edtExpirationDate;
    private Button btnAddCard;
    private DatabaseHelper dbHelper;
    private int userId; // Asegúrate de obtener el ID del usuario desde la sesión o la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_management);

        // Inicializar vistas
        edtCardNumber = findViewById(R.id.edtCardNumber);
        edtCardType = findViewById(R.id.edtCardType);
        edtExpirationDate = findViewById(R.id.edtExpirationDate);
        btnAddCard = findViewById(R.id.btnAddCard);

        // Inicializar la base de datos
        dbHelper = new DatabaseHelper(this);

        // Obtener el ID del usuario (esto puede variar según tu implementación)
        userId = 1; // Este es un ejemplo, obtén el ID del usuario de la sesión o la base de datos

        // Agregar tarjeta
        btnAddCard.setOnClickListener(v -> {
            String cardNumber = edtCardNumber.getText().toString();
            String cardType = edtCardType.getText().toString();
            String expirationDate = edtExpirationDate.getText().toString();

            if (!cardNumber.isEmpty() && !cardType.isEmpty() && !expirationDate.isEmpty()) {
                boolean isInserted = dbHelper.insertCard(userId, cardNumber, cardType, expirationDate, "Activa");
                if (isInserted) {
                    Toast.makeText(CardManagementActivity.this, "Tarjeta agregada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CardManagementActivity.this, "Error al agregar la tarjeta", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CardManagementActivity.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}