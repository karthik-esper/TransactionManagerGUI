package com.banking;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * A class that handles every operation regarding creating and updating the transaction manager in order to run project
 * Can take input continuously and be stopped at any point by entering Q as a command line input
 * @author Karthik Gangireddy, Vineal Sunkara
 */
public class TransactionManager {
    private AccountDatabase accountDatabase; // Account database that handles stuff
    private boolean notQ; // instance variable to check whether Q has been entered or not

    /**
     * Default constructor for transaction manager with an empty database.
     */
    public TransactionManager() {
        this.accountDatabase = new AccountDatabase();
    }

    /**
     * Main command which is used to run the entire package.
     * Uses the takeInput() method to process command line input,
     * stops when the Q command has been used.
     */
    public void run() {
        AccountDatabase organizer = new AccountDatabase();
        Scanner inputReader = new Scanner(System.in);
        System.out.println("Transaction Manager is running...");
        notQ = true;
        while (notQ) {
            takeInput(inputReader);
        }
        System.out.println("Transaction Manager is terminated.");
        inputReader.close();
    }

    /**
     * Creates an account after running every check to make sure a valid one can be made.
     * @param tokens Array that contains string representations of every token.
     * @return either an account if able to create, null otherwise.
     */
    private Account createAccount(String[] tokens) {
        if (tokens.length <= 2) {
            System.out.println("Missing data for opening an account.");
            return null;
        }
        if (!checkInputLength(tokens, tokens[1])){
            System.out.println("Missing data for opening an account.");
            return null;
        }
        if (!validDob(tokens[4])) {
            return null;
        }
        if (tokens[1].equals("C")|| tokens[1].equals("CC")) {
            return createCheckings(tokens);
        }
        else if (tokens[1].equals("S") || tokens[1].equals("MM")) {
            return createSavings(tokens);
        }
        return null;
        }

    /**
     * Creates a checkings or college checkings account if possible.
     * @param tokens Array that contains string representations of every token.
     * @return either an account if able to create, null otherwise.
     */
    private Account createCheckings(String[] tokens) {
        if (!checkInitialDeposit(tokens[5], tokens[1])) {
            return null;
        }
        if (tokens[1].equals("C")) {
            Profile holder = new Profile(tokens[2], tokens[3], new Date(tokens[4]));
            return new Checking(holder, Double.parseDouble(tokens[5]));
        }
        else if (tokens[1].equals("CC")) {
            if (!validAge(tokens[4])) {
                return null;
            }
            Profile holder = new Profile(tokens[2], tokens[3], new Date(tokens[4]));
            Campus campus = null;
            if (tokens[6].equals("0")) {
                campus = Campus.NEW_BRUNSWICK;
            }
            else if (tokens[6].equals("1")) {
                campus = Campus.NEWARK;
            }
            else if (tokens[6].equals("2")){
                campus = Campus.CAMDEN;
            }
            else {
                System.out.println("Invalid campus code.");
                return null;
            }
            return new CollegeChecking(holder, Double.parseDouble(tokens[5]), campus);
        }
        return null;
    }

    /**
     * Checks to see if the user DOB is valid.
     * @param date the date to test.
     * @return true if valid, false otherwise.
     */
    private boolean validDob(String date) {
        Date today = new Date("10/16/2023");
        Date tooEarly = new Date ("10/16/2007");
        Date test = new Date(date);

        if (test.compareTo(today) > 0) {
            System.out.println("DOB invalid: " + date + " cannot be today or a future day.");
            return false;
        }
        if (!test.isValid()) {
            System.out.println("DOB invalid: " + date + " not a valid calendar date!");
            return false;
        }
        if (tooEarly.compareTo(test) == -1) {
            System.out.println("DOB invalid: "  + date + " under 16.");
            return false;
        }
        return true;
    }

    /**
     * Checks specifically for college checkings if person is too old or young.
     * @param date date to test.
     * @return true if valid age, false if otherwise.
     */
    private boolean validAge(String date) {
        Date Test = new Date(date);
        Date tooEarly = new Date ("10/16/2007");
        Date tooLate = new Date ("10/16/1999");
        if (tooEarly.compareTo(Test) == -1) {
            System.out.println("DOB invalid: "  + date + " under 16.");
            return false;
        }
        else if (tooLate.compareTo(Test) == 1) {
            System.out.println("DOB invalid: "  + date + " over 24.");
            return false;
        }
        return true;
    }

