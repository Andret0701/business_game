package game_engine.utils;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import game_engine.gfx.Sprite;

public class Loader {

    static private String getSpritesPath(String name) {
        return "business-game/src/main/java/business_game/sprites/" + name + ".png";
    }

    public static Image loadImage(String name) {
        String path = getSpritesPath(name);
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Image not found: " + path);
        }
        return image;
    }

    public static Sprite loadSprite(String name, int offset_x, int offset_y) {
        return new Sprite(name, offset_x, offset_y);
    }

    /*
     * public static SpriteSheet loadSpriteSheet(String name, int sprite_width, int
     * sprite_height, int offset_x,
     * int offset_y) {
     * return new SpriteSheet(name, sprite_width, sprite_height, offset_x,
     * offset_y);
     * }
     */
}
