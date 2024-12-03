package com.example.proyectocrud;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CardManagementActivity extends AppCompatActivity {

    private EditText edtCardNumber, edtCardType, edtExpirationDate;
    private Button btnAddCard;
    private LinearLayout linearLayoutCards;
    private DatabaseHelper dbHelper;
    private int userId; // ID del usuario (debe obtenerse de la sesión o base de datos)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_management);

        // Inicializar vistas
        edtCardNumber = findViewById(R.id.edtCardNumber);
        edtCardType = findViewById(R.id.edtCardType);
        edtExpirationDate = findViewById(R.id.edtExpirationDate);
        btnAddCard = findViewById(R.id.btnAddCard);
        linearLayoutCards = findViewById(R.id.linearLayoutCards);

        // Inicializar la base de datos
        dbHelper = new DatabaseHelper(this);

        // Obtener el ID del usuario (esto depende de tu implementación)
        userId = 1; // Ejemplo estático

        // Mostrar tarjetas registradas al cargar la actividad
        mostrarTarjetas();

        // Agregar tarjeta
        btnAddCard.setOnClickListener(v -> {
            String cardNumber = edtCardNumber.getText().toString();
            String cardType = edtCardType.getText().toString();
            String expirationDate = edtExpirationDate.getText().toString();

            if (!cardNumber.isEmpty() && !cardType.isEmpty() && !expirationDate.isEmpty()) {
                boolean isInserted = dbHelper.insertCard(userId, cardNumber, cardType, expirationDate, "Activa");
                if (isInserted) {
                    Toast.makeText(CardManagementActivity.this, "Tarjeta agregada correctamente", Toast.LENGTH_SHORT).show();
                    edtCardNumber.setText("");
                    edtCardType.setText("");
                    edtExpirationDate.setText("");
                    mostrarTarjetas(); // Actualizar la lista de tarjetas
                } else {
                    Toast.makeText(CardManagementActivity.this, "Error al agregar la tarjeta", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CardManagementActivity.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mostrarTarjetas() {
        linearLayoutCards.removeAllViews(); // Limpia la vista para evitar duplicados

        Cursor cursor = dbHelper.getUserCards(userId);
        if (cursor != null && cursor.getCount() > 0) {
            int cardIdIndex = cursor.getColumnIndex("card_id");
            int cardNumberIndex = cursor.getColumnIndex("card_number");
            int cardTypeIndex = cursor.getColumnIndex("card_type");
            int expirationDateIndex = cursor.getColumnIndex("expiration_date");
            int cardStatusIndex = cursor.getColumnIndex("card_status");

            if (cardIdIndex == -1 || cardNumberIndex == -1 || cardTypeIndex == -1 ||
                    expirationDateIndex == -1 || cardStatusIndex == -1) {
                Toast.makeText(this, "Error: Columnas no encontradas.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Iterar sobre las tarjetas y mostrar sus detalles
            while (cursor.moveToNext()) {
                int cardId = cursor.getInt(cardIdIndex);
                String cardNumber = cursor.getString(cardNumberIndex);
                String cardType = cursor.getString(cardTypeIndex);
                String expirationDate = cursor.getString(expirationDateIndex);
                String cardStatus = cursor.getString(cardStatusIndex);

                // Crear un contenedor vertical para cada tarjeta
                LinearLayout cardLayout = new LinearLayout(this);
                cardLayout.setOrientation(LinearLayout.VERTICAL);
                cardLayout.setPadding(16, 16, 16, 16);
                cardLayout.setBackgroundColor(Color.WHITE);  // Fondo gris claro
                cardLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                // Mostrar la información de la tarjeta
                TextView cardInfo = new TextView(this);
                cardInfo.setText(
                        "Número: " + cardNumber + "\n" +
                                "Tipo: " + cardType + "\n" +
                                "Expiración: " + expirationDate + "\n" +
                                "Estado: " + cardStatus
                );
                cardInfo.setTextSize(16);
                cardInfo.setTextColor(Color.BLACK); // Texto negro
                cardLayout.addView(cardInfo);

                // Botón para eliminar la tarjeta
                Button deleteButton = new Button(this);
                deleteButton.setText("Eliminar");
                deleteButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6200EE"))); // Fondo púrpura
                deleteButton.setTextColor(Color.WHITE); // Texto blanco
                deleteButton.setTextSize(12); // Tamaño de texto más pequeño

                // Ajustar tamaño del botón
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                buttonParams.setMargins(0, 8, 0, 0); // Márgenes del botón
                deleteButton.setLayoutParams(buttonParams);

                deleteButton.setOnClickListener(v -> {
                    boolean isDeleted = dbHelper.deleteCard(cardId); // Eliminar la tarjeta
                    if (isDeleted) {
                        Toast.makeText(this, "Tarjeta eliminada.", Toast.LENGTH_SHORT).show();
                        mostrarTarjetas(); // Actualizar la lista
                    } else {
                        Toast.makeText(this, "Error al eliminar la tarjeta.", Toast.LENGTH_SHORT).show();
                    }
                });

                cardLayout.addView(deleteButton);

                // Agregar el contenedor de la tarjeta al `LinearLayout`
                linearLayoutCards.addView(cardLayout);
            }
            cursor.close();
        } else {
            // Mostrar un mensaje si no hay tarjetas
            TextView noCardsMessage = new TextView(this);
            noCardsMessage.setText("No hay tarjetas registradas.");
            noCardsMessage.setTextSize(18);
            noCardsMessage.setGravity(Gravity.CENTER);
            linearLayoutCards.addView(noCardsMessage);
        }
    }

}
