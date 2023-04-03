module business_game {
    requires javafx.controls;
    requires javafx.fxml;

    opens business_game to javafx.fxml;
    exports business_game;
}
