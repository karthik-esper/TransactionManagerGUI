package com.banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;

/**
 * Controller class for the JavaFX project.
 * Handles cases of invalid and valid user input.
 * Allows the user to run every command that was part of Transaction Manager.
 * @author Karthik Gangireddy, Vineal Sunkara
 */
public class TransactionManagerController {
    private AccountDatabase accountDatabase = new AccountDatabase(); // default database

    // Buttons to factor in selected inputs
    @FXML
    private RadioButton checkingButton;
    @FXML
    private RadioButton collegeCheckingButton;
    @FXML
    private RadioButton savingsButton;
    @FXML
    private RadioButton moneyMarket;
    @FXML
    private RadioButton withdrawCheckingButton;
    @FXML
    private RadioButton withdrawCollegeCheckingButton;
    @FXML
    private RadioButton withdrawSavingsButton;
    @FXML
    private RadioButton withdrawMoneyMarket;
    @FXML
    private RadioButton campusNB;
    @FXML
    private RadioButton campusNW;
    @FXML
    private RadioButton campusCA;
    @FXML
    private RadioButton loyaltyButton;
    // Textfields and pickers for profile information
    @FXML
    private TextField openFirstName;
    @FXML
    private TextField openLastName;
    @FXML
    private TextField withdrawFirstName;
    @FXML
    private TextField withdrawLastName;
    @FXML
    private DatePicker openDOB;
    @FXML
    private DatePicker withdrawDOB;
    // Consoles for output
    @FXML
    private TextArea openConsole;
    @FXML
    private TextArea withdrawConsole;
    @FXML
    private TextArea accountDatabaseOutput;
    // Fields for money amounts
    @FXML
    private TextField initialDeposit;
    @FXML
    private TextField changeAmount;
    // Groups for the account stuff
    @FXML
    private ToggleGroup AccountType;
    @FXML
    private ToggleGroup CampusType;
    @FXML
    private ToggleGroup withdrawAccountType;

    /**
     * Prints accounts in sorted order to the console.
     */
    @FXML
    protected void printAccounts() {
        accountDatabaseOutput.clear();
        accountDatabaseOutput.setText(accountDatabase.printSorted());
    }

    /**
     * Prints accounts along with their fees and interest to the console.
     */
    @FXML
    protected void printFeesAndInterest() {
        accountDatabaseOutput.clear();
        accountDatabaseOutput.setText(accountDatabase.printFeesAndInterests());
    }

    /**
     * Applies the fees and interests and calculates new ones, which it then prints to the console.
     */
    @FXML
    protected void applyFeesAndInterest() {
        accountDatabaseOutput.clear();
        accountDatabaseOutput.setText(accountDatabase.printUpdatedBalances());
    }

