package business_game.game_engine;

import java.util.HashMap;

import business_game.game_engine.managers.DrawManager;

import business_game.game_engine.managers.GameLoop;
import business_game.game_engine.managers.Input;
import business_game.game_engine.managers.Time;
import business_game.game_engine.utils.Vector2;
import business_game.game_engine.utils.Vector2Int;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Game {
    public static Game instance;

    public InputManager input;
    public DrawManager draw;
    public TimeManager time;

    public void setActive() {
        instance = this;
    }

    /// move?? ///
    public final String name = "Game";
    public final Vector2Int screen_size = new Vector2Int(800, 600);
    public final boolean is_resizable = true;
    public final int FPS = 60;
    /// ///

    protected Canvas canvas;

    protected HashMap<String, Scene> scenes = new HashMap<String, Scene>();
    protected Scene current_scene;

    public Game(Canvas canvas) {
        this.canvas = canvas;
    }

    public void start() {
        input = new InputManager(canvas);
        draw = new DrawManager(canvas);
        time = new TimeManager();
        GameLoop.start(FPS, this::update);
    }

    public void update() {
        // fix this later - make canvas scale with window
        canvas.widthProperty().bind(canvas.getScene().widthProperty());
        canvas.heightProperty().bind(canvas.getScene().heightProperty());
        canvas.requestFocus();

        setActive();

        input.update();
        time.update();

        current_scene.update();
        current_scene.draw();

        Draw.background(Color.BLACK);
    }

    public void addScene(Scene scene) {
        scenes.put(scene.name, scene);
        if (current_scene == null)
            current_scene = scene;
    }

    public void setScene(String scene_name) {
        if (!scenes.containsKey(scene_name))
            throw new IllegalArgumentException("Scene " + scene_name + " does not exist");
        // maybe reset change later
        current_scene = scenes.get(scene_name);
    }

    public Vector2 getScreenSize() {
        return new Vector2(canvas.getWidth(), canvas.getHeight());
    }

}
