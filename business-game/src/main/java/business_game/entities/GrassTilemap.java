package business_game.entities;

import business_game.game_engine.Tilemap;
import business_game.game_engine.Sprite;

public class GrassTilemap extends Tilemap {
    public GrassTilemap() {
        super(200, 200);
        fill(new Sprite("Tiles/grass", 0, 0));
        for (int i = 0; i < width; i++) {
            setTile(i, 5, new Sprite("Tiles/RoadGrassTop", 0, 0));
            setTile(i, 4, new Sprite("Tiles/RoadTop", 0, 0));
            setTile(i, 3, new Sprite("Tiles/Road", 0, 0));
            setTile(i, 2, new Sprite("Tiles/RoadBottom", 0, 0));
            setTile(i, 1, new Sprite("Tiles/RoadGrassBottom", 0, 0));
        }
    }
}
