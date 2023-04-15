package business_game.entities;

import game_engine.entity.Drawable;
import game_engine.entity.Entity;
import game_engine.managers.Draw;

public class Square extends Entity implements Drawable {
    public Square() {
        super();
    }

    @Override
    public void draw(double x, double y, double angle, double scale) {
        Draw.rect(x, y, 10, 10, angle, scale);
    }

}
