package com.banking;

/**
 * Class for the MoneyMarket account type, which factors in loyalty bonuses and withdrawals.
 * @author Karthik Gangireddy, Vineal Sunkara
 */
public class MoneyMarket extends Savings{
    private static final double INTEREST_RATE = 0.045; // Monkey Market's annual base interest rate
    private static final double MONTHLY_FEE = 25; // Money Market monthly fee
    private static final double PENALTY_AMT = 3; // # withdrawals needed for fee
    private static final double PENALTY = 10; // Fee for 3+ withdrawals
    private static final double MONTHS = 12; // Months in a year
    private static final double LOYALTYMIN = 2000; // minimum needed to be a loyal customer
    private static final double LOYALTY_BONUS = .0025; // Boost for being a loyal customer
    private int withdrawal; //number of withdrawals

    /**
     * Constructor for Money Market, starts with default loyalty and zero withdrawals.
     * @param holder profile of the account holder.
     * @param balance balance of the account.
     */
    public MoneyMarket (Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
        this.isLoyal = true;
        this.withdrawal = 0;
    }

    /**
     * returns number of withdrawals
     * @return number of withdrawals
     */
    public int getWithdrawal() {
        return withdrawal;
    }

    /**
     * Calculates monthly interest for account.
     * Factors in loyalty bonus if applicable.
     * @return monthly interest in dollars.
     */
    @Override
    public double monthlyInterest() {
        double rate = INTEREST_RATE;
        if (isLoyal) {
            rate += LOYALTY_BONUS;
        }
        rate = rate/MONTHS;
        return this.balance * (rate);
    }

    /**
     * Calculates monthly fee.
     * Factors in both loyalty and number of withdrawals in calculations.
     * @return monthly fee in dollars.
     */
    @Override
    public double monthlyFee() {
        if (this.balance >= LOYALTYMIN) {
            if (this.withdrawal >= PENALTY_AMT) {
                return PENALTY;
            }
            return 0;
        }
        else {
            if (this.withdrawal > PENALTY_AMT) {
                return PENALTY + MONTHLY_FEE;
            }
            return MONTHLY_FEE;
        }
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

    /**
     * increments withdrawals by 1.
     */
    public void addWithdrawal() {
        withdrawal += 1;
    }

    /**
     * resets withdrawals to zero.
     */
    public void clearWithdrawal() {this.withdrawal = 0;}
}
