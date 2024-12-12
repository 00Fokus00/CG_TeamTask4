module com.cgvsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires static lombok;


    opens com.cgvsu to javafx.fxml;
    exports com.cgvsu;
}