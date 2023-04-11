package business_game;

import business_game.scenes.GrassLandScene;
import game_engine.Game;

public class BusinessGame extends Game {
    public BusinessGame() {
        super();
        addScene(new GrassLandScene());
        setScene("Grass Land");
        setGravity(0, 0);
        draw_colliders = true;
    }
}
