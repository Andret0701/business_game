package business_game.game_engine.components;

import business_game.game_engine.utils.Entity;

public abstract class DrawComponent implements Component {
    protected Entity parent;
    public boolean visible = true;
    public double layer = 0;

    public DrawComponent(Entity parent, double layer) {
        this.parent = parent;
        this.layer = layer;
    }

    public abstract void draw();

    @Override
    public void update() {
    }
}
