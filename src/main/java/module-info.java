module com.banking {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.banking to javafx.fxml;
    exports com.banking;
}