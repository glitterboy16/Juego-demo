module com.desarrollo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.desarrollo to javafx.fxml;
    exports com.desarrollo;
}
