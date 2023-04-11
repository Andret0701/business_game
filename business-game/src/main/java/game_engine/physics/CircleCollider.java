package game_engine.physics;

import game_engine.managers.Draw;
import game_engine.types.Vector2;
import javafx.scene.paint.Color;

public class CircleCollider extends Collider {
    private double radius;

    public CircleCollider(double radius) {
        this.radius = radius;
    }

    public CircleCollider(double radius, double offset_x, double offset_y) {
        this.radius = radius;
        this.offset = new Vector2(offset_x, offset_y);
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void draw(double x, double y, double angle, double scale) {
        Draw.fill(Color.TRANSPARENT);
        Draw.stroke(Color.LIGHTGREEN);
        Draw.strokeWeight(1); // scale?
        Draw.circle(x, y, radius * scale);
    }
}
