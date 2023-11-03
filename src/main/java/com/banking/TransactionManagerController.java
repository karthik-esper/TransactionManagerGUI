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
    protected void getOpenFullName() {
        System.out.print(openFirstName.getText());
        System.out.print(" " + openLastName.getText());
    }
    @FXML
    protected void getOpenDate() {
        if (openDOB.getValue() != null) {
            String date = openDOB.getValue().toString();
            String year = date.substring(0,3);
            String month = date.substring(5,6);
            String day = date.substring(8);
            System.out.println(date);
            System.out.println(year + "," + month + "," + day);
        }
    }





}