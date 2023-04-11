package game_engine.types;

import game_engine.gfx.Sprite;

public class Tile {
    public final boolean solid;
    public final Sprite sprite;

    public Tile(boolean solid, Sprite sprite) {
        this.solid = solid;
        this.sprite = sprite;
    }

}
