module com.example.servidor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.servidor to javafx.fxml;
    exports com.example.servidor;
}