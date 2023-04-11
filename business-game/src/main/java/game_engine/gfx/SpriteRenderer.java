package game_engine.gfx;

import game_engine.types.Vector2;

public class SpriteRenderer {
    public Sprite sprite;
    public Vector2 offset = new Vector2(0, 0);
    public boolean flip_x = false;
    public boolean flip_y = false;

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
    }

    public SpriteRenderer() {
    }

}
