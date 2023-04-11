package business_game.game_engine;

import java.util.HashMap;
import java.util.List;

import business_game.game_engine.entity.Camera;
import business_game.game_engine.entity.Drawable;
import business_game.game_engine.entity.Entity;
import business_game.game_engine.entity.Interactable;
import business_game.game_engine.interfaces.Updateable;
import business_game.game_engine.managers.Draw;
import business_game.game_engine.managers.GameLoop;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;

import business_game.game_engine.managers.Input;
import business_game.game_engine.physics.PhysicsWorld;
import business_game.game_engine.types.Transform;
import business_game.game_engine.types.Vector2;
import business_game.game_engine.types.Vector2Int;

import java.util.Collections;

public class Game implements Updateable {
    public static Game instance;

    public void setActive() {
        instance = this;
    }

    // #region GAME SETTINGS
    public String name = "Game";
    public Vector2Int screen_size = new Vector2Int(800, 600);
    public boolean is_resizable = true;
    public int FPS = 60;
    // #endregion

    public final GameLoop game_loop;
    public final Input input;
    private PhysicsWorld physics_world;

    public Game() {
        game_loop = new GameLoop(FPS, this);
        input = new Input();
        physics_world = new PhysicsWorld();
    }

    private double current_fps = 0;

    // #region UPDATE MANAGER
    @Override
    public void update(double delta_time) {
        setActive();
        input.update();

        Draw.background(Color.BLACK);

        physics_world.update(delta_time);
        updateEntities(delta_time);
        updateDrawables();

        int n = 4;
        current_fps = current_fps * ((double) (n - 1) / (double) n) + (1 / delta_time) * ((double) (1)
                / (double) n);
        System.out.println("FPS: " + ((int) current_fps) + " | Entities: " + entities.size() + " | Drawables: "
                + drawEntities.size());
    }

    public void updateEntities(double delta_time) {
        for (Entity entity : entities)
            entity.update(delta_time);
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

    public void addScene(Scene scene) {
        scenes.put(scene.name, scene);
    }

    public void setScene(String scene_name) {
        if (!scenes.containsKey(scene_name))
            throw new IllegalArgumentException("Scene " + scene_name + " does not exist");
        destroyAll();

        Scene scene = scenes.get(scene_name);
        boolean created_camera = false;
        for (Entity entity : scene) {
            Vector2 position = entity.getTransform().getPosition();
            Entity created = create(entity, position.x, position.y);
            if (created_camera == false && entity instanceof Camera) {
                setMainCamera((Camera) created);
                created_camera = true;
            }
        }

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
}
