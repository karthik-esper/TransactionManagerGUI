package com.banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TransactionManagerController {
    private AccountDatabase accountDatabase = new AccountDatabase();

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
    @FXML
    private TextArea openConsole;
    @FXML
    private TextArea withdrawConsole;
    @FXML
    private TextArea accountDatabaseOutput;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField initialDeposit;
    @FXML
    private TextField changeAmount;
    @FXML
    private ToggleGroup AccountType;
    @FXML
    private ToggleGroup CampusType;
    @FXML
    private ToggleGroup WithdrawAccountType;

    @FXML
    protected void printAccounts() {
        accountDatabaseOutput.clear();
        accountDatabaseOutput.setText(accountDatabase.printSorted());
    }

    @FXML
    protected void printFeesAndInterest() {
        accountDatabaseOutput.clear();
        accountDatabaseOutput.setText(accountDatabase.printFeesAndInterests());
    }

    @FXML
    protected void applyFeesAndInterest() {
        accountDatabaseOutput.clear();
        accountDatabaseOutput.setText(accountDatabase.printUpdatedBalances());
    }

    @FXML
    protected void clearOutput() {
        accountDatabaseOutput.clear();
    }


    @FXML
    protected void Warning() {
        openConsole.setText("Why'd you push it man?");
    }
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

    @FXML
    protected String getOpenFirstName() {
        return openFirstName.getText();
    }

    @FXML
    protected String getOpenLastName() {
        return openLastName.getText();
    }
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
    @FXML
    protected void onOpenClick() {
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
                accountDatabase.open(newAcc);
            }
            else {
                return;
            }
        }
        else {
            openConsole.setText("Button not selected for account type, please do so.");
            return;
        }

        openFirstName.clear();
        openLastName.clear();
        openDOB.getEditor().clear();
        initialDeposit.clear();
    }
    @FXML
    protected void onCloseClick() {
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
            }
            else {
                openConsole.setText("Account not closed, account not found in database.");
                return;
            }
        }
        openFirstName.clear();
        openLastName.clear();
        openDOB.getEditor().clear();
        initialDeposit.clear();
    }

    @FXML
    protected void openClearClick() {
        openFirstName.clear();
        openLastName.clear();
        openDOB.getEditor().clear();
        initialDeposit.clear();

    }

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

    private Account createAccount(Profile holder, double deposit) {
        if (getInitialDeposit() < 0) {
            openConsole.setText("No balance entered, please enter an initial deposit.");
            return null;
        }
        if (checkingButton.isSelected() || collegeCheckingButton.isSelected()) {
            Account newAccount = createChecking(holder, deposit);
            return newAccount;
        }
        else {
            Account newAccount = createSavings(holder, deposit);
            return newAccount;
        }
    }

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

    private Account createChecking(Profile holder, double deposit){
        if (checkingButton.isSelected()) {
            openConsole.clear();
            openConsole.setText("Checking account created");
            return new Checking(holder, deposit);
        }
        else {
            if (CampusType.getSelectedToggle() != null) {
                if (!validAge(getOpenDate().toString(), "Overload")){
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
     * This one creates account for closing
     * @param holder
     * @return
     */
    private Account createChecking(Profile holder){
        if (checkingButton.isSelected()) {
            openConsole.clear();
            return new Checking(holder, 0);
        }
        else {
            if (CampusType.getSelectedToggle() != null) {
                if (!validAge(getOpenDate().toString(), "Overload")){
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


    @FXML
    protected int getInitialDeposit() {
        if (!initialDeposit.getText().isEmpty()) {
            int initDeposit = Integer.parseInt(initialDeposit.getText());
            return initDeposit;
        }
        else {
            return -1;
        }


    }
    @FXML
    protected String getWithdrawFirstName() {
        return withdrawFirstName.getText();
    }

    @FXML
    protected String getWithdrawLastName() {
        return withdrawLastName.getText();
    }

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

    @FXML
    protected void getAmount() {
        int amount = Integer.parseInt(changeAmount.getText());
        System.out.println(amount);
    }



}