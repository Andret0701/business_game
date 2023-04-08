package business_game;

public abstract class Camera {
    public double zoom;

    public Camera(double zoom) {
        setZoom(zoom);
    }

    public abstract Vector2 getPosition();

    public abstract Vector2 getScreenSize();

    public abstract double getRotation();

    public void setZoom(double zoom) {
        if (zoom <= 0)
            throw new IllegalArgumentException("Zoom must be greater than 0");
        this.zoom = zoom;
    }

    public double zoomScreen(double screen_size) {
        return zoom * screen_size;
    }

    public double zoomWorld(double world_size) {
        return world_size / zoom;
    }

    public Vector2 screenToWorld(Vector2 screen_point) {
        Vector2 result = new Vector2(screen_point.x, screen_point.y);
        result.sub(getCenterOffset());
        result.y *= -1;
        result.div(zoom);
        result.add(getPosition());
        return result;
    }

    public Vector2 worldToScreen(Vector2 world_point) {
        Vector2 result = new Vector2(world_point.x, world_point.y);
        result.sub(getPosition());
        result.mul(zoom);
        result.y *= -1;
        result.add(getCenterOffset());
        System.out.println(result);
        return result;
    }

    private Vector2 getCenterOffset() {
        Vector2 screen_size = getScreenSize();
        return new Vector2(screen_size.x / 2, screen_size.y / 2);
    }

}
