module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.zxing;
    requires java.desktop;
    requires org.neo4j.driver;

    opens org.example to javafx.fxml;
    exports org.example;
}
