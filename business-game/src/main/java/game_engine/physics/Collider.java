package game_engine.physics;

import game_engine.types.Vector2;

public abstract class Collider {
    public boolean isTrigger;
    protected Vector2 offset;

    public Collider() {
    }

    public Vector2 getOffset() {
        return offset.copy();
    }

    public abstract void draw(double x, double y, double angle, double scale);

}
