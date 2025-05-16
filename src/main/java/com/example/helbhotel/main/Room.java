package com.example.helbhotel.main;

public abstract class Room {

    private String floor;
    private int roomNumber;
    protected char roomType; // L B E
    protected String roomTypeFullName;
    protected String roomColor;
    private boolean isAvailable;
    private Reservation reservation;

    public Room(String floor, int roomNumber) {
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getName() {
        return floor + roomNumber + roomType;
    }

    public String getColor() {
        return roomColor;
    }

    public String getRoomType() {
        return String.valueOf(roomType);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

}
