module business_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive org.dyn4j;

    opens business_game to javafx.fxml;

    exports business_game;
    exports game_engine;
}