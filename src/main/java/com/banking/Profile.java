package com.banking;

/**
 * Class which stores the profile of the account holder
 * @author Karthik Gangireddy, Vineal Sunkara
 */
public class Profile implements Comparable<Profile>{
    private String fname; //Profile user first name
    private String lname; // Profile user last name
    private Date dob; // Profile user date of birth

    /**
     * Creates a constructor for the profile
     * @param fname first name of the user
     * @param lname last name of the user
     * @param dob date of birth of the user
     */
    public Profile (String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     *
     * @param profile the object to be compared.
     * @return 1 if this's attributes are lexicographically greater than profile's, 0 if equal, -1 otherwise
     */
    @Override
    public int compareTo(Profile profile) {
        if (this.lname.compareToIgnoreCase(profile.lname) != 0) {
            return this.lname.compareTo(profile.lname);
        }
        else if (this.fname.compareToIgnoreCase(profile.fname) != 0) {
            return this.fname.compareTo(profile.fname);
        }
        else if (this.dob.compareTo(profile.dob) != 0) {
            return this.dob.compareTo(profile.dob);
        }
        else {
            return 0;
        }
    }

    /**
     * Returns the profile as a string in order of first name last name date.
     * @return String version of the profile.
     */
    @Override
    public String toString() {
        return this.fname + " " + this.lname + " " + this.dob.toString();
    }

}
