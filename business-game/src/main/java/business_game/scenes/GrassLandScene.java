package business_game.scenes;

import business_game.game_engine.Scene;
import business_game.game_engine.Tilemap;
import business_game.game_engine.Sprite;

public class GrassLandScene extends Scene {
    public GrassLandScene() {
        super("GrassLandScene");
        Tilemap tilemap = (Tilemap) create(new Tilemap(2000, 2000), 0, 0);
        tilemap.fill(new Sprite("Tiles/grass", 0, 0));
        for (int i = 0; i < tilemap.getWidth(); i++) {
            tilemap.setTile(i, 5, new Sprite("Tiles/RoadGrassTop", 0, 0));
            tilemap.setTile(i, 4, new Sprite("Tiles/RoadTop", 0, 0));
            tilemap.setTile(i, 3, new Sprite("Tiles/Road", 0, 0));
            tilemap.setTile(i, 2, new Sprite("Tiles/RoadBottom", 0, 0));
            tilemap.setTile(i, 1, new Sprite("Tiles/RoadGrassBottom", 0, 0));
        }
    }

}
