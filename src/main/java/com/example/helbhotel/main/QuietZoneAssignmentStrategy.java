package com.example.helbhotel.main;

import java.util.ArrayList;
import java.util.List;

public class QuietZoneAssignmentStrategy implements RoomAssignmentStrategy {

    @Override
    public void suggestRoomsAssigment(Hotel hotel) {
        List<RoomAssignmentSuggestion> suggestions = new ArrayList<>();
        List<Reservation> reservations = hotel.getReservations();

        // Récupère toutes les chambres disponibles
        List<Room> availableRooms = hotel.getRooms();

        // On récupère la structure du bâtiment
        List<Room[][]> building = hotel.getBuilding();

        for (Reservation reservation : reservations) {
            Room assignedRoom = null;

            // Parcours toutes les chambres disponibles et essaie de trouver une chambre adaptée
            for (Room room : availableRooms) {
                if (!room.isAvailable()) continue;

                // Trouve la position de la chambre dans le building (floor, i, j)
                Position pos = findRoomPosition(building, room);
                if (pos == null) continue;

                boolean isOnEdge = isOnBuildingEdge(pos, building.get(pos.floor).length, building.get(pos.floor)[0].length);

                // Conditions pour fumeurs : doit être en bordure (avec fenêtre)
                if (reservation.isSmoker() && !isOnEdge) {
                    continue;
                }

                // Conditions pour clients avec enfants : ne pas être à côté d’une chambre sans enfants
                if (reservation.hasChildren()) {
                    if (isNextToRoomWithoutChildren(building.get(pos.floor), pos.i, pos.j)) {
                        continue;
                    }
                }

                // Si on arrive ici, la chambre convient
                assignedRoom = room;
                break;
            }

            if (assignedRoom != null) {
                suggestions.add(new RoomAssignmentSuggestion(assignedRoom, reservation));
                availableRooms.remove(assignedRoom);  // Ne plus la proposer aux autres
            }
        }

        hotel.setRoomAssignmentSuggestions(suggestions);
    }

    // Méthode pour trouver la position de la chambre dans le bâtiment
    private Position findRoomPosition(List<Room[][]> building, Room room) {
        for (int f = 0; f < building.size(); f++) {
            Room[][] floor = building.get(f);
            for (int i = 0; i < floor.length; i++) {
                for (int j = 0; j < floor[i].length; j++) {
                    if (floor[i][j] == null) continue;
                    if (floor[i][j].equals(room)) {
                        return new Position(f, i, j);
                    }
                }
            }
        }
        return null;
    }

    // Vérifie si la chambre est sur le bord du bâtiment (première/dernière ligne ou colonne)
    private boolean isOnBuildingEdge(Position pos, int maxRows, int maxCols) {
        return pos.i == 0 || pos.i == maxRows - 1 || pos.j == 0 || pos.j == maxCols - 1;
    }

    // Vérifie si une chambre est adjacente à une chambre sans enfants
    private boolean isNextToRoomWithoutChildren(Room[][] floor, int i, int j) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int d = 0; d < 4; d++) {
            int ni = i + dx[d];
            int nj = j + dy[d];

            if (ni >= 0 && ni < floor.length && nj >= 0 && nj < floor[0].length) {
                Room neighbor = floor[ni][nj];
                if (neighbor != null && neighbor.getReservation() != null) {
                    if (!neighbor.getReservation().hasChildren()) {
                        return true; // Voisin sans enfants trouvé
                    }
                }
            }
        }
        return false;
    }

    // Classe interne pour garder position floor, ligne, colonne
    private static class Position {
        int floor, i, j;

        Position(int floor, int i, int j) {
            this.floor = floor;
            this.i = i;
            this.j = j;
        }
    }
}
