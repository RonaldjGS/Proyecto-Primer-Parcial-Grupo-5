package com.example.proyectocrud;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private Context context;
    private List<Card> cardList;
    private DatabaseHelper dbHelper;

    public CardAdapter(Context context, List<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
        this.dbHelper = new DatabaseHelper(context); // Inicializar DatabaseHelper

    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.txtCardNumber.setText(card.getCardNumber());
        holder.txtCardType.setText(card.getCardType());
        holder.txtExpirationDate.setText(card.getExpirationDate());
        holder.txtCardStatus.setText(card.getCardStatus());


        // Configurar botón de eliminar
        holder.btnDeleteCard.setOnClickListener(view -> {
            String cardNumber = card.getCardNumber(); // Obtén el número desde el objeto Card
            boolean isDeleted = dbHelper.deleteCardByNumber(cardNumber);
            if (isDeleted) {
                Toast.makeText(view.getContext(), "Tarjeta eliminada", Toast.LENGTH_SHORT).show();
                // Actualiza la lista
                int adapterPosition = holder.getAdapterPosition(); // Obtén la posición del holder
                cardList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
            } else {
                Toast.makeText(view.getContext(), "Error al eliminar la tarjeta", Toast.LENGTH_SHORT).show();
            }
        });
        // Configurar botón de actualizar
        holder.btnUpdateCard.setOnClickListener(view -> {
            // Crea un EditText para ingresar el nuevo estado
            final EditText input = new EditText(view.getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT); // Puedes cambiar el tipo según lo que quieras editar

            // Crea el diálogo de actualización
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Actualizar Estado de la Tarjeta")
                    .setMessage("Ingrese el nuevo estado:")
                    .setView(input) // Muestra el campo de texto
                    .setPositiveButton("Actualizar", (dialog, which) -> {
                        String newStatus = input.getText().toString();
                        if (!newStatus.isEmpty()) {
                            // Actualizar el estado en la base de datos
                            boolean isUpdated = dbHelper.updateCardStatus(card.getCardId(), newStatus);
                            if (isUpdated) {
                                Toast.makeText(view.getContext(), "Estado de la tarjeta actualizado", Toast.LENGTH_SHORT).show();
                                card.setCardStatus(newStatus); // Actualiza el estado en el modelo
                                notifyItemChanged(position); // Notifica al adaptador que se actualizó el elemento
                            } else {
                                Toast.makeText(view.getContext(), "Error al actualizar el estado", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(view.getContext(), "Por favor ingrese un estado válido", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancelar", null) // No hacer nada si se cancela
                    .create()
                    .show();
        });


    }


    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView txtCardNumber, txtCardType, txtExpirationDate, txtCardStatus;
        Button btnDeleteCard, btnUpdateCard; // Agrega btnUpdateCard aquí

        public CardViewHolder(View itemView) {
            super(itemView);
            txtCardNumber = itemView.findViewById(R.id.txtCardNumber);
            txtCardType = itemView.findViewById(R.id.txtCardType);
            txtExpirationDate = itemView.findViewById(R.id.txtExpirationDate);
            txtCardStatus = itemView.findViewById(R.id.txtCardStatus);
            btnDeleteCard = itemView.findViewById(R.id.btnDeleteCard);
            btnUpdateCard = itemView.findViewById(R.id.btnUpdateCard); // Asegúrate de que esta línea esté aquí
        }
    }


}
