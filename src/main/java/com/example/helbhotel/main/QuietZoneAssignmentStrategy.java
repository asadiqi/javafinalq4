/*package com.example.helbhotel.strategy;

import com.example.helbhotel.a.Hotel;
import com.example.helbhotel.a.Room;
import com.example.helbhotel.parser.Reservation;

import com.example.helbhotel.a.Room;
import com.example.helbhotel.parser.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public class QuietZoneAssignmentStrategy implements RoomAssignmentStrategy {
    @Override
    public Room assignRoom(Hotel hotel, Reservation reservation) {
       /* List<Room> filtered = availableRooms.stream()
                .filter(room -> {
                    // Si le client a des enfants et cherche une chambre calme, on l'évite
                    if (reservation.nombreEnfants > 0 && !roomHasNonChildrenNearby(room)) return false;
                    // Si le client est fumeur et cherche une chambre non-fumeur, on l'évite
                    if (reservation.fumeur && !isWindowRoom(room)) return false;
                    return true;
                })
              .collect(Collectors.toList());



        return filtered.isEmpty() ? null : filtered.get(0); // ou random



        return  null;
    }

    private boolean roomHasNonChildrenNearby(Room room) {
        // TODO: Logique sur les chambres adjacentes si nécessaire
        return false;
    }

    private boolean isWindowRoom(Room room) {
        int number = room.getRoomNumber();
        return number % 4 == 1 || number % 4 == 0; // Exemples : début ou fin de ligne
    }
}

 */

