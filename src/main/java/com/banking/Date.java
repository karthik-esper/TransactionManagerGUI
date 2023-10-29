package com.banking;

import java.util.Calendar;
import java.util.StringTokenizer;
/**
 * Class that handles the creation of the Date object to be used in Event. Date stores the year, month, date
 * Has constructers for Date, methods for getting month, year, day, comparing dates, validating dates, checking for leap year, and converting Date to String.
 * @author Karthik Gangireddy, Vineal Sunkara
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public static final int DAYSTART = 1; //beginning of date range
    public static final int DAYEND = 31; //end of date range
    public static final int FEBDAYEND = 28; //Last day of february
    public static final int QUADRENNIAL = 4; //Divisor for checking leap year. If year % quadrennial = 0, year is leap year.
    public static final int CENTENNIAL = 100; //Divisor for checking leap year. If year % centennial = 0, year is leap year.
    public static final int QUARTERCENTENNIAL = 400; //Divisor for checking leap year. If year % quartercentennial = 0, year is leap year.
    public static final int MONTHINCREMENTER = 1; //Because Calendar.MONTH returns 1 number less than actual month, this constant is added to match real world value of months

    /**
     * Constructer for the date class, has parameters for the year, month, and day.
     *
     * @param year  is the year of Date
     * @param month is the month of Date
     * @param day   is the day of Date
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Creates the Date from a string parameter
     *
     * @param date the date in string format
     */
    public Date(String date) {
        StringTokenizer dateAttributes = new StringTokenizer(date, "/");
        this.month = Integer.parseInt(dateAttributes.nextToken());
        this.day = Integer.parseInt(dateAttributes.nextToken());
        this.year = Integer.parseInt(dateAttributes.nextToken());
    }

    /**
     * Returns the year from Date object as int
     *
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the month from Date object as int
     *
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns the day from Date object as int
     *
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * Compares the Date object to the input Date object, and returns 1 if the testDate is greater than/in the future, -1 if the testDate is less than/in the past, and 0 if
     * testDate is identical to input Date object
     *
     * @param testDate the input date which is compared to initial Date object
     * @return int either 1 or 0 or -1
     */
    @Override
    public int compareTo(Date testDate) {
        if (this.year != testDate.year) {
            return (this.year > testDate.year) ? 1 : -1;
        } else if (this.month != testDate.month) {
            return (this.month > testDate.month) ? 1 : -1;
        } else if (this.day != testDate.day) {
            return (this.day > testDate.day) ? 1 : -1;
        } else {
            return 0;
        }
    }

    /**
     * Checks if the Date object is valid based on Gregorian Calender and Project Guidelines
     *
     * @return boolean - returns true or false depending on if date is valid or not
     */
    public boolean isValid() {
        if (month < (Calendar.JANUARY + MONTHINCREMENTER) || month > (Calendar.DECEMBER + MONTHINCREMENTER) || day < DAYSTART || day > DAYEND) {
            return false;
        }

        if (((month == Calendar.APRIL + MONTHINCREMENTER || month == Calendar.JUNE + MONTHINCREMENTER || month == Calendar.SEPTEMBER + MONTHINCREMENTER || month == Calendar.NOVEMBER + MONTHINCREMENTER)) && day == 31) {
            return day <= 30;
        }

        if (month == (Calendar.FEBRUARY + MONTHINCREMENTER)) {
            if (isLeapYear()) {
                return day <= FEBDAYEND + 1;
            } else {
                return day <= FEBDAYEND;
            }
        } else {
            return true;
        }

    }

    /**
     * Checks if year is a leap year
     *
     * @return boolean - returns true or false if year is or isn't leap year
     */
    public boolean isLeapYear() {
        return (year % QUADRENNIAL == 0 && year % CENTENNIAL != 0) || (year % QUARTERCENTENNIAL == 0);
    }

    /**
     * Overrides toString() method to allow Date object to be converted to a string
     *
     * @return String - returns date as String
     */
    @Override
    public String toString() {
        return this.month + "/" + this.day + "/" + this.year;
    }
}
