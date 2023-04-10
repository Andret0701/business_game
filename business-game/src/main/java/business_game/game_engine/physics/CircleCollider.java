package business_game.game_engine.physics;

import business_game.game_engine.types.Vector2;

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
}
