package com.example.helbhotel.main;

public class TicketFactory {
    public Ticket createTicket(String roomType, int rate) {
        // Rate
        if (roomType.equals("L") && rate >= 4) {
            return new GoldTicket();
        } else if (roomType.equals("B") && rate >= 4) {
            return new SilverTicket();
        }

        return new BronzeTicket();
    }
}