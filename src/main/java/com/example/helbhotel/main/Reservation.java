package com.example.helbhotel.main;

public class Reservation {
    // Attributes
    private String fullName;
    private int numberOfPeople;
    private boolean isSmoker;
    private String stayPurpose;
    private int numberOfChildren;

    // Constructor
    public Reservation(String fullName, int numberOfPeople, boolean isSmoker,
                       String stayPurpose, int numberOfChildren) {
        // Validation of constraints
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }

        if (numberOfPeople < 1 || numberOfPeople > 4) {
            throw new IllegalArgumentException("Number of people must be between 1 and 4");
        }

        if (numberOfChildren < 0) {
            throw new IllegalArgumentException("Number of children cannot be negative");
        }

        if (numberOfChildren >= numberOfPeople) {
            throw new IllegalArgumentException(
                    "Number of children cannot be greater than or equal to the total number of people");
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

    // Setters
    public void setFullName(String fullName) {
        if (fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        this.fullName = fullName;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        if (numberOfPeople < 1 || numberOfPeople > 4) {
            throw new IllegalArgumentException("Number of people must be between 1 and 4");
        }
        if (this.numberOfChildren >= numberOfPeople) {
            throw new IllegalArgumentException(
                    "Number of children cannot be greater than or equal to the total number of people");
        }
        this.numberOfPeople = numberOfPeople;
    }

    public void setIsSmoker(boolean isSmoker) {
        this.isSmoker = isSmoker;
    }

    public void setStayPurpose(String stayPurpose) {
        this.stayPurpose = stayPurpose;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        if (numberOfChildren < 0) {
            throw new IllegalArgumentException("Number of children cannot be negative");
        }
        if (numberOfChildren >= this.numberOfPeople) {
            throw new IllegalArgumentException(
                    "Number of children cannot be greater than or equal to the total number of people");
        }
        this.numberOfChildren = numberOfChildren;
    }

    public String setToCsvLineFormat() {

        StringBuilder csvFormatLine = new StringBuilder();
        csvFormatLine.append(fullName.replace(" ", ","));
        csvFormatLine.append(",");
        csvFormatLine.append(numberOfPeople);
        csvFormatLine.append(",");
        csvFormatLine.append(isSmoker ? "True" : "False");
        csvFormatLine.append(",");
        csvFormatLine.append(stayPurpose);
        csvFormatLine.append(",");
        csvFormatLine.append(numberOfChildren);

        return csvFormatLine.toString();
    }

}