    /**
     * Creates a Savings or Money Market account if possible.
     * @param tokens Array that contains string representations of every token.
     * @return either an account if able to create, null otherwise.
     */
    private Account createSavings(String[] tokens) {
        if (!checkInitialDeposit(tokens[5], tokens[1])) {
            return null;
        }
        if (tokens[1].equals("S")) {
            Profile holder = new Profile(tokens[2], tokens[3], new Date(tokens[4]));
            int isLoyal = Integer.parseInt(tokens[6]);
            return new Savings(holder, Double.parseDouble(tokens[5]), (isLoyal > 0));
        }
        else if (tokens[1].equals("MM")) {
            Profile holder = new Profile(tokens[2], tokens[3], new Date(tokens[4]));
            return new MoneyMarket(holder, Double.parseDouble(tokens[5]));
        }
        return null;
    }

    /**
     * Checks if the input length is right to perform the operation.
     * @param operation the array that represents the tokens to add.
     * @param account the account type that is being checked.
     * @return true if array is long enough, false if otherwise.
     */
    private boolean checkInputLength (String[] operation, String account) {
        if (account.equals("CC") || account.equals("S")) {
            return (operation.length >= 7);
        }
        else if (account.equals("C") || account.equals("MM")) {
            return (operation.length >= 6);
        }
        else if (account.equals("Close")) {
            return (operation.length >= 5);
        }
        return true;
    }

    /**
     * Checks is the account can be made with the current deposit.
     * @param deposit the deposit to be checked.
     * @param type checks the account type to see if money market.
     * @return true if valid deposit, false otherwise.
     */
    private boolean checkInitialDeposit(String deposit, String type) {
        try {
            Double.parseDouble(deposit);
        } catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return false;
        }
        if (Double.parseDouble(deposit) <= 0) {
            System.out.println("Initial deposit cannot be 0 or negative.");
            return false;
        }
        if (type.equals("MM")) {
            if (Double.parseDouble(deposit) < 2000) {
                System.out.println("Minimum of $2000 to open a Money Market account.");
                return false;
            }
        }

