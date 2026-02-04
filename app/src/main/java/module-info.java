module programa {
    requires javafx.controls;
    requires javafx.fxml;

    opens programa to javafx.fxml;

    exports programa;
    exports programa.controller to javafx.fxml;
    exports programa.model;

    opens programa.model to javafx.fxml;
    opens programa.controller to javafx.fxml;
}
