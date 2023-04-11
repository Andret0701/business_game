package game_engine.physics;

import game_engine.managers.Draw;
import game_engine.types.Vector2;
import javafx.scene.paint.Color;

public class BoxCollider extends Collider {
    private Vector2 size;

    public BoxCollider(double width, double height, double offset_x, double offset_y) {
        this.size = new Vector2(width, height);
        this.offset = new Vector2(offset_x, offset_y);
    }

    public BoxCollider(double width, double height) {
        this.size = new Vector2(width, height);
    }

    public double getWidth() {
        return size.x;
    }

    public double getHeight() {
        return size.y;
    }

    @Override
    public void draw(double x, double y, double angle, double scale) {
        Draw.fill(Color.TRANSPARENT);
        Draw.stroke(Color.LIGHTGREEN);
        Draw.strokeWeight(1); // scale?
        Draw.rect(x, y, size.x * scale, size.y * scale);
    }
}
