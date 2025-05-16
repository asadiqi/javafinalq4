package com.example.helbhotel.main;

public class LuxeRoom extends Room {

    private final String COLOR = "#D8C4EC";
    private final String FULL_NAME = "Luxe";
    private final char TYPE_CODE = 'L';

    public LuxeRoom(String floor, int roomNumber) {
        super(floor, roomNumber);
        this.roomColor=COLOR;
        this.roomTypeFullName = FULL_NAME;
        this.roomType=TYPE_CODE;

    }

}

