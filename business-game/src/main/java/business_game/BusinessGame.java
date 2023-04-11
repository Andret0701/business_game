package business_game;

import business_game.game_engine.Game;
import business_game.scenes.GrassLandScene;

public class BusinessGame extends Game {
    public BusinessGame() {
        name = "Business Game";
        addScene(new GrassLandScene());
        setScene("Grass Land");
    }
}
