package business_game.game_engine.utils;

public abstract class Entity {
    public Entity() {

    }

    public void update() {
    }

    protected Vector2 position = new Vector2(0, 0);

    public Vector2 getPosition() {
        return position.copy();
    }

    public void setPosition(Vector2 position) {
        this.position = position.copy();
    }

    public Entity copy() {
        return this;
    }

    public Entity copy(double x, double y) {
        Entity entity = copy();
        entity.setPosition(new Vector2(x, y));
        return entity;
    }
}
