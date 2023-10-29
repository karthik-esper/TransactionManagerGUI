package com.banking;

/**
 * Enum class whose values are the campus code of each respective campus.
 * @author Karthik Gangireddy, Vineal Sunkara
 */
public enum Campus {
    NEW_BRUNSWICK(0),
    NEWARK(1),
    CAMDEN(2);

    private final int campusCode; // Campus code as a number

    /**
     * Constructor for a Campus based on the code.
     * @param campusCode campus code
     */
    private Campus (int campusCode) {
        this.campusCode = campusCode;
    }

    /**
     * Getter method for campus code.
     * @return campus code
     */
    public int getCampusCode() {
        return campusCode;
    }


}
