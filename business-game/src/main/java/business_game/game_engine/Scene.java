package business_game.game_engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import business_game.game_engine.entity.Entity;
import business_game.game_engine.types.Vector2;

public class Scene implements Iterable<Entity> {
    protected String name;

    public Scene(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private Collection<Entity> entities = new ArrayList<>();

    protected void add(Entity entity) {
        entities.add(entity);
    }

    protected void add(Entity entity, double x, double y) {
        entity.getTransform().setPosition(new Vector2(x, y));
        entities.add(entity);
    }

    @Override
    public Iterator<Entity> iterator() {
        return entities.iterator();
    }

}
