package business_game.game_engine.entity;

import business_game.game_engine.types.Transform;

public interface Drawable {
    public Transform getTransform();

    public void draw(double x, double y, double angle, double scale);

    public double getLayer();

    public boolean isVisable();
}