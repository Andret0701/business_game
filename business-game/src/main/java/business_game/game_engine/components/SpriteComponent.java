package business_game.game_engine.components;

import business_game.Vector2;
import business_game.game_engine.Game;
import business_game.game_engine.Sprite;
import business_game.game_engine.utils.Entity;

public class SpriteComponent extends DrawComponent {
    public Sprite sprite;
    public Vector2 offset;
    public boolean flip_x;
    public boolean flip_y;

    public SpriteComponent(Entity parent, double layer, Sprite sprite, double offset_x, double offset_y, boolean flip_x,
            boolean flip_y) {
        super(parent, layer);
        this.sprite = sprite;
        this.offset = new Vector2(offset_x, offset_y);
        this.flip_x = flip_x;
        this.flip_y = flip_y;
    }

    public SpriteComponent(Entity parent, double layer, Sprite sprite, int offset_x, int offset_y) {
        this(parent, layer, sprite, offset_x, offset_y, false, false);
    }

    public SpriteComponent(Entity parent, double layer, Sprite sprite) {
        this(parent, layer, sprite, 0, 0);
    }

    @Override
    public void draw() {
        if (sprite == null)
            return;
        Vector2 position = parent.getPosition();
        position.add(offset);
        Game.instance.draw.sprite(sprite, position.x, position.y, flip_x, flip_y);
    }
}
