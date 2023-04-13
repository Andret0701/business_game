package game_engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import game_engine.entity.Camera;
import game_engine.entity.Drawable;
import game_engine.entity.Entity;
import game_engine.entity.Interactable;
import game_engine.interfaces.Updateable;
import game_engine.managers.DataSaver;
import game_engine.managers.Draw;
import game_engine.managers.GameLoop;
import game_engine.managers.Input;
import game_engine.physics.Collider;
import game_engine.physics.PhysicsWorld;
import game_engine.types.Transform;
import game_engine.types.Vector2;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class Game implements Updateable {
    public static Game instance;

    public void setActive() {
        instance = this;
    }

    public int FPS = 60;
    public final GameLoop game_loop;
    public final Input input;
    private final PhysicsWorld physics_world;
    private final DataSaver data_saver = new DataSaver();

    public Game() {
        game_loop = new GameLoop(FPS, this);
        input = new Input();
        physics_world = new PhysicsWorld();
    }

    public void start() {
        game_loop.start();
    }

    // #region UPDATE MANAGER
    @Override
    public void update(double delta_time) {
        setActive();
        data_saver.setActive();
        input.update();

        loadScene();

        Draw.background(Color.BLACK);

        physics_world.update(delta_time);
        updateEntities(delta_time);
        updateDrawables();

        if (draw_colliders)
            drawColliders();
    }

    public void updateEntities(double delta_time) {
        List<Entity> entitiesCopy = new ArrayList<>(entities); // make a copy of the entities list
        for (Entity entity : entitiesCopy) { // iterate over the copy instead of the original list
            if (entity == null) {
                throw new NullPointerException("Entity is null");
            }
            entity.update(delta_time);
        }
    }

    Comparator<Drawable> drawableComparator = new Comparator<Drawable>() {
        @Override
        public int compare(Drawable d1, Drawable d2) {
            return Double.compare(d1.getLayer(), d2.getLayer());
        }
    };

    public void updateDrawables() {
        Collections.sort(drawEntities, drawableComparator);
        for (Drawable drawable : drawEntities) {
            if (!drawable.isVisable())
                continue;

            Transform transform = drawable.getTransform();
            Vector2 position = transform.getPosition();
            double scale = transform.getScale();
            double angle = transform.getAngle();

            position = main_camera.worldToScreen(position);
            scale = main_camera.zoomScreen(scale);
            angle = main_camera.rotateScreen(angle);

            drawable.draw(position.x, position.y, angle, scale);
        }
    }

    // #endregion

    // #region SCENE MANAGER
    protected HashMap<String, Scene> scenes = new HashMap<String, Scene>();
    private String scene_to_load = null;

    public void addScene(Scene scene) {
        scenes.put(scene.name, scene);
    }

    public void setScene(String scene_name) {
        if (!scenes.containsKey(scene_name))
            throw new IllegalArgumentException("Scene " + scene_name + " does not exist");
        scene_to_load = scene_name;
    }

    /**
     * 
     */
    private void loadScene() {
        if (scene_to_load == null)
            return;

        Scene scene = scenes.get(scene_to_load);
        scene.loadContent();
        destroyAll();

        boolean created_camera = false;
        for (Entity entity : scene) {
            Vector2 position = entity.getTransform().getPosition();
            Entity created = create(entity, position.x, position.y);
            if (created_camera == false && entity instanceof Camera) {
                setMainCamera((Camera) created);
                created_camera = true;
            }
        }
        scene_to_load = null;
    }
    // #endregion

    // #region ENTITY MANAGER
    private List<Entity> entities = new ArrayList<>();
    private List<Drawable> drawEntities = new ArrayList<>();
    private List<Interactable> interactableEntities = new ArrayList<>();

    public Entity create(Entity entity, double x, double y) {
        entity = entity.copy(x, y);

        entities.add(entity);
        if (entity instanceof Drawable)
            drawEntities.add((Drawable) entity);
        if (entity instanceof Interactable)
            createInteractable((Interactable) entity);
        return entity;
    }

    public void destroy(Entity entity) {
        if (!entities.contains(entity))
            throw new IllegalArgumentException("Entity does not exist");

        entities.remove(entity);
        if (entity instanceof Drawable)
            drawEntities.remove((Drawable) entity);
        if (entity instanceof Interactable)
            destroyInteractable((Interactable) entity);
    }

    public void destroyAll() {
        entities.clear();
        drawEntities.clear();
        for (Interactable interactable : interactableEntities)
            destroyInteractable(interactable);
    }

    private void createInteractable(Interactable interactable) {
        interactableEntities.add(interactable);
        physics_world.addRigidbody(interactable.getRigidbody());
    }

    private void destroyInteractable(Interactable interactable) {
        interactableEntities.remove(interactable);
        physics_world.removeRigidbody(interactable.getRigidbody());
    }
    // #endregion

    // #region CAMERA MANAGER
    protected Camera main_camera = new Camera(10);

    public Camera getMainCamera() {
        return main_camera;
    }

    public void setMainCamera(Camera camera) {
        main_camera = camera;
    }
    // #endregion

    // #region TAG MANAGER
    public List<Entity> getEntitiesWithTag(String tag) {
        List<Entity> entitiesWithTag = new ArrayList<>();
        for (Entity entity : entities)
            if (entity.hasTag(tag))
                entitiesWithTag.add(entity);
        return entitiesWithTag;
    }

    public Entity getEntityWithTag(String tag) {
        for (Entity entity : entities)
            if (entity.hasTag(tag))
                return entity;
        return null;
    }

    // #endregion

    // #region PHYSICS MANAGER
    public boolean draw_colliders = false;

    public void setGravity(double x, double y) {
        physics_world.setGravity(new Vector2(x, y));
    }

    public void drawColliders() {
        for (Interactable interactable : interactableEntities) {
            Transform transform = interactable.getTransform();
            Vector2 position = transform.getPosition();
            double scale = transform.getScale();
            double angle = transform.getAngle();

            for (Collider collider : interactable.getRigidbody().getColliders()) {
                Vector2 collider_position = position.copy();
                collider_position.add(collider.getOffset());
                collider_position = main_camera.worldToScreen(collider_position);
                scale = main_camera.zoomScreen(scale);
                angle = main_camera.rotateScreen(angle);

                collider.draw(collider_position.x, collider_position.y, angle, scale);
            }
        }

    }
    // #endregion

    // #region DATA SAVER
    public void save(String path) {
        data_saver.setActive();
        DataSaver.save(path);
    }

    public void load(String path) {
        data_saver.setActive();
        DataSaver.load(path);
    }
    // #endregion

}
