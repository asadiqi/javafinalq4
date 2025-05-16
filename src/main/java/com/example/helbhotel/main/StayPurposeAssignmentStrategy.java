package com.example.helbhotel.main;

import com.example.helbhotel.main.Hotel;
import com.example.helbhotel.main.Reservation;
import com.example.helbhotel.main.Room;
import com.example.helbhotel.main.RoomAssignmentSuggestion;

import java.util.ArrayList;
import java.util.List;

public class StayPurposeAssignmentStrategy implements RoomAssignmentStrategy {

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
                case "Business":
                    businessRooms.add(room);
                    break;
                case "Luxe":
                    luxuryRooms.add(room);
                    break;
                case "Economic":
                    economicRooms.add(room);
                    break;
            }
        }

        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            Room assignedRoom = null;

            String stayPurpose = reservation.getStayPurpose();
            boolean isSmoker = reservation.isSmoker();
            boolean hasChildren = reservation.hasChildren();

            if ("Affaire".equalsIgnoreCase(stayPurpose)) {
                // Si motif Affaire -> Business
                if (!businessRooms.isEmpty()) {
                    assignedRoom = businessRooms.remove(0);
                }
            } else {
                // Tourisme ou Autre
                if (!isSmoker && !hasChildren) {
                    // chambre Luxe
                    if (!luxuryRooms.isEmpty()) {
                        assignedRoom = luxuryRooms.remove(0);
                    }
                } else {
                    // chambre Économique
                    if (!economicRooms.isEmpty()) {
                        assignedRoom = economicRooms.remove(0);
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