    @FXML
    protected void loadFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open bankAccounts.txt");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Add extension filters (optional)
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        // Show open file dialog and get the selected file
        // You need to provide a window for the dialog. If you have a reference to the current stage, use it here.
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Do something with the selected file
            System.out.println("File selected: " + file.getAbsolutePath());
        }
    }

    /**
     * Handles only allowing you to select a campus if you are making a College Checkings account.
     */
    @FXML
    protected void campusSelector() {
        if(collegeCheckingButton.isSelected()) {
            campusCA.setDisable(false);
            campusNW.setDisable(false);
            campusNB.setDisable(false);
        }
        else {
            campusCA.setDisable(true);
            campusCA.setSelected(false);
            campusNB.setDisable(true);
            campusNB.setSelected(false);
            campusNW.setDisable(true);
            campusNW.setSelected(false);
        }
    }

    /**
     * Handles all the operations needed to open an account.
     * Factors in possible errors such as invalid dates, empty fields, and improper entries.
     * Opens the account after checking that it is not a duplicate.
     */
    @FXML
    protected void onOpenClick() {
        depositWithdrawClear();
        if (AccountType.getSelectedToggle() != null) {
            if (getOpenFirstName().equals("") || getOpenLastName().equals("")) {
                openConsole.setText("Invalid name, either first name or last name is empty");
                return;
            }
            if (getOpenDate() == null) {
                openConsole.setText("The date entered is not valid!");
                return;
            }
            if (!validAge(getOpenDate().toString())) {
                return;
            }
            Profile holder = new Profile(getOpenFirstName(), getOpenLastName(), getOpenDate());
            Account newAcc = createAccount(holder, getInitialDeposit());
                if (newAcc != null) {
                    if (!accountDatabase.contains(newAcc)) {
                        accountDatabase.open(newAcc);
                    }
                    else {
                        openConsole.setText("Account is already in database.");
                        return;
                    }
            }
            else {return;}
        }
        else {
            openConsole.setText("Button not selected for account type, please do so.");
            return;
        }
        openClearClick();
    }

    /**
     * Handles all the operations needed to close an account.
     * Factors in possible errors such as invalid dates, empty fields, and improper entries.
     * Closes account after checking that it is in the database.
     */
    @FXML
    protected void onCloseClick() {
        depositWithdrawClear();
        if (AccountType.getSelectedToggle() != null) {
            if (getOpenDate() == null) {
                openConsole.setText("The date entered is not valid!");
                return;
            }
            Profile holder = new Profile(getOpenFirstName(), getOpenLastName(), getOpenDate());
            if (AccountType.getSelectedToggle() != null) {
                Account newAcc = createAccount(holder);
                if (accountDatabase.contains(newAcc)) {
                    if (accountDatabase.close(newAcc)) {
                        openConsole.setText("Account successfully closed!");
                    }
                } else {
                    openConsole.setText("Account not closed, account not found in database.");
                    return;
                }
            }
        }
        else {
            openConsole.setText("Button not selected for account type, please do so.");
            return;
        }
        openClearClick();
    }

    /**
     * Clears all fields in the open/close window.
     */
    @FXML
    protected void openClearClick() {
        openFirstName.clear();
        openLastName.clear();
        openDOB.getEditor().clear();
        initialDeposit.clear();
        AccountType.selectToggle(null);
        CampusType.selectToggle(null);
        loyaltyButton.setSelected(false);
    }

    /**
     * Handles all operations necessary to deposit into an account.
     * Creates a temporary account checking that all parameters are valid, in order to deposit.
     * Ensures that invalid balances or non-existent accounts will not be part of the operations.
     */
    @FXML
    protected void depositClick() {
        openClearClick();
        if (withdrawAccountType.getSelectedToggle() != null) {
            if (getWithdrawFirstName().equals("") || getWithdrawLastName().equals("")) {
                withdrawConsole.setText("Invalid name, either first name or last name is empty");
                return;
            }
            Profile holder = new Profile(getWithdrawFirstName(), getWithdrawLastName(), getWithdrawDOB());
            double newBalanceAmount = getAmount();
            Account tempAccount = createAccount(holder, newBalanceAmount);
            if (tempAccount != null){
                if (accountDatabase.contains(tempAccount)) {
                    accountDatabase.deposit(tempAccount);
                    withdrawConsole.setText("Amount Deposited: " + newBalanceAmount);
                } else {
                    withdrawConsole.setText("Account not found!");
                    return;
                }
            }
        }
        else {
            withdrawConsole.setText("Button not selected for account type, please do so.");
            return;
        }
        depositWithdrawClear();
    }
    /**
     * Handles all operations necessary to withdraw an account.
     * Creates a temporary account checking that all parameters are valid, in order to withdraw.
     * Ensures that invalid balances or insufficient balances will not allow the operation to go through.
     */
    @FXML
    protected void withdrawClick() {
        openClearClick();
        if (withdrawAccountType.getSelectedToggle() != null) {
            if (getWithdrawFirstName().equals("") || getWithdrawLastName().equals("")) {
                withdrawConsole.setText("Invalid name, either first name or last name is empty");
                return;
            }
            Profile holder = new Profile(getWithdrawFirstName(), getWithdrawLastName(), getWithdrawDOB());
            double newBalanceAmount = getAmount();
            Account tempAccount = createAccount(holder, newBalanceAmount);
            if (tempAccount != null) {
                if (accountDatabase.contains(tempAccount)) {
                    if (accountDatabase.withdraw(tempAccount)) {
                        withdrawConsole.setText("Amount Withdrawn: " + newBalanceAmount);
                    }
                    else {
                        withdrawConsole.setText("Insufficient funds to withdraw.");
                        return;
                    }
                }
                else {
                    withdrawConsole.setText("Account not found!");
                    return;
                }
            }
        }
        else {
            withdrawConsole.setText("Button not selected for account type, please do so.");
            return;
        }
        depositWithdrawClear();
    }

    /**
     * Clears all fields in the deposit/withdraw tab.
     */
    @FXML
    protected void depositWithdrawClear() {
        withdrawFirstName.clear();
        withdrawLastName.clear();
        withdrawDOB.getEditor().clear();
        changeAmount.clear();
        withdrawAccountType.selectToggle(null);
    }

    /**
     * Gets the first name to open/close an account
     * @return the text in the first name box for the open/close tab
     */
    @FXML
    protected String getOpenFirstName() {
        return openFirstName.getText();
    }

    /**
     * Gets the last name to open/close an account
     * @return the text in the last name box for the open/close tab
     */
    @FXML
    protected String getOpenLastName() {
        return openLastName.getText();
    }

    /**
     * Gets the date from the date picker in open/close.
     * @return the Date in the date picker in the open/close tab.
     */
    @FXML
    protected Date getOpenDate() {
        if (openDOB.getValue() != null) {
            String date = openDOB.getValue().toString();
            String year = date.substring(0,4);
            String month = date.substring(5,7);
            String day = date.substring(8);
            Date openDate = new Date(month + "/" + day + "/" + year);
            return openDate;
        }
        return null;
    }

    /**
     * Gets the first name to withdraw/deposit from the first name textfield.
     * @return the first name to deposit/withdraw.
     */
    @FXML
    protected String getWithdrawFirstName() {
        return withdrawFirstName.getText();
    }
    /**
     * Gets the last name to withdraw/deposit from the last name textfield.
     * @return the last name to deposit/withdraw.
     */
    @FXML
    protected String getWithdrawLastName() {
        return withdrawLastName.getText();
    }

    /**
     * Gets the date from the date picker in deposit/withdraw.
     * @return the Date in the date picker in the deposit/withdraw tab.
     */
    @FXML
    protected Date getWithdrawDOB() {
        if (withdrawDOB.getValue() != null) {
            String date = withdrawDOB.getValue().toString();
            String year = date.substring(0,4);
            String month = date.substring(5,7);
            String day = date.substring(8);
            Date withdrawDate = new Date(month + "/" + day + "/" + year);
            return withdrawDate;
        }
        return null;
    }

    /**
     * Gets the number for the initial deposit box while also checking for invalid input.
     * @return the number from the deposit box.
     */
    @FXML
    protected double getInitialDeposit() {
        if (!initialDeposit.getText().isEmpty()) {
            try {
                Double.parseDouble(initialDeposit.getText());
            } catch (NumberFormatException e) {
                return -1;
            }
            double initDeposit = Double.parseDouble(initialDeposit.getText());
            return initDeposit;
        }
        else {
            return -1;
        }
    }
    /**
     * Gets the number from the change amount box while also checking for invalid input.
     * @return the number to deposit/withdraw into account.
     */
    @FXML
    protected double getAmount() {
        if (!changeAmount.getText().isEmpty()) {
            try {
                Double.parseDouble(changeAmount.getText());
            } catch (NumberFormatException e) {
                return -1;
            }
            double changeAmt = Double.parseDouble(changeAmount.getText());
            return changeAmt;
        }
        else {
            return -1;
        }
    }

    /**
     * Creates an account given a holder and deposit.
     * @param holder the profile of the holder of the account.
     * @param deposit the amount of either the initial deposit or the operation to be done.
     * @return an account if able to be created, null otherwise.
     */
    private Account createAccount(Profile holder, double deposit) {
        if (getInitialDeposit() < 0 && getAmount() < 0) {
            openConsole.setText("Balance entered is either empty or invalid, please try again.");
            withdrawConsole.setText("Balance entered is either empty or invalid, please try again.");
            return null;
        }
        if (checkingButton.isSelected() || collegeCheckingButton.isSelected()) {
            Account newAccount = createChecking(holder, deposit);
            return newAccount;
        }
        else if (withdrawCheckingButton.isSelected() || withdrawCollegeCheckingButton.isSelected()){
            Account newAccount = createCheckingOperation(holder, deposit);
            return newAccount;
        }
        else if (withdrawSavingsButton.isSelected() || withdrawMoneyMarket.isSelected()) {
            Account newAccount = createSavingsOperation(holder, deposit);
            return newAccount;
        }
        else {
            Account newAccount = createSavings(holder, deposit);
            return newAccount;
        }
    }
    /**
     * Creates an account given only the holder, mainly for closing operations.
     * @param holder the profile of the holder of the account.
     * @return an account if able to be created, null otherwise.
     */
    private Account createAccount(Profile holder) {
        if (checkingButton.isSelected() || collegeCheckingButton.isSelected()) {
            Account newAccount = createChecking(holder);
            return newAccount;
        }
        else {
            Account newAccount = createSavings(holder);
            return newAccount;
        }
    }

    /**
     * Creates a checking/college checking account based on more specific fields to select.
     * @param holder profile of the holder of the account.
     * @param deposit the amount to deposit into the account.
     * @return an account if able to be made, null otherwise.
     */
    private Account createChecking(Profile holder, double deposit){
        if (checkingButton.isSelected()) {
            openConsole.clear();
            openConsole.setText("Checking account created");
            return new Checking(holder, deposit);
        }
        else {
            if (CampusType.getSelectedToggle() != null) {
                if (!validAge(holder.getDob().toString(), "Overload")){
                    return null;
                }
                if (campusNB.isSelected()) {
                    openConsole.clear();
                    openConsole.setText("College Checking account created");
                    return new CollegeChecking(holder, deposit, Campus.NEW_BRUNSWICK);
                }
                else if (campusNW.isSelected()) {
                    openConsole.clear();
                    openConsole.setText("College Checking account created");
                    return new CollegeChecking(holder, deposit, Campus.NEWARK);
                }
                else if (campusCA.isSelected()) {
                    openConsole.clear();
                    openConsole.setText("College Checking account created");
                    return new CollegeChecking(holder, deposit, Campus.CAMDEN);
                }
            }
            openConsole.setText("Account could not be created, campus not selected");
            return null;
        }
    }

    /**
     * This one creates checking/college checking account for closing, doesn't need deposit.
     * @param holder profile of the account holder.
     * @return account if it can be created, null otherwise.
     */
    private Account createChecking(Profile holder){
        if (checkingButton.isSelected()) {
            openConsole.clear();
            return new Checking(holder, 0);
        }
        else {
            if (CampusType.getSelectedToggle() != null) {
                if (!validAge(holder.getDob().toString(), "Overload")){
                    return null;
                }
                if (campusNB.isSelected()) {
                    openConsole.clear();
                    return new CollegeChecking(holder, 0, Campus.NEW_BRUNSWICK);
                }
                else if (campusNW.isSelected()) {
                    openConsole.clear();
                    return new CollegeChecking(holder, 0, Campus.NEWARK);
                }
                else if (campusCA.isSelected()) {
                    openConsole.clear();
                    return new CollegeChecking(holder, 0, Campus.CAMDEN);
                }
            }
            openConsole.setText("Account could not be created, campus not selected");
            return null;
        }
    }

    /**
     * Creates a checking/college checking account with the intention of using for withdraw or deposit.
     * @param holder profile of the holder of the account.
     * @param deposit amount for the operation.
     * @return account if can be created, false otherwise.
     */
    private Account createCheckingOperation(Profile holder, double deposit){
        if (withdrawCheckingButton.isSelected()) {
            withdrawConsole.clear();
            return new Checking(holder, deposit);
        }
        else {
            withdrawConsole.clear();
            return new CollegeChecking(holder, deposit, Campus.NEW_BRUNSWICK);
        }
    }
    /**
     * Creates a savings/money market account based on more specific fields to select.
     * Checks loyalty, and for money market checks to see if balance is sufficient.
     * @param holder profile of the holder of the account.
     * @param deposit the amount to deposit into the account.
     * @return an account if able to be made, null otherwise.
     */
    private Account createSavings(Profile holder, double deposit) {
        if (savingsButton.isSelected()) {
            if (loyaltyButton.isSelected()) {
                openConsole.clear();
                openConsole.setText("Savings account created");
                return new Savings(holder, deposit, true);
            }
            else {
                openConsole.clear();
                openConsole.setText("Savings account created");
                return new Savings(holder, deposit, false);
            }
        }
        else {
            if (getInitialDeposit() >= 2000) {
                openConsole.clear();
                openConsole.setText("Money Market account created");
                return new MoneyMarket(holder, deposit);
            }
            else {
                openConsole.setText("Not enough money for initial deposit!");
                return null;
            }
        }
    }

    /**
     * Creates a savings/money market specifically for closing, does not worry about balance.
     * @param holder profile of the holder of the account.
     * @return an account if able to be made, null otherwise.
     */
    private Account createSavings(Profile holder) {
        if (savingsButton.isSelected()) {
            if (loyaltyButton.isSelected()) {
                openConsole.clear();
                return new Savings(holder, 0, true);
            }
            else {
                openConsole.clear();
                openConsole.setText("Savings account created");
                return new Savings(holder, 0, false);
            }
        }
        else {
            openConsole.clear();
            return new MoneyMarket(holder,0);
        }
    }

    /**
     * Creates a savings/money market account for the sake of withdraw/deposit.
     * @param holder profile of the holder of the account.
     * @param deposit the amount for the operation.
     * @return an account if able to be made, null otherwise.
     */
    private Account createSavingsOperation(Profile holder, double deposit) {
        if (withdrawSavingsButton.isSelected()) {
            withdrawConsole.clear();
            return new Savings(holder, deposit, true);
        }
        else{
            withdrawConsole.clear();
            return new MoneyMarket(holder,deposit);
        }
    }

    /**
     * Checks if the holder is too young to open an account.
     * @param date the date of birth of the user as a String.
     * @return true if valid age, false is too young.
     */
    private boolean validAge(String date) {
        Date Test = new Date(date);
        Date tooEarly = new Date ("11/6/2007");
        Date tooLate = new Date ("11/6/1999");
        if (tooEarly.compareTo(Test) == -1) {
            openConsole.setText("DOB invalid: "  + date + " under 16.");
            return false;
        }

        return true;
    }

    /**
     * Checks in the case of a college checkings account if the holder is too old.
     * @param date the date of birth of the user.
     * @param overload a string that signifies this overloaded method instead.
     * @return true is user is valid age, false if too young/old.
     */
    private boolean validAge(String date, String overload) {
        Date Test = new Date(date);
        Date tooEarly = new Date ("11/6/2007");
        Date tooLate = new Date ("11/6/1999");
        if (tooEarly.compareTo(Test) == -1) {
            openConsole.setText("DOB invalid: "  + date + " under 16.");
            return false;
        }
        else if (tooLate.compareTo(Test) == 1) {
            openConsole.setText("DOB invalid: "  + date + " over 24.");
            return false;
        }
        return true;
    }

}