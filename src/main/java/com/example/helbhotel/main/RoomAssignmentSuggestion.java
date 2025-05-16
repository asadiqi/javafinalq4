package com.example.helbhotel.main;

public class RoomAssignmentSuggestion {
    private Reservation reservation;
    private Room room;

    public RoomAssignmentSuggestion(Room room, Reservation reservation) {
        this.reservation = reservation;
        this.room = room;
    }

    public Reservation getReservation() {
        return reservation;

    }

    public Room getRoom() {
        return room;
    }


}