        return true;
    }

    /**
     * Checks the account balance before withdrawing.
     * @param balance amount to check.
     * @return true if can be withdrawn, false if otherwise.
     */
    private boolean checkBalance(String balance){
        try {
            Double.parseDouble(balance);
        } catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return false;
        }

        if (Double.parseDouble(balance) <= 0) {
            System.out.println("Withdraw - amount cannot be 0 or negative.");
            return false;
        }

        return true;
    }

    /**
     * checks deposit to see if the deposit can be made.
     * @param balance amount to check.
     * @return true if can be deposited, false if otherwise.
     */
    private boolean checkDeposit(String balance){
        try {
            Double.parseDouble(balance);
        } catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return false;
        }

        if (Double.parseDouble(balance) <= 0) {
            System.out.println("Deposit - amount cannot be 0 or negative.");
            return false;
        }

        return true;
    }

    /**
     * Creates an account for the sake of either withdrawing or depositing into another.
     * @param tokens Array that contains string representations of every token.
     * @return account if one can be made, null if cannot be made.
     */
    private Account accountForOperation(String[] tokens) {
        if (tokens[0].equals("W")) {
            if (!checkBalance(tokens[5])) {return null;}
        }
        else if (tokens[0].equals("D")) {
            if (!checkDeposit(tokens[5])) {return null;}
        }

        if (tokens[1].equals("CC")) {
            Profile person = new Profile(tokens[2], tokens[3], new Date(tokens[4]));
            return new CollegeChecking(person, Double.parseDouble(tokens[5]), Campus.NEWARK);
        } else if (tokens[1].equals("C")) {
            Profile person = new Profile(tokens[2], tokens[3], new Date(tokens[4]));
            return new Checking(person, Double.parseDouble(tokens[5]));
        } else if (tokens[1].equals("S")) {
            Profile person = new Profile(tokens[2], tokens[3], new Date(tokens[4]));
            return new Savings(person, Double.parseDouble(tokens[5]), true);
        } else if (tokens[1].equals("MM")) {
            Profile person = new Profile(tokens[2], tokens[3], new Date(tokens[4]));
            return new MoneyMarket(person, Double.parseDouble(tokens[5]));
        }

        return null;
    }

    /**
     * Creates an account with limited details for the sake of closing an equivalent.
     * @param tokens Array that contains string representations of every token.
     * @return account if one can be made, null if otherwise.
     */
    private Account accountForClosing(String[] tokens) {
        if (!checkInputLength(tokens, "Close")){
            System.out.println("Missing data for closing an account.");
            return null;
        }
        if (!validDob(tokens[4])) {
            return null;
        }
        if (tokens[1].equals("CC")) {
            Profile person = new Profile(tokens[2], tokens[3], new Date(tokens[4]));
            return new CollegeChecking(person, 0, Campus.NEWARK);
        } else if (tokens[1].equals("C")) {
            Profile person = new Profile(tokens[2], tokens[3], new Date(tokens[4]));
            return new Checking(person, 0);
        } else if (tokens[1].equals("S")) {
            Profile person = new Profile(tokens[2], tokens[3], new Date(tokens[4]));
            return new Savings(person, 0, true);
        } else if (tokens[1].equals("MM")) {
            Profile person = new Profile(tokens[2], tokens[3], new Date(tokens[4]));
            return new MoneyMarket(person, 0);
        }
        return null;
    }

    /**
     * Handles the creation and operation of an account for the deposit method.
     * @param tokens Array that contains string representations of every token.
     */
    private void depositHelper (String[] tokens) {
        Account forDeposit = accountForOperation(tokens);
        if (forDeposit != null) {
            if (!this.accountDatabase.contains(forDeposit)) {
                System.out.println(forDeposit.holder.toString() + "(" + tokens[1] + ") is not in the database.");
                return;
            }
            this.accountDatabase.deposit(forDeposit);
            System.out.println(forDeposit.holder.toString() + "(" + tokens[1] + ") Deposit - balance updated.");
        }
    }
    /**
     * Handles the creation and operation of an account for the withdraw method.
     * @param tokens Array that contains string representations of every token.
     */
    private void withdrawalHelper (String[] tokens) {
        Account forWithdrawal = accountForOperation(tokens);
        if (forWithdrawal != null) {
            if (!this.accountDatabase.contains(forWithdrawal)) {
                System.out.println(forWithdrawal.holder.toString() + "(" + tokens[1] + ") is not in the database.");
                return;
            }
            boolean check = this.accountDatabase.withdraw(forWithdrawal);
            if (!check) {System.out.println(forWithdrawal.holder.toString() + "(" + tokens[1] + ") Withdraw - insufficient fund.");}
            else {System.out.println(forWithdrawal.holder.toString() + "(" + tokens[1] + ") Withdraw - balance updated.");}
        }
    }

    /**
     * Handles the creation and operation of an account for the open method.
     * @param tokens Array that contains string representations of every token.
     */
    private void openHelper (String[] tokens) {
        Account account = createAccount(tokens);
        if (account != null) {
            if (this.accountDatabase.contains(account, "Open")) {
                System.out.println(account.holder.toString() + "(" + tokens[1] + ") is already in the database.");
                return;
            }
            this.accountDatabase.open(account);
            System.out.println(account.holder.toString() + "(" + tokens[1] + ") opened.");
        }
    }
    /**
     * Handles the creation and operation of an account for the close method.
     * @param tokens Array that contains string representations of every token.
     */
    private void closeHelper(String[] tokens) {
        Account accountToRemove = accountForClosing(tokens);
        if (accountToRemove != null) {
            if (!this.accountDatabase.contains(accountToRemove)) {
                System.out.println(accountToRemove.holder.toString() + "(" + tokens[1] + ") is not in the database.");
                return;
            }
            this.accountDatabase.close(accountToRemove);
            System.out.println(accountToRemove.holder.toString() + "(" + tokens[1] + ") has been closed.");
        }
    }

    /**
     * Reads from the command line and uses the information to determine which command to execute.
     * Can be halted by entering the command Q, which will set the instance variable to true and end input.
     * Can read multiple lines at once.
     * @param reader the scanner that will handle all the command line input.
     */
    private void takeInput(Scanner reader) {
        String result = reader.nextLine();
        if (result.equals("")) {
            result = reader.nextLine();
        }
        String[] tokens = result.split("\\s+");
        if (tokens[0].equals("Q")){notQ = false;}
        else if (tokens[0].equals("P")) {
            this.accountDatabase.printSorted();
        }
        else if (tokens[0].equals("PI")) {
            this.accountDatabase.printFeesAndInterests();
        }
        else if (tokens[0].equals("UB")) {
            this.accountDatabase.printUpdatedBalances();
        }
        else if (tokens[0].equals("D")) {
            depositHelper(tokens);
        }
        else if (tokens[0].equals("W")) {
            withdrawalHelper(tokens);
        }
        else if (tokens[0].equals("O")) {
            openHelper(tokens);
        }
        else if (tokens[0].equals("C")) {
            closeHelper(tokens);
        }
        else {
            if (!tokens[0].equals("")) {
                System.out.println("Invalid command!");
            }
        }
    }
}

