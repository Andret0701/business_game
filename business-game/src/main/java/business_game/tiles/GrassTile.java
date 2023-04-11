package business_game.tiles;

import game_engine.gfx.Sprite;
import game_engine.types.Tile;

public class GrassTile extends Tile {
    public GrassTile() {
        super(false, new Sprite("Tiles/Grass", 0, 0));
    }
}
