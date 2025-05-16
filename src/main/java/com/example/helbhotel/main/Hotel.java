package com.example.helbhotel.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hotel {

    private int amountOfFloors;
    private List<Room[][]> building = new ArrayList<Room[][]>();
    private Room[][] floor = new Room[4][4];
    private HConfigParser configParser;
    private ReservationParser reservationParser;
    private static final String HCONFIG_FILE_PATH = "C:\\Users\\sadiq\\Desktop\\Cours_Q4\\Java\\Final\\HELBHotel\\src\\main\\java\\com\\example\\helbhotel\\main\\hconfig";
    private static final String CSV_FILE_PATH = "C:\\Users\\sadiq\\Desktop\\Cours_Q4\\Java\\Final\\HELBHotel\\src\\main\\java\\com\\example\\helbhotel\\main\\reservation.csv";

    private List<Reservation> reservations = new ArrayList<>();
    private List<RoomAssignmentSuggestion> roomAssignmentSuggestions = new ArrayList<>();

    public Hotel() {
        configParser = new HConfigParser(HCONFIG_FILE_PATH);
        reservationParser = new ReservationParser(CSV_FILE_PATH);
        amountOfFloors = configParser.getNombreEtages();
        List<List<String>> floorData = configParser.getChambreConfig();

        for (int floorNumber = 0; floorNumber < amountOfFloors; floorNumber++) {
            this.floor = new Room[4][4];
            int roomNumber = 1;
            for (int i = 0; i < floorData.size(); i++) {
                for (int j = 0; j < floorData.get(i).size(); j++) {
                    String roomChar = floorData.get(i).get(j);
                    this.floor[i][j] = new RoomFactory().createRoom(roomChar.charAt(0),
                            getFloorLabel(floorNumber),
                            roomNumber);

                    if (roomChar.equals("Z"))
                        continue;
                    roomNumber++;

                }
            }
            building.add(this.floor);
        }

        while (reservationParser.hasNextRequest()) {
            this.reservations.add(reservationParser.getNextReservationRequest());
        }
    }

    public void handleReservation(RoomAssignmentSuggestion roomAssignmentSuggestion, int indexSuggestion) {

        reservationParser.deleteLineInFile(CSV_FILE_PATH,
                roomAssignmentSuggestion.getReservation().setToCsvLineFormat());

        for (int floorNumber = 0; floorNumber < this.building.size(); floorNumber++) {
            Room[][] floorData = this.building.get(floorNumber);
            for (int i = 0; i < floorData.length; i++) {
                for (int j = 0; j < floorData[i].length; j++) {
                    Room room = floorData[i][j];
                    if (room.getRoomType().equals("Z"))
                        continue;

                    if (floorData[i][j].getName().equals(roomAssignmentSuggestion.getRoom().getName())) {
                        floorData[i][j].setAvailable(false);
                        floorData[i][j].setReservation(roomAssignmentSuggestion.getReservation());
                        break;
                    }
                }
            }
        }

        roomAssignmentSuggestions.remove(indexSuggestion);

    }

    public void freeRoom(Room roomToFree) {
        for (int floorNumber = 0; floorNumber < this.building.size(); floorNumber++) {
            Room[][] floorData = this.building.get(floorNumber);
            for (int i = 0; i < floorData.length; i++) {
                for (int j = 0; j < floorData[i].length; j++) {
                    Room room = floorData[i][j];
                    if (room.getRoomType().equals("Z"))
                        continue;

                    if (floorData[i][j].getName().equals(roomToFree.getName())) {
                        floorData[i][j].setAvailable(true);
                        floorData[i][j].setReservation(null);
                        break;
                    }
                }
            }
        }
    }

    public List<String[]> getRoomsInformation() {
        List<String[]> roomInformation = new ArrayList<>();

        // Create some string arrays with 2 places each
        String[] luxeRoom = new String[2];
        luxeRoom[0] = "Luxe";
        luxeRoom[1] = "#D8C4EC";

        String[] economicRoom = new String[2];
        economicRoom[0] = "Economic";
        economicRoom[1] = "#FFE5B4";

        String[] businessRoom = new String[2];
        businessRoom[0] = "Business";
        businessRoom[1] = "#BFDFFF";

        // Add the arrays to the list
        roomInformation.add(luxeRoom);
        roomInformation.add(economicRoom);
        roomInformation.add(businessRoom);

        return roomInformation;
    }

    public List<Room> getRooms() {
        List<Room> roomList = new ArrayList<Room>();

        for (int floorNumber = 0; floorNumber < this.building.size(); floorNumber++) {
            Room[][] floorData = this.building.get(floorNumber);
            for (int i = 0; i < floorData.length; i++) {
                for (int j = 0; j < floorData[i].length; j++) {
                    Room room = floorData[i][j];
                    if (room.getRoomType().equals("Z"))
                        continue;
                    roomList.add(room);
                }
            }
        }

        return roomList;
    }

    public static String getFloorLabel(int n) {
        StringBuilder sb = new StringBuilder();
        n++;
        while (n > 0) {
            n--;
            sb.insert(0, (char) ('A' + (n % 26)));
            n /= 26;
        }
        return sb.toString();
    }

    public Room[][] getFloor(String floorLabel) {
        int floorNumber = getFloorIndex(floorLabel);
        return building.get(floorNumber);
    }


    public static int getFloorIndex(String label) {
        int result = 0;
        for (int i = 0; i < label.length(); i++) {
            result *= 26;
            result += label.charAt(i) - 'A' + 1;
        }
        return result - 1;
    }

    public int getAmountOfFloors() {
        return configParser.getNombreEtages();
    }

    public boolean areAllRoomsBooked() {
        for (int i = 0; i < building.size(); i++) {
            Room[][] floor = building.get(i);
            for (int j = 0; j < floor.length; j++) {
                for (int k = 0; k < floor[j].length; k++) {
                    if (floor[i][j].getRoomType().equals("Z")) {
                        continue;
                    }

                    if (floor[i][j].isAvailable()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public List<Room> giveRandomAvailableRooms(int count) {
        List<Room> availableRooms = new ArrayList<>();

        for (Room[][] floor : building) {
            for (int i = 0; i < floor.length; i++) {
                for (int j = 0; j < floor[i].length; j++) {
                    if (floor[i][j] != null && floor[i][j].isAvailable()) {
                        availableRooms.add(floor[i][j]);
                    }
                }
            }
        }

        if (availableRooms.size() <= count) {
            return availableRooms;
        }

        List<Room> result = new ArrayList<>();
        Random random = new Random();

        while (result.size() < count && !availableRooms.isEmpty()) {
            int randomIndex = random.nextInt(availableRooms.size());
            result.add(availableRooms.remove(randomIndex));
        }

        return result;

    }

    public List<Room[][]> getBuilding() {
        return building;
    }

    public List<Reservation> getReservations() {
        while (reservationParser.hasNextRequest()) {
            this.reservations.add(reservationParser.getNextReservationRequest());
        }
        return reservations;
    }

    public List<RoomAssignmentSuggestion> getSuggestions() {
        return this.roomAssignmentSuggestions;
    }

    public void setRoomAssignmentSuggestions(List<RoomAssignmentSuggestion> roomAssignmentSuggestions) {
        this.roomAssignmentSuggestions = roomAssignmentSuggestions;
    }

}