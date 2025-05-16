package com.example.helbhotel.main;

public class Reservation {

    private String fullName;
    private int numberOfPeople;
    private boolean isSmoker;
    private String stayPurpose;
    private int numberOfChildren;
    private final int MIN_PEOPLE = 1;
    private final int MAX_PEOPLE = 4;
    private final int MIN_CHILDREN = 0;
    private final String CSV_TRUE = "True";
    private final String CSV_FALSE = "False";
    private final String CSV_SEPARATOR = ",";
    private final String ERR_EMPTY_FULLNAME = "Full name cannot be empty";
    private final String ERR_PEOPLE_RANGE = "Number of people must be between " + MIN_PEOPLE + " and " + MAX_PEOPLE;
    private final String ERR_NEGATIVE_CHILDREN = "Number of children cannot be negative";
    private final String ERR_CHILDREN_COUNT = "Number of children cannot be greater than or equal to the total number of people";


    // Constructor
    public Reservation(String fullName, int numberOfPeople, boolean isSmoker,
                       String stayPurpose, int numberOfChildren) {
        // Validation of constraints
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException(ERR_EMPTY_FULLNAME);
        }

        if (numberOfPeople < MIN_PEOPLE  || numberOfPeople > MAX_PEOPLE ) {
            throw new IllegalArgumentException(ERR_PEOPLE_RANGE);
        }

        if (numberOfChildren < MIN_CHILDREN) {
            throw new IllegalArgumentException(ERR_NEGATIVE_CHILDREN);
        }

        if (numberOfChildren >= numberOfPeople) {
            throw new IllegalArgumentException(ERR_CHILDREN_COUNT);
        }

        this.fullName = fullName;
        this.numberOfPeople = numberOfPeople;
        this.isSmoker = isSmoker;
        this.stayPurpose = stayPurpose;
        this.numberOfChildren = numberOfChildren;
    }

    // Getters
    public String getFullName() {
        return fullName;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public boolean isSmoker() {
        return isSmoker;
    }

    public String getStayPurpose() {
        return stayPurpose;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public boolean hasChildren() {
        return this.numberOfChildren > 0;
    }


    public String setToCsvLineFormat() {

        StringBuilder csvFormatLine = new StringBuilder();
        csvFormatLine.append(fullName.replace(" ", CSV_SEPARATOR));
        csvFormatLine.append(CSV_SEPARATOR);
        csvFormatLine.append(numberOfPeople);
        csvFormatLine.append(CSV_SEPARATOR);
        csvFormatLine.append(isSmoker ? CSV_TRUE  : CSV_FALSE );
        csvFormatLine.append(CSV_SEPARATOR);
        csvFormatLine.append(stayPurpose);
        csvFormatLine.append(CSV_SEPARATOR);
        csvFormatLine.append(numberOfChildren);

        return csvFormatLine.toString();
    }

}