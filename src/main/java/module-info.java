module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.zxing;
    requires java.desktop;

    opens org.example to javafx.fxml;
    exports org.example;
}