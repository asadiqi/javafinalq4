package com.example.helbhotel.main;

public class RoomAssignment {
    private Reservation reservation;
    private Room room;

    public RoomAssignment(Reservation reservation, Room room) {
        this.reservation = reservation;
        this.room = room;
    }
}