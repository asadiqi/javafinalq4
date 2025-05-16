package com.example.helbhotel.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAssignmentStrategy implements RoomAssignmentStrategy {

    private final int START_INDEX = 0;
    private final String ROOM_TYPE_EXCLUDED = "Z";

    @Override
    public void suggestRoomsAssigment(Hotel hotel) {
        List<RoomAssignmentSuggestion> roomAssignmentSuggestions = new ArrayList<>();
        List<Room> availableRooms = new ArrayList<>();

        // First, collect all available rooms
        for (Room[][] floor : hotel.getBuilding()) {
            for (int i = START_INDEX; i < floor.length; i++) {
                for (int j = START_INDEX; j < floor[i].length; j++) {
                    if (!floor[i][j].getRoomType().equals(ROOM_TYPE_EXCLUDED) && floor[i][j].isAvailable()) {
                        availableRooms.add(floor[i][j]);
                    }
                }
            }
        }

        List<Room> result = new ArrayList<>();
        Random random = new Random();

        while (!availableRooms.isEmpty()) {
            int randomIndex = random.nextInt(availableRooms.size());
            result.add(availableRooms.remove(randomIndex));
        }

        for (int i =START_INDEX; i < hotel.getReservations().size(); i++) {
            RoomAssignmentSuggestion suggestion = new RoomAssignmentSuggestion(result.get(i),
                    hotel.getReservations().get(i));
            roomAssignmentSuggestions.add(suggestion);
        }

        hotel.setRoomAssignmentSuggestions(roomAssignmentSuggestions);

    }

}