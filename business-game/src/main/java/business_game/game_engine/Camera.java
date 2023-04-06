package business_game.game_engine;

import business_game.game_engine.utils.Entity;
import business_game.game_engine.managers.Draw;
import business_game.game_engine.managers.Time;
import business_game.game_engine.utils.Vector2;

public class Camera extends Entity {
    private float zoom;

    private Entity target;

    public Camera(float zoom) {
        this.zoom = zoom;
    }

    @Override
    public void update() {
        if (target == null)
            return;
        float speed = 2f;

        Vector2 movement = target.getPosition();
        movement.sub(position);
        movement.mul(speed * Time.delta_time);
        position.add(movement);
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public double zoom(double size) {
        return zoom * size;
    }

    public Vector2 cameraToWorld(Vector2 point) {
        Vector2 result = new Vector2(point.x, point.y);
        result.sub(getCenterOffset());
        result.y *= -1;
        result.div(zoom);
        result.add(position);
        return result;
    }

    public Vector2 worldToCamera(Vector2 point) {
        Vector2 result = new Vector2(point.x, point.y);
        result.sub(position);
        result.mul(zoom);
        result.y *= -1;
        result.add(getCenterOffset());
        return result;
    }

    private Vector2 getCenterOffset() {
        return new Vector2(Draw.getWidth() / 2.0, Draw.getHeight() / 2.0);
    }

    public void setTarget(Entity target) {
        this.target = target;
    }
}
