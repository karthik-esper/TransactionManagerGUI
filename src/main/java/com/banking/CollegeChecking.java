package com.banking;
/**
 * class for the College Checking account type.
 * @author Karthik Gangireddy, Vineal Sunkara
 */
public class CollegeChecking extends Checking {
    private static final double INTEREST_RATE = 0.01; // College checking's annual interest rate
    private static final double MONTHLY_FEE = 0; // no monthly fee
    private static final double MONTHS = 12; // Months in a year
    private Campus campus; // Campus of account

    /**
     * Constructor for College Checking account.
     * @param holder profile of account holder.
     * @param balance balance of initial deposit.
     * @param campus campus of account holder.
     */
    public CollegeChecking (Profile holder, double balance, Campus campus) {
        this.holder = holder;
        this.balance = balance;
        this.campus = campus;
    }

    /**
     * Getter method that returns campus.
     * @return campus of the account holder.
     */
    public Campus getCampus() {
        return campus;
    }

    /**
     * Calculates monthly interest for account.
     * @return monthly interest in dollars.
     */
    @Override
    public double monthlyInterest() {
        double rate = INTEREST_RATE/MONTHS;
        return this.balance * rate;
    }

    /**
     * Returns account's zero monthly fee.
     * @return monthly fee of account.
     */
    @Override
    public double monthlyFee() {
        return MONTHLY_FEE;
    }

    /**
     * Calls the compareTo method in parent class to compare objects.
     * @param account the object to be compared.
     * @return 1, 0, or -1 depending on which object is greater.
     */
    @Override
    public int compareTo(Account account) {
        return super.compareTo(account);
    }
}
