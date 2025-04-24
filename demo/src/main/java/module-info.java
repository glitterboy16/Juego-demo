module com.desarrollo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.desarrollo to javafx.fxml;
    opens com.desarrollo.controllers to javafx.fxml;
    exports com.desarrollo;
}
