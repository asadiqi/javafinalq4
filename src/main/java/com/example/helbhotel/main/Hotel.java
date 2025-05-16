package com.example.helbhotel.main;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private  final int FLOOR_ROWS = 4;
    private  final int FLOOR_COLUMNS = 4;
    private static final String EMPTY_ROOM_TYPE = "Z";
    private static final int ROOM_INFO_ARRAY_SIZE = 2;
    private static final String LUXE_LABEL = "Luxe";
    private static final String LUXE_COLOR = "#D8C4EC";
    private static final String ECONOMIC_LABEL = "Economic";
    private static final String ECONOMIC_COLOR = "#FFE5B4";
    private static final String BUSINESS_LABEL = "Business";
    private static final String BUSINESS_COLOR = "#BFDFFF";
    private static final int LABEL_INDEX = 0;
    private static final int COLOR_INDEX = 1;
    private static final int INITIAL_ROOM_NUMBER = 1;
    private static final int INITIAL_FLOOR_NUMBER = 0;
    private static final int ALPHABET_LENGTH = 26;
    private static final char FIRST_LETTER = 'A';
    private static final int ALPHABET_START_OFFSET = 1;
    private static final int FIRST_CHAR_INDEX = 0;
    private static final int INITIAL_INDEX = 0;
    private final int amountOfFloors;
    private final List<Room[][]> building = new ArrayList<Room[][]>();
    private Room[][] floor = new Room[FLOOR_ROWS][FLOOR_COLUMNS];
    private HConfigParser configParser;
    private ReservationParser reservationParser;
    private static final String HCONFIG_FILE_PATH = "C:\\Users\\sadiq\\Desktop\\javafinalq4\\src\\main\\java\\com\\example\\helbhotel\\main\\hconfig";
    private static final String CSV_FILE_PATH = "C:\\Users\\sadiq\\Desktop\\javafinalq4\\src\\main\\java\\com\\example\\helbhotel\\main\\reservation.csv";
    private List<Reservation> reservations = new ArrayList<>();
    private List<RoomAssignmentSuggestion> roomAssignmentSuggestions = new ArrayList<>();


    public Hotel() {
        configParser = new HConfigParser(HCONFIG_FILE_PATH);
        reservationParser = new ReservationParser(CSV_FILE_PATH);
        amountOfFloors = configParser.getNombreEtages();
        List<List<String>> floorData = configParser.getChambreConfig();

        for (int floorNumber = INITIAL_FLOOR_NUMBER; floorNumber < amountOfFloors; floorNumber++) {
            this.floor = new Room[FLOOR_ROWS][FLOOR_COLUMNS];
            int roomNumber = INITIAL_ROOM_NUMBER ;
            for (int i = INITIAL_INDEX; i < floorData.size(); i++) {
                for (int j = INITIAL_INDEX; j < floorData.get(i).size(); j++) {
                    String roomChar = floorData.get(i).get(j);
                    this.floor[i][j] = new RoomFactory().createRoom(roomChar.charAt(FIRST_CHAR_INDEX ),
                            getFloorLabel(floorNumber),
                            roomNumber);

                    if (roomChar.equals(EMPTY_ROOM_TYPE))
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

        for (int floorNumber = INITIAL_FLOOR_NUMBER; floorNumber < this.building.size(); floorNumber++) {
            Room[][] floorData = this.building.get(floorNumber);
            for (int i = INITIAL_INDEX ; i < floorData.length; i++) {
                for (int j = INITIAL_INDEX ; j < floorData[i].length; j++) {
                    Room room = floorData[i][j];
                    if (room.getRoomType().equals(EMPTY_ROOM_TYPE))
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
        for (int floorNumber = INITIAL_FLOOR_NUMBER; floorNumber < this.building.size(); floorNumber++) {
            Room[][] floorData = this.building.get(floorNumber);
            for (int i = INITIAL_INDEX ; i < floorData.length; i++) {
                for (int j = INITIAL_INDEX ; j < floorData[i].length; j++) {
                    Room room = floorData[i][j];
                    if (room.getRoomType().equals(EMPTY_ROOM_TYPE))
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
        String[] luxeRoom = new String[ROOM_INFO_ARRAY_SIZE];
        luxeRoom[LABEL_INDEX] = LUXE_LABEL;
        luxeRoom[COLOR_INDEX] = LUXE_COLOR;

        String[] economicRoom = new String[ROOM_INFO_ARRAY_SIZE];
        economicRoom[LABEL_INDEX] = ECONOMIC_LABEL;
        economicRoom[COLOR_INDEX] = ECONOMIC_COLOR;

        String[] businessRoom = new String[ROOM_INFO_ARRAY_SIZE];
        businessRoom[LABEL_INDEX] = BUSINESS_LABEL;
        businessRoom[COLOR_INDEX] = BUSINESS_COLOR;

        // Add the arrays to the list
        roomInformation.add(luxeRoom);
        roomInformation.add(economicRoom);
        roomInformation.add(businessRoom);

        return roomInformation;
    }

    public List<Room> getRooms() {
        List<Room> roomList = new ArrayList<Room>();

        for (int floorNumber = INITIAL_FLOOR_NUMBER; floorNumber < this.building.size(); floorNumber++) {
            Room[][] floorData = this.building.get(floorNumber);
            for (int i = INITIAL_INDEX; i < floorData.length; i++) {
                for (int j = INITIAL_INDEX; j < floorData[i].length; j++) {
                    Room room = floorData[i][j];
                    if (room.getRoomType().equals(EMPTY_ROOM_TYPE))
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
        while (n > INITIAL_INDEX) {
            n--;
            sb.insert(INITIAL_INDEX, (char) (FIRST_LETTER  + (n % ALPHABET_LENGTH)));
            n /= ALPHABET_LENGTH;
        }
        return sb.toString();
    }

    public Room[][] getFloor(String floorLabel) {
        int floorNumber = getFloorIndex(floorLabel);
        return building.get(floorNumber);
    }


    public static int getFloorIndex(String label) {
        int result = INITIAL_INDEX;
        for (int i = INITIAL_INDEX; i < label.length(); i++) {
            result *= ALPHABET_LENGTH;
            result += label.charAt(i) - FIRST_LETTER  + ALPHABET_START_OFFSET;
        }
        return result - ALPHABET_START_OFFSET;
    }

    public int getAmountOfFloors() {
        return configParser.getNombreEtages();
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