package com.example.helbhotel.main;

public class BusinessRoom extends Room {


    private final String COLOR = "#BFDFFF";
    private final String FULL_NAME = "Business";
    private final char TYPE_CODE = 'B';


    public BusinessRoom(String floor, int roomNumber) {

        super(floor, roomNumber);
        this.roomColor=COLOR;
        this.roomTypeFullName = FULL_NAME;
        this.roomType=TYPE_CODE;
    }
}

