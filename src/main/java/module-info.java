module com.example.helbhotel {
    requires javafx.controls;
    requires javafx.fxml;



    exports com.example.helbhotel.main;
    opens com.example.helbhotel.main to javafx.fxml;
}