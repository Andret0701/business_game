package business_game;

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

}
