package com.example.helbhotel.main;

public class TicketFactory {

    private final String LUXE_ROOM_TYPE = "L";
    private final String BUSINESS_ROOM_TYPE = "B";
    private final int RATE_THRESHOLD = 4;

    public Ticket createTicket(String roomType, int rate) {

        if (roomType.equals(LUXE_ROOM_TYPE) && rate >= RATE_THRESHOLD) {
            return new GoldTicket();
        } else if (roomType.equals(BUSINESS_ROOM_TYPE) && rate >= RATE_THRESHOLD) {
            return new SilverTicket();
        }

        return new BronzeTicket();
    }
}