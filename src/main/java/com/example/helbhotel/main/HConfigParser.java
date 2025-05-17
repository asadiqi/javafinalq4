package com.example.helbhotel.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HConfigParser extends FileParser {

    private int nombreEtages;
    private ArrayList<ArrayList<String>> chambreConfig = new ArrayList<>();
    private final String ERR_FILE_EMPTY = "The file is empty.";
    private final String ERR_FLOORS_NOT_POSITIVE = "The number of floors must be a positive integer.";
    private final String ERR_FLOORS_FORMAT = "Incorrect format for the number of floors.";
    private final String ERR_READING_HCONFIG = "Error reading the hconfig file: ";
    private final String CSV_SEPARATOR = ",";
    private final int INITIAL_EXPECTED_COLUMN_COUNT = -1;
    private final int INDEX=0;

    public HConfigParser(String filename) {
        parse(filename);  // Appel au parse() de la classe mère
    }

    public int getNombreEtages() {
        return nombreEtages;
    }

    public List<List<String>> getChambreConfig() {
        List<List<String>> config = new ArrayList<>();
        for (ArrayList<String> ligne : chambreConfig) {
            config.add(new ArrayList<>(ligne)); // Chaque ligne est convertie en List<String>
        }
        return config;
    }

    @Override
    protected void parse(String filename) {
        try {
            List<String> lines = readLines(filename, false);  // Utilisation de la méthode héritée

            // Vérification du nombre d'étages
            if (lines.isEmpty()) {
                throw new IllegalArgumentException(ERR_FILE_EMPTY );
            }

            nombreEtages = parseNombreEtages(lines.get(0));

            // Retirer la première ligne (nombre d'étages)
            lines.remove(INDEX);

            // Lecture et validation de la matrice de configuration des chambres
            chambreConfig = parseChambresConfig(lines);

        } catch (IOException e) {
            throw new RuntimeException(ERR_READING_HCONFIG + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e;  // Rejeter l'exception avec un message pertinent
        }
    }

    private int parseNombreEtages(String line) {
        try {
            int etages = Integer.parseInt(line.trim());
            if (etages <= INDEX) {
                throw new IllegalArgumentException(ERR_FLOORS_NOT_POSITIVE );
            }
            return etages;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERR_FLOORS_FORMAT );
        }
    }

    private ArrayList<ArrayList<String>> parseChambresConfig(List<String> lines) {
        ArrayList<ArrayList<String>> config = new ArrayList<>();
        int expectedColumnCount = INITIAL_EXPECTED_COLUMN_COUNT;

        for (String line : lines) {
            List<String> parts = splitLine(line, CSV_SEPARATOR );
            validateRoomConfig(parts);

            // Validation de la consistence des colonnes
            if (expectedColumnCount == INITIAL_EXPECTED_COLUMN_COUNT) {
                expectedColumnCount = parts.size();
            } else {
                validateColumnCount(parts, expectedColumnCount);
            }

            config.add(new ArrayList<>(parts));
        }

        return config;
    }
}
