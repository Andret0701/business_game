package business_game;

public abstract class Entity implements Updateable {
    public Transform transform = new Transform();

    public Entity() {
    }

    public Entity(double x, double y) {
        transform.setPosition(new Vector2(x, y));
    }

    @Override
    public void update(double delta_time) {

    }

    public Entity copy() {
        return this;
    }

    public Entity copy(double x, double y) {
        Entity entity = copy();
        entity.transform.setPosition(new Vector2(x, y));
        return entity;
    }
}
