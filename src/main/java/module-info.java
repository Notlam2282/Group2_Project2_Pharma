module com.example.pharmacy {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires java.net.http;

    opens com.example.pharmacy to javafx.fxml;
    exports com.example.pharmacy;
    exports com.example.pharmacy.Model;
    opens com.example.pharmacy.Model to javafx.fxml;
}