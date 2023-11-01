package com.banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

public class TransactionManagerController {
    private AccountDatabase accountDatabase = new AccountDatabase();

    @FXML
    private TextArea openConsole;

    @FXML
    private TextArea accountDatabaseOutput;


    @FXML
    protected void onHelloButtonClick() {
        openConsole.setText("Welcome to JavaFX Application!");
        System.out.println("Hola ninos!");
    }

    @FXML
    protected void printAccounts() {
        //accountDatabaseOutput.setText(accountDatabase.printSorted());
        accountDatabaseOutput.setText("this kinda works?");
    }
    @FXML
    protected void Warning() {
        openConsole.setText("Why'd you push it man?");
    }

}