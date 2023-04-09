package business_game.game_engine;

import java.util.HashMap;

import org.dyn4j.world.World;

import business_game.Updateable;
import business_game.Vector2;
import business_game.game_engine.managers.DrawManager;

import business_game.game_engine.managers.GameLoop;
import business_game.game_engine.managers.Input;
import business_game.game_engine.managers.Time;
import business_game.game_engine.utils.Vector2Int;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import business_game.Entity;
import business_game.Interactable;
import business_game.Drawable;

import java.util.ArrayList;
import java.util.Collection;

import business_game.Camera;
import business_game.Draw;

import org.dyn4j.dynamics.Body;

public class Game implements Updateable {
    public static Game instance;

    public void setActive() {
        instance = this;
    }

    // #region GAME SETTINGS
    public final String name = "Game";
    public final Vector2Int screen_size = new Vector2Int(800, 600);
    public final boolean is_resizable = true;
    public final int FPS = 60;
    // #endregion

    public final GameLoop game_loop;
    private World<Body> physics_world;
    private Camera main_camera;

    public Game() {
        game_loop = new GameLoop(FPS, this);
        physics_world = new World<Body>();
    }

    @Override
    public void update(double delta_time) {
        // fix this later - make canvas scale with window
        // canvas.widthProperty().bind(canvas.getScene().widthProperty());
        // canvas.heightProperty().bind(canvas.getScene().heightProperty());
        // canvas.requestFocus();

        setActive();

        Draw.background(Color.BLACK);
    }

    public void updateInteractables(double delta_time) {
        for (Interactable interactable : interactableEntities)
            interactable.getRigidbody().syncBody();

        physics_world.update(delta_time);

        for (Interactable interactable : interactableEntities)
            interactable.getRigidbody().syncTransform();
    }

    public void updateEntities(double delta_time) {
        for (Entity entity : entities)
            entity.update(delta_time);
    }

    public void updateDrawables() {
        for (Drawable drawable : drawEntities) {
            Entity entity = (Entity) drawable; // this is bad - fix later

            Vector2 position = entity.transform.getPosition();
            double scale = entity.transform.getScale();
            double angle = entity.transform.getAngle();

            position = main_camera.worldToScreen(position);
            scale = main_camera.zoomScreen(scale);
            angle = main_camera.rotateScreen(angle);

            drawable.draw(position.x, position.y, angle, scale);
        }
    }

    // #region SCENE MANAGER
    protected HashMap<String, Scene> scenes = new HashMap<String, Scene>();

    public void addScene(Scene scene) {
        scenes.put(scene.name, scene);
    }

    public void setScene(String scene_name) {
        if (!scenes.containsKey(scene_name))
            throw new IllegalArgumentException("Scene " + scene_name + " does not exist");
        destroyAll();
        for (Entity entity : scenes.get(scene_name)) {
            Vector2 position = entity.transform.getPosition();
            create(entity, position.x, position.y);
        }
    }
    // #endregion

    // #region ENTITY MANAGER
    private Collection<Entity> entities = new ArrayList<>();
    private Collection<Drawable> drawEntities = new ArrayList<>();
    private Collection<Interactable> interactableEntities = new ArrayList<>();

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
        physics_world.addBody(interactable.getRigidbody().getBody());
    }

    private void destroyInteractable(Interactable interactable) {
        interactableEntities.remove(interactable);
        physics_world.removeBody(interactable.getRigidbody().getBody());
    }
    // #endregion

}
