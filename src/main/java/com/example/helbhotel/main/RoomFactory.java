package com.example.helbhotel.main;

public class RoomFactory {

    private final char LUXE_ROOM = 'L';
    private final char BUSINESS_ROOM = 'B';
    private final char ECONOMIC_ROOM = 'E';

    public Room createRoom(char roomType, String floor, int roomNumber) {
        if (roomType == LUXE_ROOM) {
            return new LuxeRoom(floor,roomNumber);
        } else if (roomType == BUSINESS_ROOM) {
            return new BusinessRoom(floor,roomNumber);
        } else if (roomType == ECONOMIC_ROOM) {
            return new EconomicRoom(floor,roomNumber);
        }
        return new EmptySpace(floor);
    }

}
