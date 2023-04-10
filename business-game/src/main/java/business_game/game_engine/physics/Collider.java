package business_game.game_engine.physics;

import business_game.game_engine.types.Vector2;

public abstract class Collider {
    public boolean isTrigger;
    protected Vector2 offset;

    public Collider() {
    }

    public Vector2 getOffset() {
        return offset.copy();
    }

    public void setScale(double scale) {
        // TODO: implement
    }

}
