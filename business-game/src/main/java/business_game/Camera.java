package business_game;

public class Camera extends Entity {
    public Camera(double zoom) {
        transform.setScale(zoom);
    }

    public double zoomScreen(double screen_size) {
        return transform.worldToLocalScale(screen_size);
    }

    public double zoomWorld(double world_size) {
        return transform.localToWorldScale(world_size);
    }

    public double rotateScreen(double screen_angle) {
        return transform.worldToLocalAngle(screen_angle);
    }

    public double rotateWorld(double world_angle) {
        return transform.localToWorldAngle(world_angle);
    }

    public Vector2 screenToWorld(Vector2 screen_point) {
        return transform.localToWorldPosition(screen_point);
    }

    public Vector2 worldToScreen(Vector2 world_point) {
        return transform.worldToLocalPosition(world_point);
    }

    @Override
    public Entity copy() {
        Camera camera = new Camera(0);
        camera.transform = transform.copy();
        return camera;
    }
}
