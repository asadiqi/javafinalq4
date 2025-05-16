package com.example.helbhotel.main;

public class BronzeTicket extends Ticket {
    private static final String TYPE = "bronze";
    private static final int PRIX = 25;

    public BronzeTicket() {
        super(TYPE, PRIX);
    }

}