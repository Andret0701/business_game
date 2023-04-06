package business_game.game_engine.utils;

public abstract class DrawEntity extends Entity {
    public boolean visible = true;

    public void draw() {
    }

    public int getLayer() {
        return 0;
    }
}
