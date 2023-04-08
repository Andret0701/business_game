package business_game.scenes;

import business_game.entities.GrassTilemap;
import business_game.game_engine.Scene;
import business_game.game_engine.Tilemap;

public class GrassLandScene extends Scene {
    public GrassLandScene() {
        super("GrassLandScene");
        Tilemap tilemap = (Tilemap) create(new GrassTilemap(), 0, 0);
    }

}
