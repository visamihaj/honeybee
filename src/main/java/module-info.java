module com.honeybee.honeybee {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.honeybee.honeybee to javafx.fxml;
    exports com.honeybee.honeybee;
}