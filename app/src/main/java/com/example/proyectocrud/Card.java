package com.example.proyectocrud;

public class Card {
    private String cardNumber;
    private String cardType;
    private String expirationDate;
    private String cardStatus;

    public Card(String cardNumber, String cardType, String expirationDate, String cardStatus) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expirationDate = expirationDate;
        this.cardStatus = cardStatus;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCardStatus() {
        return cardStatus;
    }
}
