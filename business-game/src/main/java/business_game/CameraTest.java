package business_game;

public class CameraTest extends Camera {
    public Vector2 position;

    public CameraTest(double x, double y, double zoom) {
        super(zoom);
        this.position = new Vector2(x, y);
    }

    @Override
    public Vector2 getPosition() {
        return position.copy();
    }

    private Vector2 screen_size = new Vector2(640, 480);

    @Override
    public Vector2 getScreenSize() {
        return new Vector2(screen_size.x, screen_size.y);
    }

    public void setScreenSize(double x, double y) {
        this.screen_size = new Vector2(x, y);
    }

    @Override
    public double getRotation() {
        return 0;
    }
}
