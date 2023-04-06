package business_game.game_engine.utils;

public class Vector2Int {
    public int x, y;

    public Vector2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2Int other) {
        x += other.x;
        y += other.y;
    }

    public void sub(Vector2Int other) {
        x -= other.x;
        y -= other.y;
    }

    public void mul(int scalar) {
        x *= scalar;
        y *= scalar;
    }

    public void div(int scalar) {
        if (scalar == 0)
            throw new IllegalArgumentException("Divide by zero");
        x /= scalar;
        y /= scalar;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2 toVector2() {
        return new Vector2(x, y);
    }

    public Vector2Int copy() {
        return new Vector2Int(x, y);
    }

    @Override
    public String toString() {
        return "" + x + ", " + y;
    }

}
