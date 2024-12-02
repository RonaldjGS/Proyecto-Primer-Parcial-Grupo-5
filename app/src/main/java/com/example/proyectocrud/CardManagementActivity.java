package com.example.proyectocrud;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CardManagementActivity extends AppCompatActivity {
    private EditText edtCardNumber, edtCardType, edtExpirationDate;
    private Button btnAddCard;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<Card> cardList;
    private DatabaseHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_management);

        // Inicializar vistas
        edtCardNumber = findViewById(R.id.edtCardNumber);
        edtCardType = findViewById(R.id.edtCardType);
        edtExpirationDate = findViewById(R.id.edtExpirationDate);
        btnAddCard = findViewById(R.id.btnAddCard);
        recyclerView = findViewById(R.id.recyclerViewCards);

        // Inicializar la base de datos
        dbHelper = new DatabaseHelper(this);

        // Obtener el ID del usuario (esto puede variar según tu implementación)
        userId = 1; // Este es un ejemplo, obtén el ID del usuario de la sesión o la base de datos

        // Inicializar la lista de tarjetas
        cardList = new ArrayList<>();
        cardAdapter = new CardAdapter(this, cardList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cardAdapter);

        // Cargar las tarjetas desde la base de datos
        loadCards();

        // Agregar tarjeta
        btnAddCard.setOnClickListener(v -> {
            String cardNumber = edtCardNumber.getText().toString();
            String cardType = edtCardType.getText().toString();
            String expirationDate = edtExpirationDate.getText().toString();

            if (!cardNumber.isEmpty() && !cardType.isEmpty() && !expirationDate.isEmpty()) {
                boolean isInserted = dbHelper.insertCard(userId, cardNumber, cardType, expirationDate, "Activa");
                if (isInserted) {
                    Toast.makeText(CardManagementActivity.this, "Tarjeta agregada correctamente", Toast.LENGTH_SHORT).show();
                    loadCards(); // Cargar las tarjetas actualizadas
                } else {
                    Toast.makeText(CardManagementActivity.this, "Error al agregar la tarjeta", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CardManagementActivity.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para cargar las tarjetas desde la base de datos
    private void loadCards() {
        cardList.clear(); // Limpiar la lista actual
        Cursor cursor = dbHelper.getUserCards(userId);
        if (cursor != null && cursor.moveToFirst()) {
            int cardNumberIndex = cursor.getColumnIndex(DatabaseHelper.COL_CARD_NUMBER);
            int cardTypeIndex = cursor.getColumnIndex(DatabaseHelper.COL_CARD_TYPE);
            int expirationDateIndex = cursor.getColumnIndex(DatabaseHelper.COL_EXPIRATION_DATE);
            int cardStatusIndex = cursor.getColumnIndex(DatabaseHelper.COL_CARD_STATUS);

            // Verificar si los índices son válidos
            if (cardNumberIndex != -1 && cardTypeIndex != -1 && expirationDateIndex != -1 && cardStatusIndex != -1) {
                do {
                    String cardNumber = cursor.getString(cardNumberIndex);
                    String cardType = cursor.getString(cardTypeIndex);
                    String expirationDate = cursor.getString(expirationDateIndex);
                    String cardStatus = cursor.getString(cardStatusIndex);

                    cardList.add(new Card(cardNumber, cardType, expirationDate, cardStatus));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        cardAdapter.notifyDataSetChanged(); // Notificar al adaptador para actualizar la vista
    }
}