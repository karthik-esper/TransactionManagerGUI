package com.banking;

/**
 * Class that handles all operations related to accounts.
 * Can open, close, withdraw, and deposit.
 * Keeps count of the number of accounts and is able to sort them to print.
 * @author Karthik Gangireddy, Vineal Sunkara
 */
public class AccountDatabase {
    private Account [] accounts; //list of various types of accounts
    private int numAcct; //number of accounts in the array
    private static final int GROWTH = 4; // number to grow by and initial size
    private static final double LOYALTYAMT = 2000; //if amount is crossed, can change the status of isLoyal

    /**
     * Constructor for AccountDatabase, creates a database with array length 4 and zero accounts
     */
    public AccountDatabase() {
        this.accounts = new Account[GROWTH];
        this.numAcct = 0;
    }



    /**
     * Iterates through the accounts array to see if the account is already listed.
     * @param account the account to find.
     * @return the index of the event if it is found in the array, otherwise -1 if not in array.
     */
    private int find(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (this.accounts[i].equals(account)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Copies the original array but increases the size by 4 when the accounts array reaches capacity
     */
    private void grow() {
        Account[] toInsert = new Account[this.accounts.length + GROWTH];
        for (int i = 0; i < accounts.length; i++) {
            toInsert[i] = this.accounts[i];
        }
        this.accounts = toInsert;
    }

    /**
     * Checks to see if account is in the database using the find method.
     * @param account account to check.
     * @return true if account is found.
     */
    public boolean contains(Account account){
        if (this.find(account) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * overload of contains specifically to prevent both college checking and checkings both opening.
     * @param account account to check.
     * @param operation string that signifies use of this one.
     * @return true if a checking or college checking is held by someone trying to open one.
     */
    public boolean contains(Account account, String operation){
        if (account instanceof CollegeChecking) {
            Checking acc = new Checking();
            acc.holder = account.holder;
            acc.balance = account.balance;
            if (this.find(acc) >= 0) {
                return true;
            }
            else if(this.find(account) >= 0) {
                return true;
            }
        }
        else if (account instanceof Checking) {
            CollegeChecking acc = new CollegeChecking(account.holder,0, null);
            acc.balance = account.balance;
            if (this.find(acc) >= 0) {
                return true;
            }
            else if (this.find(account) >= 0) {
                return true;
            }
        }
        else {return this.contains(account);}
        return false;
        }

    /**
     * Opens an account if valid one can be opened.
     * @param account account to open.
     * @return true if account opened, false otherwise.
     */
    public boolean open(Account account){
        if (numAcct > 0) {
            if (this.find(account) >= 0) {
                return false;
            }
        }
        if (numAcct >= accounts.length) {
            this.grow();
            this.open(account);
        }
        else {
            this.accounts[numAcct] = account;
            numAcct += 1;
        }
        return true;
    }

    /**
     * Closes an account if account can be closed.
     * @param account account to close.
     * @return true if account was closed, false if otherwise.
     */
    public boolean close(Account account){
        int eventIndex = this.find(account);
        if (eventIndex < 0) {
            return false; // element not in events
        }
        else {
            this.accounts[eventIndex] = null;
            for (int i = 0; i < this.accounts.length-1; i++){
                if (this.accounts[i] == null && this.accounts[i+1] != null) {
                    this.accounts[i] = this.accounts[i+1];
                    this.accounts[i+1] = null;
                }
            }
        }
        numAcct -= 1;
        return true;
    }

    /**
     * Withdraws from account if account can be withdrawn from.
     * @param account account to withdraw from.
     * @return true if withdrawal happened, false otherwise.
     */
    public boolean withdraw(Account account){
        if (this.contains(account)) {
            int index = find(account);
            if (account.balance <= accounts[index].balance) {
                accounts[index].balance -= account.balance;
                if (accounts[index] instanceof MoneyMarket) {
                    if (accounts[index].balance < LOYALTYAMT) {
                        ((MoneyMarket) accounts[index]).isLoyal = false;
                    }
                    ((MoneyMarket) accounts[index]).addWithdrawal();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Deposits into an account, otherwise does if cannot.
     * @param account account to deposit into.
     */
    public void deposit(Account account){
        if (account.balance <= 0) {
            return;
        }
        if (this.contains(account)) {
            int index = find(account);
            accounts[index].balance += account.balance;
            if (accounts[index] instanceof MoneyMarket) {
                if (accounts[index].balance >= LOYALTYAMT) {
                    ((MoneyMarket) accounts[index]).isLoyal = true;
                }
            }
        }
    }

    /**
     * Print the sorted array by account type and profile.
     * @return the String version of the sorted array.
     */
    public String printSorted(){
        if (numAcct > 0) {
            for (int i = 0; i < this.accounts.length; i++) {
                if (this.accounts[i] != null) {
                    sortByAccountType(this.accounts);
                }
            }
            String toAdd = "\n*Accounts sorted by account type and profile.\n";
            for (int i = 0; i < this.accounts.length && this.accounts[i] != null; i++) {
                toAdd += this.accounts[i].toString() + "\n";
            }

            toAdd += "*end of list.\n";
            return toAdd;
        }
        else {
            return "Account Database is empty!";
        }

    }

    /**
     * Sorts the database using a variant of bubble sort.
     * @param accounts array of accounts from the database.
     */
    public void sortByAccountType(Account accounts[]) {
        Account tmp;
        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = i + 1; j < numAcct; j++) {
                if (accounts[i].compareTo(accounts[j]) > 0) {
                    tmp = accounts[i];
                    accounts[i] = accounts[j];
                    accounts[j] = tmp;
                }
            }
        }
    }

    /**
     * Calculates and displays the monthly fee and interest of the account at given time.
     * @return the String version of the array with fees and interest.
     */
    public String printFeesAndInterests() {
        if (numAcct > 0) {
            String toAdd = "\n*list of accounts with fee and monthly interest\n";
            for (int i = 0; i < this.accounts.length; i++) {
                if (this.accounts[i] != null) {
                    sortByAccountType(this.accounts);
                    String toPrint = this.accounts[i].toString();
                    toPrint += "::fee $" + String.format("%.2f",this.accounts[i].monthlyFee());
                    toPrint += "::monthly interest $" + String.format("%.2f",this.accounts[i].monthlyInterest());
                    toAdd += toPrint + "\n";
                }
            }
            toAdd += "*end of list.\n";
            return toAdd;
        }
        else {
            return "Account Database is empty!";
        }
    }

    /**
     * Prints updated balances by calculating interest and fees.
     * @return the String version of the balances printed.
     */
    public String printUpdatedBalances(){
        if (numAcct > 0) {
            String toAdd = "\n*list of accounts with fees and interests applied.\n";
            for (int i = 0; i < this.accounts.length; i++) {
                if (this.accounts[i] != null) {
                    sortByAccountType(this.accounts);
                    double interest = this.accounts[i].monthlyInterest();
                    double fee = this.accounts[i].monthlyFee();
                    this.accounts[i].balance -= fee;
                    this.accounts[i].balance += interest;
                    if (this.accounts[i] instanceof MoneyMarket) {
                        ((MoneyMarket) this.accounts[i]).clearWithdrawal();
                    }
                    toAdd += this.accounts[i].toString() + "\n";
                }
            }
            toAdd += "*end of list.\n";
            return toAdd;
        }
        else {
            return "Account Database is empty!";
        }
    }
}
