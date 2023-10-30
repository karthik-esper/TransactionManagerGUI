package com.banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class TransactionManagerController {
    private AccountDatabase accountDatabase = new AccountDatabase();

    @FXML
    private Label welcomeText;
    @FXML
    private RadioButton AccountOpener;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        System.out.println("Hola ninos!");
    }
    @FXML
    protected void Warning() {
        welcomeText.setText("Why'd you push it man?");
    }

    @FXML
    protected void openAccount() {
        Account account = new Checking(new Profile("Karthik", "Gogogogo", new Date("05/23/2004")),100);
        accountDatabase.open(account);
        System.out.println(account.toString());
    }

}