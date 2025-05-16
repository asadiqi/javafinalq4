package com.example.helbhotel.main;


public class EmptySpace extends Room {

    private static final int INVALID_ROOM_NUMBER = -1;
    private static final char TYPE_CODE = 'Z';

    public EmptySpace(String floor) {
        super(floor,INVALID_ROOM_NUMBER );
        this.roomType = TYPE_CODE;
    }
}
