package com.example.helbhotel.main;

import java.util.ArrayList;
import java.util.List;

public class SequentialAssignmentStrategy implements RoomAssignmentStrategy {

    private final String TYPE_BUSINESS = "B";
    private final String TYPE_LUXURY = "L";
    private final String TYPE_ECONOMIC = "E";
    private final String STAY_PURPOSE_BUSINESS = "Business";

    @Override
    public void suggestRoomsAssigment(Hotel hotel) {
        List<RoomAssignmentSuggestion> suggestions = new ArrayList<>();
        List<Reservation> reservations = hotel.getReservations();
        List<Room[][]> building = hotel.getBuilding();

        // Parcours des réservations dans l'ordre
        for (Reservation reservation : reservations) {
            Room assignedRoom = null;
            String stayPurpose = reservation.getStayPurpose();
            boolean isSmoker = reservation.isSmoker();
            boolean hasChildren = reservation.hasChildren();

            // Parcours des étages dans l'ordre (du 1er étage)
            for (Room[][] floor : building) {
                boolean foundRoomOnFloor = false;
                // Parcours des chambres de la matrice (ex: 4x4)
                for (int i = 0; i < floor.length; i++) {
                    for (int j = 0; j < floor[i].length; j++) {
                        Room room = floor[i][j];

                        // Ignorer chambres non dispo ou type "Z"
                        if (room == null || !room.isAvailable() || room.getRoomType().equals("Z")) {
                            continue;
                        }

                        // Logique selon Stay Purpose et caractéristiques client
                        if (STAY_PURPOSE_BUSINESS.equalsIgnoreCase(stayPurpose)) {
                            if (room.getRoomType().equals(TYPE_BUSINESS)) {
                                assignedRoom = room;
                                foundRoomOnFloor = true;
                                break;
                            }
                        } else {
                            if (!isSmoker && !hasChildren) {
                                if (room.getRoomType().equals(TYPE_LUXURY)) {
                                    assignedRoom = room;
                                    foundRoomOnFloor = true;
                                    break;
                                }
                            } else {
                                if (room.getRoomType().equals(TYPE_ECONOMIC)) {
                                    assignedRoom = room;
                                    foundRoomOnFloor = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (foundRoomOnFloor) break;
                }
                if (assignedRoom != null) break; // chambre trouvée, on sort du parcours des étages
            }

            if (assignedRoom != null) {
                suggestions.add(new RoomAssignmentSuggestion(assignedRoom, reservation));
                assignedRoom.setAvailable(false); // bloquer cette chambre pour ne pas la réassigner
            }
        }

        hotel.setRoomAssignmentSuggestions(suggestions);
    }
}
