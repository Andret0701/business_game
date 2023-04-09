package business_game.game_engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import business_game.Entity;

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

    @Override
    public Iterator<Entity> iterator() {
        return entities.iterator();
    }

}
