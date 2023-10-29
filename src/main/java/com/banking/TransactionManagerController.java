package com.banking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransactionManagerController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        System.out.println("Hola ninos!");
    }
    @FXML
    protected void Warning() {
        welcomeText.setText("Why'd you push it man?");
    }
}