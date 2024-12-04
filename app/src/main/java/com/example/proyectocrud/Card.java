package com.example.proyectocrud;

public class Card {
    private int cardId; // Identificador único de la tarjeta
    private String cardNumber;
    private String cardType;
    private String expirationDate;
    private String cardStatus;

    public Card(int cardId, String cardNumber, String cardType, String expirationDate, String cardStatus) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expirationDate = expirationDate;
        this.cardStatus = cardStatus;
    }

    // Getter y Setter para cardId
    public int getCardId() {
        return cardId;
    }

    // Getter y Setter para cardStatus
    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus; // Asigna el nuevo estado a la tarjeta
    }

    // Otros getters
    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}

