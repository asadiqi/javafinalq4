package com.example.helbhotel.main;

import java.util.ArrayList;
import java.util.List;

public class StayPurposeAssignmentStrategy implements RoomAssignmentStrategy {

    private final String TYPE_BUSINESS = "B";
    private final String TYPE_LUXURY = "L";
    private final String TYPE_ECONOMIC = "E";
    private final String STAY_PURPOSE_BUSINESS = "Business";
    private final int FIRST_INDEX = 0;
    private final int Start_INDEX = 0;

    @Override
    public void suggestRoomsAssigment(Hotel hotel) {
        List<RoomAssignmentSuggestion> suggestions = new ArrayList<>();

        List<Reservation> reservations = hotel.getReservations();
        List<Room> availableRooms = hotel.getRooms();

        // Filtrer les chambres disponibles par type
        List<Room> businessRooms = new ArrayList<>();
        List<Room> luxuryRooms = new ArrayList<>();
        List<Room> economicRooms = new ArrayList<>();

        for (Room room : availableRooms) {
            if (!room.isAvailable()) continue;
            switch (room.getRoomType()) {
                case TYPE_BUSINESS:
                    businessRooms.add(room);
                    break;
                case TYPE_LUXURY:
                    luxuryRooms.add(room);
                    break;
                case TYPE_ECONOMIC:
                    economicRooms.add(room);
                    break;
            }
        }


        for (int i = Start_INDEX; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            Room assignedRoom = null;

            String stayPurpose = reservation.getStayPurpose();
            boolean isSmoker = reservation.isSmoker();
            boolean hasChildren = reservation.hasChildren();

            if (STAY_PURPOSE_BUSINESS.equalsIgnoreCase(stayPurpose)) {
                // Si motif Affaire -> Business
                if (!businessRooms.isEmpty()) {
                    assignedRoom = businessRooms.remove(FIRST_INDEX);
                }
            } else {
                // Tourisme ou Autre
                if (!isSmoker && !hasChildren) {
                    // chambre Luxe
                    if (!luxuryRooms.isEmpty()) {
                        assignedRoom = luxuryRooms.remove(FIRST_INDEX);
                    }
                } else {
                    // chambre Économique
                    if (!economicRooms.isEmpty()) {
                        assignedRoom = economicRooms.remove(FIRST_INDEX);
                    }
                }
            }

            if (assignedRoom != null) {
                suggestions.add(new RoomAssignmentSuggestion(assignedRoom, reservation));
            }
        }
        hotel.setRoomAssignmentSuggestions(suggestions);
    }
}
