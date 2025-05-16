package com.example.helbhotel.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class FileParser {

    protected static final String DELIMITER = ","; // Délimiteur par défaut
    protected static final int MAX_COLUMN_COUNT = 6; // Nombre de colonnes par défaut (à surcharger si besoin)
    protected final String ROOM_TYPE_BUSINESS = "B";
    protected final String ROOM_TYPE_ECONOMY = "E";
    protected final String ROOM_TYPE_LUXURY = "L";
    protected final String ROOM_TYPE_EMPTY = "Z";

    protected final String ERR_INVALID_ROOM_TYPE = "Invalid room type: ";
    protected final String ERR_INVALID_COLUMN_COUNT = "Invalid line format, expected ";
    protected final String VALID_ROOM_TYPES = "Valid types are B (Business, E (Economy), L (Luxury), Z (empty space). ";
    protected final String POSITIVE_INTEGER = " must be a positive integer.. ";
    protected final String NO_NEGATIVE_INTEGER = "  must be a non-negative integer.. ";
    protected final String ERR_INVALID_BOOLEAN_VALUE = "Invalid value for %s. Expected '%s' or '%s'.";


    protected final int INITIAL_INDEX = 0;
    protected final int VALUE = 1;

    protected final String VALUE_TRUE = "True";
    protected final String VALUE_FALSE = "False";

    // Lecture de lignes dans un fichier
    protected List<String> readLines(String filename, boolean skipFirstLine) throws IOException {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            boolean isFirstLine = skipFirstLine;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    protected List<String> deleteLine(String filename, String line) throws IOException {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(filename));
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals(line)) {
                    lines.remove(i);
                    break;
                }
            }

            // Write the modified content back to the file
            Files.write(Paths.get(filename), lines);
            System.out.println("Line deleted successfully.");
            return lines;
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return lines;
    }

    // Découper une ligne
    protected List<String> splitLine(String line, String delimiter) {
        List<String> partsList = new ArrayList<>();
        String[] parts = line.split(delimiter);
        for (String part : parts) {
            partsList.add(part.trim());
        }
        return partsList;
    }

    // Valider le nombre de colonnes
    protected void validateColumnCount(List<String> parts, int expectedColumnCount) {
        if (parts.size() != expectedColumnCount) {
            throw new IllegalArgumentException(ERR_INVALID_COLUMN_COUNT + expectedColumnCount + " columns.");
        }
    }

    // Valider les types de chambre
    protected void validateRoomConfig(List<String> parts) {
        for (String part : parts) {
            if (!isValidRoomType(part)) {
                throw new IllegalArgumentException(ERR_INVALID_ROOM_TYPE + part
                        + VALID_ROOM_TYPES);
            }
        }
    }

    private boolean isValidRoomType(String part) {
        return part.equals(ROOM_TYPE_BUSINESS) || part.equals(ROOM_TYPE_ECONOMY) || part.equals(ROOM_TYPE_LUXURY) || part.equals(ROOM_TYPE_EMPTY);
    }

    // Parsing utilitaires

    protected int parseIntPositive(String value, String fieldName) {
        try {
            int parsed = Integer.parseInt(value.trim());
            if (parsed < VALUE) {
                throw new IllegalArgumentException(fieldName + POSITIVE_INTEGER );
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + fieldName + ": " + value);
        }
    }

    protected int parseNonNegativeInt(String value, String fieldName) {
        try {
            int parsed = Integer.parseInt(value.trim());
            if (parsed < INITIAL_INDEX) {
                throw new IllegalArgumentException(fieldName + NO_NEGATIVE_INTEGER);
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format(ERR_INVALID_BOOLEAN_VALUE, fieldName, VALUE_TRUE, VALUE_FALSE));
        }
    }

    protected boolean parseBoolean(String value, String fieldName) {
        if (value.equalsIgnoreCase(VALUE_TRUE)) {
            return true;
        } else if (value.equalsIgnoreCase(VALUE_FALSE)) {
            return false;
        } else {
            throw new IllegalArgumentException();
        }
    }

    protected abstract void parse(String filename);
}
