package business_game.game_engine.entity;

import business_game.game_engine.interfaces.Updateable;
import business_game.game_engine.types.Transform;
import business_game.game_engine.types.Vector2;

public abstract class Entity implements Updateable {
    protected Transform transform;

    public Entity() {
        this.transform = new Transform(0, 0, 1, 0);
    }

    public Transform getTransform() {
        return transform;
    }

    public abstract Entity copy();

    public Entity copy(double x, double y) {
        Entity entity = this.copy();
        entity.getTransform().setPosition(new Vector2(x, y));
        return entity;
    }

    @Override
    public void update(double delta_time) {

    }

    @Override
    public String toString() {
        return "Entity { " + transform + " }";
    }
}