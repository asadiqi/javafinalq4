package com.example.helbhotel.main;

import java.util.*;

public class HELBHotelController {

    private Hotel hotel;
    private RoomAssignmentContext context;
    private TicketFactory ticketFactory;
    private HashMap<String, Integer> codes;

    public HELBHotelController() {
        this.hotel = new Hotel();
        this.context = new RoomAssignmentContext();
        this.context.setStrategy(new RandomAssignmentStrategy());
        context.suggestRoomsAssigment(hotel);
        sortSuggestionsBy("Name");
        ticketFactory = new TicketFactory();
        codes = new HashMap<>();

    }

    public List<String[]> getRoomsInformation() {
        return hotel.getRoomsInformation();
    }

    public Room[][] getFloor(String floorLabel) {
        return hotel.getFloor(floorLabel);
    }

    public List<String> getFloorNames() {
        int amountOfFloors = hotel.getAmountOfFloors();
        List<String> floorLabels = new ArrayList<>();
        for (int i = 0; i < amountOfFloors; i++) {
            String floorLabel = hotel.getFloorLabel(i);
            floorLabels.add(floorLabel);
        }
        return floorLabels;
    }

    public List<String> getRooms() {
        List<Room> hotelRooms = hotel.getRooms();
        List<String> roomNames = new ArrayList<>();
        for (int i = 0; i < hotelRooms.size(); i++) {
            String name = hotelRooms.get(i).getName();
            roomNames.add(name);
        }
        return roomNames;
    }

    public Room getRoomByName(String roomName) {
        List<Room> hotelRooms = hotel.getRooms();
        Room room = hotelRooms.get(1);
        for (int i = 0; i < hotelRooms.size(); i++) {
            room = hotelRooms.get(i);
            String name = hotelRooms.get(i).getName();
            if (name.equals(roomName)) {
                room = hotelRooms.get(i);
                break;
            }
        }

        return room;
    }

    public Boolean isRoomAvailable(String roomName) {
        List<Room> hotelRooms = hotel.getRooms();

        for (int i = 0; i < hotelRooms.size(); i++) {
            Room room = hotelRooms.get(i);
            String name = room.getName();
            if (name.equals(roomName)) {
                return room.isAvailable();
            }
        }

        return false;
    }

    public void confirmReservation(RoomAssignmentSuggestion roomAssignmentSuggestion, int indexSuggestion) {
        hotel.handleReservation(roomAssignmentSuggestion, indexSuggestion);
    }

    public void freeRoom(Room room) {
        hotel.freeRoom(room);
    }

    public void setAssignmentStrategy(String mode) {
        switch (mode) {
            case "Random Assignment":
                context.setStrategy(new RandomAssignmentStrategy());
                break;
            //case "Quiet Zone":
                // Assure-toi d'implémenter la stratégie QuietZone aussi, sinon utilise Random pour l'instant
                //context.setStrategy(new QuietZoneAssignmentStrategy());
              //  break;
            case "Stay Purpose":
                context.setStrategy(new StayPurposeAssignmentStrategy());
                break;
           // case "Sequential Assignment":
                // context.setStrategy(new SequentialAssignmentStrategy());
               // break;
            default:
                context.setStrategy(new RandomAssignmentStrategy());
        }
        context.suggestRoomsAssigment(hotel);

    }


    public List<String> getStrategyNames() {
        return context.getStrategyNames();
    }

    public List<RoomAssignmentSuggestion> getSuggestions() {
        return hotel.getSuggestions();
    }

    public Ticket getTicket(String roomType, int rate) {
        System.out.println("inside controller");
        System.out.printf("%s, %s", roomType, rate);
        return this.ticketFactory.createTicket(roomType, rate);
    }

    public String generateCode(Ticket ticket) {

        String code = UUID.randomUUID().toString()
                .replaceAll("-", "") // remove -
                .substring(0, 10); // give just the first 10
        codes.put(code, ticket.getDiscount());

        return code;
    }

    public int getDiscount(String code) {
        return this.codes.get(code);
    }

    public void sortSuggestionsBy(String criteria) {
        List<RoomAssignmentSuggestion> currentSuggestions = hotel.getSuggestions();
        switch (criteria) {
            case "Name":
                currentSuggestions.sort(Comparator.comparing(
                        s -> s.getReservation().getFullName().toLowerCase()
                ));
                break;
            case "Roomnumber":
                currentSuggestions.sort(Comparator
                        .comparingInt((RoomAssignmentSuggestion s) -> {
                            String name = s.getRoom().getName();
                            String floorLabel = name.replaceAll("[^A-Za-z]", ""); // ex: "AB12" → "AB"
                            return floorLabelToNumber(floorLabel); // convertit "AB" → 28
                        })
                        .thenComparingInt(s -> {
                            String numberPart = s.getRoom().getName().replaceAll("[^0-9]", "");
                            return numberPart.isEmpty() ? 0 : Integer.parseInt(numberPart); // "12" → 12
                        }));
                break;

        }
    }

    private int floorLabelToNumber(String label) {
        int result = 0;
        for (int i = 0; i < label.length(); i++) {
            char c = label.charAt(i);
            result = result * 26 + (c - 'A' + 1); // A=1, B=2, ..., Z=26
        }
        return result;
    }


}
