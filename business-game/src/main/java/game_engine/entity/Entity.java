package game_engine.entity;

import java.util.ArrayList;

import game_engine.interfaces.Updateable;
import game_engine.types.Transform;
import game_engine.types.Vector2;

public abstract class Entity implements Updateable {
    protected Transform transform;
    protected ArrayList<String> tags = new ArrayList<String>();

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

    public void addTag(String tag) {
        tags.add(tag);
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    @Override
    public String toString() {
        return "Entity { " + transform + " }";
    }
}