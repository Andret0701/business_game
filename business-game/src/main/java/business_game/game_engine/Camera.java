package business_game.game_engine;

import business_game.game_engine.utils.Entity;
import business_game.game_engine.managers.Draw;
import business_game.game_engine.managers.Time;
import business_game.game_engine.utils.Vector2;

public class Camera extends Entity {
    private static Camera active_camera = null;
    private float zoom;

    private Entity target;

    public Camera(float zoom) {
        this.zoom = zoom;

        if (active_camera == null)
            active_camera = this;
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

    public static double zoom(double size) {
        if (active_camera == null)
            return size;
        return active_camera.getZoom() * size;
    }

    public static Vector2 cameraToWorld(Vector2 point) {
        if (active_camera == null)
            return point.copy();

        Vector2 result = new Vector2(point.x, point.y);
        result.sub(active_camera.getCenterOffset());
        result.y *= -1;
        result.div(active_camera.getZoom());
        result.add(active_camera.getPosition());
        return result;
    }

    public static Vector2 worldToCamera(Vector2 point) {
        if (active_camera == null)
            return point.copy();

        Vector2 result = new Vector2(point.x, point.y);
        result.sub(active_camera.getPosition());
        result.mul(active_camera.getZoom());
        result.y *= -1;
        result.add(active_camera.getCenterOffset());
        return result;
    }

    private Vector2 getCenterOffset() {
        return new Vector2(Draw.getWidth() / 2.0, Draw.getHeight() / 2.0);
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public void setActiveCamera() {
        active_camera = this;
    }

}
