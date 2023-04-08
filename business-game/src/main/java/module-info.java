module business_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens business_game to javafx.fxml;

    exports business_game;
    exports business_game.game_engine;
    exports business_game.game_engine.utils;
    exports business_game.game_engine.components;
    exports business_game.game_engine.managers;
}
