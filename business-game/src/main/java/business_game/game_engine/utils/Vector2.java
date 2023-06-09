package business_game.game_engine.utils;

public class Vector2 {
    public double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2 other) {
        x += other.x;
        y += other.y;
    }

    public void sub(Vector2 other) {
        x -= other.x;
        y -= other.y;
    }

    public void mul(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    public void mul(Vector2 other) {
        x *= other.x;
        y *= other.y;
    }

    public void div(double scalar) {
        if (scalar == 0)
            throw new IllegalArgumentException("Divide by zero");
        x /= scalar;
        y /= scalar;
    }

    public void div(Vector2 other) {
        if (other.x == 0 || other.y == 0)
            throw new IllegalArgumentException("Divide by zero");
        x /= other.x;
        y /= other.y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        double length = length();
        if (length == 0)
            return;
        div(length);
    }

    public Vector2Int toVector2Int() {
        return new Vector2Int((int) x, (int) y);
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }

    @Override
    public String toString() {
        return "" + x + " ," + y;
    }
}
