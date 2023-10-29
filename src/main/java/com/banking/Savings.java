package com.banking;

/**
 * Class for the account type Savings, factors in loyalty bonuses.
 * @author Karthik Gangireddy, Vineal Sunkara
 */
public class Savings extends Account{
    private static final double INTEREST_RATE = 0.04; // Savings base interest rate
    private static final double MONTHLY_FEE = 25; // Savings monthly fee
    private static final double MONTHS = 12; // Months in a year
    private static final double LOYALTY_BONUS = .0025; // Boost for being a loyal customer
    private static final double EXEMPTION_AMT = 500;
    protected boolean isLoyal; //loyal customer status

    /**
     * Constructor for savings account based on holder, balance, loyalty.
     * @param holder profile of the account holder.
     * @param balance balance of the initial deposit.
     * @param isLoyal loyalty status.
     */
    public Savings (Profile holder, double balance, boolean isLoyal) {
        this.holder = holder;
        this.balance = balance;
        this.isLoyal = isLoyal;
    }

    /**
     * default constructor to allow inheritance with MoneyMarket.
     */
    public Savings () {
        this.balance = 0;
        this.holder = null;
        this.isLoyal = false;
    }

    /**
     * Calculates monthly interest for account.
     * Factors in loyalty bonus if applicable.
     * @return monthly interest in dollars.
     */
    @Override
    public double monthlyInterest() {
        if (isLoyal) {
            double rate = INTEREST_RATE + LOYALTY_BONUS;
            rate = rate/MONTHS;
            return this.balance * (rate);


        }
        else {
            double rate = INTEREST_RATE/MONTHS;
            return this.balance * rate;
        }
    }

    /**
     * Calculates monthly fee.
     * Factors in possible exemption for fee based on balance.
     * @return monthly fee in dollars.
     */
    @Override
    public double monthlyFee() {
        if (this.balance >= EXEMPTION_AMT) {
            return 0;
        }
        else {
            return MONTHLY_FEE;
        }
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
