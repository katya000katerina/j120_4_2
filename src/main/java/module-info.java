module org.csvviewer {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.csvviewer to javafx.fxml;
    exports org.csvviewer;
}