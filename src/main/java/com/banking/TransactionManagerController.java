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
    private RadioButton campusNB;
    @FXML
    private RadioButton campusNW;
    @FXML
    private RadioButton campusCA;
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
    protected void onHelloButtonClick() {
        openConsole.setText("Welcome to JavaFX Application!");
        System.out.println("Hola ninos!");
    }

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
    protected void getOpenFullName() {
        System.out.print(openFirstName.getText() + " " + openLastName.getText());
    }
    @FXML
    protected void getOpenDate() {
        if (openDOB.getValue() != null) {
            String date = openDOB.getValue().toString();
            String year = date.substring(0,4);
            String month = date.substring(5,7);
            String day = date.substring(8);
            Date openDate = new Date(month + "/" + day + "/" + year);
            System.out.println(openDate);
        }
    }
    @FXML
    protected void getInitialDeposit() {
        int initDeposit = Integer.parseInt(initialDeposit.getText());
        System.out.println(initDeposit);
    }
    @FXML
    protected void getWithdrawFirstName() {
        System.out.print(withdrawFirstName.getText());
    }

    @FXML
    protected void getWithdrawLastName() {
        System.out.println(withdrawLastName.getText());
    }

    @FXML
    protected void getWithdrawDOB() {
        if (withdrawDOB.getValue() != null) {
            String date = withdrawDOB.getValue().toString();
            String year = date.substring(0,4);
            String month = date.substring(5,7);
            String day = date.substring(8);
            Date withdrawDate = new Date(month + "/" + day + "/" + year);
            System.out.println(withdrawDate);
        }
    }

    @FXML
    protected void getAmount() {
        int amount = Integer.parseInt(changeAmount.getText());
        System.out.println(amount);
    }



}