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
            throw new IndexOutOfBoundsException("No more requests available");
        }
    }

    private Reservation getReservationRequestFromLineString(String line) {
        List<String> parts = splitLine(line, DELIMITER);

        validateColumnCount(parts, MAX_COLUMN_COUNT);

        String nom = parts.get(0);
        String prenom = parts.get(1);
        int nombreDePersonnes = parseIntPositive(parts.get(2), "Number of people");
        boolean fumeur = parseBoolean(parts.get(3), "Smoker");
        String motifSejour = parts.get(4);
        int nombreEnfants = parseNonNegativeInt(parts.get(5), "Number of children");

        if (!VALID_TRIP_REASONS.contains(motifSejour)) {
            throw new IllegalArgumentException(
                    "Invalid trip reason: " + motifSejour + ". Valid reasons are: " + VALID_TRIP_REASONS);
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
            throw new IllegalArgumentException("Error reading the reservation file: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

}
