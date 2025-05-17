package com.example.helbhotel.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservationParser extends FileParser {

    private static final List<String> VALID_TRIP_REASONS = List.of("Tourism", "Business", "Other"); // Raisons de séjour
    // valides
    private ArrayList<Reservation> reservationList = new ArrayList<>();
    private int currentIndex = 0;
    private int maxIndex = 0;
    private final int INDEX_LASTNAME = 0;
    private final int INDEX_FIRSTNAME = 1;
    private final int INDEX_NUMBER_OF_PEOPLE = 2;
    private final int INDEX_SMOKER = 3;
    private final int INDEX_TRIP_REASON = 4;
    private final int INDEX_NUMBER_OF_CHILDREN = 5;
    private final String ERR_NO_MORE_REQUESTS = "No more requests available";
    private final String ERR_INVALID_TRIP_REASON = "Invalid trip reason: ";
    private final String ERR_READING_RESERVATION_FILE = "Error reading the reservation file: ";
    private final String FIELD_NUMBER_OF_PEOPLE = "Number of people";
    private final String FIELD_SMOKER = "Smoker";
    private final String FIELD_NUMBER_OF_CHILDREN = "Number of children";
    private final String VALIDE = ". Valid reasons are: ";


    public ReservationParser(String filename) {
        parse(filename);
        maxIndex = reservationList.size();
    }

    public boolean hasNextRequest() {
        return (currentIndex < maxIndex);
    }

    public Reservation getNextReservationRequest() {
        if (hasNextRequest()) {
            return reservationList.get(currentIndex++);
        } else {
            throw new IndexOutOfBoundsException(ERR_NO_MORE_REQUESTS);
        }
    }

    private Reservation getReservationRequestFromLineString(String line) {
        List<String> parts = splitLine(line, DELIMITER);

        validateColumnCount(parts, MAX_COLUMN_COUNT);

        String nom = parts.get(INDEX_LASTNAME );
        String prenom = parts.get(INDEX_FIRSTNAME );
        int nombreDePersonnes = parseIntPositive(parts.get(INDEX_NUMBER_OF_PEOPLE ), FIELD_NUMBER_OF_PEOPLE );
        boolean fumeur = parseBoolean(parts.get(INDEX_SMOKER ), FIELD_NUMBER_OF_CHILDREN );
        String motifSejour = parts.get(INDEX_TRIP_REASON );
        int nombreEnfants = parseNonNegativeInt(parts.get(INDEX_NUMBER_OF_CHILDREN ), FIELD_NUMBER_OF_CHILDREN );

        if (!VALID_TRIP_REASONS.contains(motifSejour)) {
            throw new IllegalArgumentException(
                    ERR_INVALID_TRIP_REASON+ motifSejour + VALIDE + VALID_TRIP_REASONS);
        }

        return new Reservation(nom.concat(" ").concat(prenom), nombreDePersonnes, fumeur, motifSejour, nombreEnfants);
    }

    public void deleteLineInFile(String filename, String line) {
        try {
            deleteLine(filename, line);
        } catch (IOException e) {
            System.out.println("An error happened when deleting line");
        }

    }

    @Override
    protected void parse(String filename) {
        try {
            List<String> lines = readLines(filename, true);

            for (String line : lines) {
                Reservation request = getReservationRequestFromLineString(line);
                reservationList.add(request);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(ERR_READING_RESERVATION_FILE + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

}
