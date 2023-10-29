package com.banking;

/**
 * class for the Checking account type.
 * @author Karthik Gangireddy, Vineal Sunkara
 */
public class Checking extends Account{
    private static final double INTEREST_RATE = 0.01; //Checking's annual base interest rate
    private static final double MONTHLY_FEE = 12; //Checking monthly fee
    private static final double MONTHS = 12; // Months in a year
    private static final double EXEMPTION_AMT = 1000; // Minimum needed to be exempt from monthly fee

    /**
     * Constructor for Checking.
     * @param holder profile of account holder.
     * @param balance balance of initial deposit.
     */
    public Checking (Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * default constructor to allow inheritance with CollegeChecking.
     */
    public Checking () {
        this.holder = null;
        this.balance = 0;

    }

    /**
     * Calculates monthly interest for account.
     * @return monthly interest in dollars.
     */
    @Override
    public double monthlyInterest () {
        double rate = INTEREST_RATE/MONTHS;
        return this.balance * rate;
    }

    /**
     * Calculates the monthly fee for account.
     * @return monthly fee in dollars.
     */
    @Override
    public double monthlyFee () {
        if (this.balance >= EXEMPTION_AMT) {
            return 0;
        }
        else {
            return MONTHLY_FEE;}
    }

    /**
     * Calls the compareTo method in Account to compare objects.
     * @param account the object to be compared.
     * @return 1, 0, or -1 depending on which object is greater.
     */
    @Override
    public int compareTo(Account account) {
        return super.compareTo(account);
    }

}
