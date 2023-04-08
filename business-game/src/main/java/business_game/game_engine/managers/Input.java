package business_game.game_engine.managers;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashMap;

import business_game.Vector2;

public class Input {

    private static HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private static HashMap<KeyCode, Boolean> down_keys = new HashMap<KeyCode, Boolean>();
    private static HashMap<KeyCode, Boolean> up_keys = new HashMap<KeyCode, Boolean>();

    private static ArrayList<KeyCode> pressed_keys = new ArrayList<KeyCode>();
    private static ArrayList<KeyCode> removed_keys = new ArrayList<KeyCode>();

    public static void init(Canvas canvas) {
        canvas.setOnKeyPressed(Input::keyPressed);
        canvas.setOnKeyReleased(Input::keyReleased);
        canvas.setOnMouseMoved(Input::mouseMoved);
    }

    public static void keyPressed(KeyEvent e) {
        pressed_keys.add(e.getCode());
    }

    public static void keyReleased(KeyEvent e) {
        removed_keys.add(e.getCode());
    }

    public static void update() {
        down_keys.clear();
        up_keys.clear();
        for (KeyCode key : pressed_keys) {
            down_keys.put(key, true);
            keys.put(key, true);
        }

        for (KeyCode key : removed_keys) {
            up_keys.put(key, true);
            keys.remove(key);
        }

        pressed_keys.clear();
        removed_keys.clear();
    }

    public static boolean getKey(String key) {
        key = key.toUpperCase();
        return keys.containsKey(KeyCode.valueOf(key));
    }

    public static boolean getKey(KeyCode key) {
        return keys.containsKey(key);
    }

    public static boolean getKeyDown(String key) {
        key = key.toUpperCase();
        return down_keys.containsKey(KeyCode.valueOf(key));
    }

    public static boolean getKeyDown(KeyCode key) {
        return down_keys.containsKey(key);
    }

    public static boolean getKeyUp(String key) {
        key = key.toUpperCase();
        return up_keys.containsKey(KeyCode.valueOf(key));
    }

    public static boolean getKeyUp(KeyCode key) {
        return up_keys.containsKey(key);
    }

    public static Vector2 getInput() {
        Vector2 input = new Vector2(0, 0);
        if (getKey(KeyCode.W) || getKey(KeyCode.UP))
            input.y += 1;
        if (getKey(KeyCode.S) || getKey(KeyCode.DOWN))
            input.y -= 1;
        if (getKey(KeyCode.A) || getKey(KeyCode.LEFT))
            input.x -= 1;
        if (getKey(KeyCode.D) || getKey(KeyCode.RIGHT))
            input.x += 1;

        double length = input.length();
        if (length > 1)
            input.normalize();

        return input;
    }

    private static Vector2 mouse = new Vector2(0, 0);

    public static void mouseMoved(javafx.scene.input.MouseEvent e) {
        mouse.x = e.getX();
        mouse.y = e.getY();
    }

    public static Vector2 getMouse() {
        return mouse;
    }

}
