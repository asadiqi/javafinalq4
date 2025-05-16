package com.example.helbhotel.main;

public class EconomicRoom extends Room {

    private final String COLOR = "#FFE5B4";
    private final String FULL_NAME = "Economic";
    private final char TYPE_CODE = 'E';

    public EconomicRoom(String floor, int roomNumber) {
        super(floor, roomNumber);
        this.roomColor=COLOR;
        this.roomTypeFullName = FULL_NAME;
        this.roomType=TYPE_CODE;

    }
}
