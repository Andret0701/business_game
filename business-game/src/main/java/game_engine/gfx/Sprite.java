package game_engine.gfx;

import game_engine.types.Vector2Int;
import game_engine.utils.Loader;
import javafx.scene.image.Image;

public class Sprite {
    public Image image;
    public Vector2Int offset;

    public Sprite(String path, int offset_x, int offset_y) {
        offset = new Vector2Int(offset_x, offset_y);
        image = Loader.loadImage(path);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
