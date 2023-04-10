package business_game;

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
