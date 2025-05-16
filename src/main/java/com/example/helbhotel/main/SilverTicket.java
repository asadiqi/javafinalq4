package com.example.helbhotel.main;

public class SilverTicket extends Ticket {

    private static final String TYPE = "silver";
    private static final int PRIX = 50;

    public SilverTicket() {
        super(TYPE, PRIX);
    }

}