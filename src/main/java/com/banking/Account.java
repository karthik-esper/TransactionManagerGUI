package com.banking;

/**
 * Class that stores accounts based on the parameters provided.
 * Other account subclasses inherit from this one.
 * Can compare accounts as well as print a string representation of them.
 * @author Karthik Gangireddy, Vineal Sunkara
 */
public abstract class Account implements Comparable<Account> {
    protected Profile holder; // account holder profile
    protected double balance; // account balance
    public abstract double monthlyInterest();
    public abstract double monthlyFee();

    /**
     * Constructor for account.
     * @param holder profile of the holder.
     * @param balance balance to begin with.
     */
    public Account (Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * Default constructor to allow inheritance.
     */
    public Account () {
        this.holder = null;
        this.balance = 0;
    }

    /**
     * getter method for Profile Name.
     * @return
     */
    public String getProfileName() {
        return holder.toString();
    }

    /**
     * Compared the accounts and returns value based on who is greater by comparing many attributes.
     * @param account the object to be compared.
     * @return 1 if this is greater than account, 0 if equal, -1 is this is less than account.
     */
    @Override
    public int compareTo(Account account) { // could use class comparision as first nest
        if (this.getClass().getName().compareTo(account.getClass().getName()) != 0) {
            return (this.getClass().getName().compareTo(account.getClass().getName()) > 0) ? 1 : -1;
        }
        if (this.holder.compareTo(account.holder) != 0) {
            return ((this.holder.compareTo(account.holder) > 0)) ? 1 : -1;
        }
        if (this.balance != account.balance) {
            return (this.balance > account.balance) ? 1 : -1;
        } else if (this.monthlyInterest() != account.monthlyInterest()) {
            return (this.monthlyInterest() > account.monthlyInterest()) ? 1 : -1;
        } else if (this.monthlyFee() != account.monthlyFee()) {
            return (this.monthlyFee() > account.monthlyFee()) ? 1 : -1;
        } else {
            return 0;
        }
    }

    /**
     * Equals method that checks whether two accounts are equal.
     * @param obj Object that represents the account.
     * @return true if equal and false if not equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account account = (Account) obj;
            if (this.getClass().getName().equals(account.getClass().getName())) {
                if (this.holder.compareTo(account.holder) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the account in a string format.
     * Accounts for some account types having different attributes and prints accordingly.
     * @return String represetation of the Account.
     */
    @Override
    public String toString() {
        String template = "";
        if (this instanceof MoneyMarket) {
            template += "Money Market::Savings::";
        }
        else if (this instanceof CollegeChecking) {
            template += "College Checking::";
        }
        else {
            String toTrim = this.getClass().toString();
            template += toTrim.substring(14) + "::";
        }
        template += this.holder.toString() + "::Balance $" + String.format("%,.2f",this.balance);
        if (this instanceof CollegeChecking) {
            template += "::" + ((CollegeChecking) this).getCampus();
        }
        if (this instanceof Savings) {
            if (((Savings) this).isLoyal) {
                template += "::is loyal";
            }
        }
        if (this instanceof MoneyMarket) {
            template += "::withdrawal: " + ((MoneyMarket) this).getWithdrawal();
        }

        return template;
    }

}



