package business_game.game_engine;

import java.util.HashMap;
import java.util.Vector;
import java.util.Comparator;

import business_game.Camera;
import business_game.Entity;
import business_game.game_engine.utils.DrawEntity;

import java.util.stream.Collectors;

public class Scene {
    protected Camera main_camera;

    public Scene(String name) {
        this.name = name;
        main_camera = (Camera) create(new Camera(1), 0, 0);
    }

    protected String name;

    public String getName() {
        return name;
    }

    protected HashMap<Entity, Entity> entities = new HashMap<Entity, Entity>();
    protected HashMap<DrawEntity, DrawEntity> drawEntities = new HashMap<DrawEntity, DrawEntity>();

    public Entity create(Entity entity, double x, double y) {
        entity = entity.copy(x, y);
        entities.put(entity, entity);
        if (entity instanceof DrawEntity)
            drawEntities.put((DrawEntity) entity, (DrawEntity) entity);
        return entity;
    }

    public void destroy(Entity entity) {
        entities.remove(entity);
        if (entity instanceof DrawEntity)
            drawEntities.remove((DrawEntity) entity);
    }

    public void update() {
        for (Entity entity : entities.values())
            entity.update();
    }

    public void draw() {
        Vector<DrawEntity> drawEntitiesList = getSortedDrawEntities();
        for (DrawEntity entity : drawEntitiesList)
            entity.draw();
    }

    private Vector<DrawEntity> getSortedDrawEntities() {
        Vector<DrawEntity> drawEntitiesList = new Vector<>(
                drawEntities.values().stream()
                        .filter(e -> e.visible)
                        .sorted(Comparator.comparingInt(DrawEntity::getLayer)).collect(Collectors.toList()));

        return drawEntitiesList;
    }

    public Camera getMainCamera() {
        return main_camera;
    }

}
