package com.example.helbhotel.main;

public abstract class Ticket {

    protected String ticketColor;
    protected int discount;

    public Ticket(String ticketColor, int discount) {
        this.ticketColor = ticketColor;
        this.discount = discount;
    }

    public String getTicketColor() {
        return this.ticketColor;
    }

    public int getDiscount() {
        return this.discount;
    }
}