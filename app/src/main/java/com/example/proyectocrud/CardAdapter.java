package com.example.proyectocrud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView txtCardNumber, txtCardType, txtExpirationDate, txtCardStatus;

        public CardViewHolder(View itemView) {
            super(itemView);
            txtCardNumber = itemView.findViewById(R.id.txtCardNumber);
            txtCardType = itemView.findViewById(R.id.txtCardType);
            txtExpirationDate = itemView.findViewById(R.id.txtExpirationDate);
            txtCardStatus = itemView.findViewById(R.id.txtCardStatus);
        }
    }
}

