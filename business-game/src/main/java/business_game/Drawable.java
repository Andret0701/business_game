package business_game;

public interface Drawable {
    public void draw(double x, double y, double angle, double scale);

    public double getLayer();

    public boolean isVisable();
}