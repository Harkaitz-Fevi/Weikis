module programa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base; // Asegúrate de que esta línea esté presente

    opens programa to javafx.fxml;
    exports programa;

    opens programa.controller to javafx.fxml;
    exports programa.controller;

    // CAMBIO AQUÍ: Abrir a javafx.base permite que la tabla lea los atributos
    opens programa.model to javafx.fxml, javafx.base; 
    exports programa.model;
}