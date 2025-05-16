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
            throw new IllegalArgumentException("Invalid line format, expected " + expectedColumnCount + " columns.");
        }
    }

    // Valider les types de chambre
    protected void validateRoomConfig(List<String> parts) {
        for (String part : parts) {
            if (!isValidRoomType(part)) {
                throw new IllegalArgumentException("Invalid room type: " + part
                        + ". Valid types are B (Business), E (Economy), L (Luxury), Z (empty space).");
            }
        }
    }

    private boolean isValidRoomType(String part) {
        return part.equals("B") || part.equals("E") || part.equals("L") || part.equals("Z");
    }

    // Parsing utilitaires

    protected int parseIntPositive(String value, String fieldName) {
        try {
            int parsed = Integer.parseInt(value.trim());
            if (parsed < 1) {
                throw new IllegalArgumentException(fieldName + " must be a positive integer.");
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + fieldName + ": " + value);
        }
    }

    protected int parseNonNegativeInt(String value, String fieldName) {
        try {
            int parsed = Integer.parseInt(value.trim());
            if (parsed < 0) {
                throw new IllegalArgumentException(fieldName + " must be a non-negative integer.");
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + fieldName + ": " + value);
        }
    }

    protected boolean parseBoolean(String value, String fieldName) {
        if (value.equalsIgnoreCase("True")) {
            return true;
        } else if (value.equalsIgnoreCase("False")) {
            return false;
        } else {
            throw new IllegalArgumentException("Invalid value for " + fieldName + ". Expected 'True' or 'False'.");
        }
    }

    protected abstract void parse(String filename);
}
