package com.example.helbhotel.main;

public class BusinessRoom extends Room {


    public BusinessRoom(String floor, int roomNumber) {
        super(floor, roomNumber);
        this.roomColor="#BFDFFF";
        this.roomTypeFullName = "Business";
        this.roomType='B';
    }
}

