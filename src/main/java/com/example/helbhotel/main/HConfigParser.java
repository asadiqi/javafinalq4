package com.example.helbhotel.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HConfigParser extends FileParser {

    private int nombreEtages;
    private ArrayList<ArrayList<String>> chambreConfig = new ArrayList<>();

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
                throw new IllegalArgumentException("The file is empty.");
            }

            nombreEtages = parseNombreEtages(lines.get(0));

            // Retirer la première ligne (nombre d'étages)
            lines.remove(0);

            // Lecture et validation de la matrice de configuration des chambres
            chambreConfig = parseChambresConfig(lines);

        } catch (IOException e) {
            throw new RuntimeException("Error reading the hconfig file: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e;  // Rejeter l'exception avec un message pertinent
        }
    }

    private int parseNombreEtages(String line) {
        try {
            int etages = Integer.parseInt(line.trim());
            if (etages <= 0) {
                throw new IllegalArgumentException("The number of floors must be a positive integer.");
            }
            return etages;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Incorrect format for the number of floors.");
        }
    }

    private ArrayList<ArrayList<String>> parseChambresConfig(List<String> lines) {
        ArrayList<ArrayList<String>> config = new ArrayList<>();
        int expectedColumnCount = -1;

        for (String line : lines) {
            List<String> parts = splitLine(line, ",");
            validateRoomConfig(parts);

            // Validation de la consistence des colonnes
            if (expectedColumnCount == -1) {
                expectedColumnCount = parts.size();
            } else {
                validateColumnCount(parts, expectedColumnCount);
            }

            config.add(new ArrayList<>(parts));
        }

        return config;
    }
}
