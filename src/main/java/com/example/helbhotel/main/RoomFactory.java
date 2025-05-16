package com.example.helbhotel.main;

public class RoomFactory {

    public Room createRoom(char roomType, String floor, int roomNumber) {
        if (roomType == 'L') {
            return new LuxeRoom(floor,roomNumber);
        } else if (roomType == 'B') {
            return new BusinessRoom(floor,roomNumber);
        } else if (roomType == 'E') {
            return new EconomicRoom(floor,roomNumber);
        }
        return new EmptySpace(floor);
    }

}
