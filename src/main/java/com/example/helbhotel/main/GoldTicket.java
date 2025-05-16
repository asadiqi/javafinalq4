package com.example.helbhotel.main;

public class GoldTicket extends Ticket {

    private static final String TYPE = "gold";
    private static final int PRIX = 100;

    public GoldTicket() {
        super(TYPE, PRIX);
    }
}