module com.desarrollo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media; // Añadido para usar Media y MediaPlayer

    opens com.desarrollo to javafx.fxml;
    opens com.desarrollo.controllers to javafx.fxml;
    exports com.desarrollo;
}