package business_game.game_engine.components;

import business_game.game_engine.utils.Entity;
import business_game.Camera;
import business_game.Vector2;
import business_game.game_engine.Game;

public class CameraComponent extends Camera implements Component {
    private Entity parent;

    public CameraComponent(Entity parent, double zoom) {
        super(zoom);
        this.parent = parent;
    }

    @Override
    public Vector2 getPosition() {
        return parent.getPosition();
    }

    @Override
    public Vector2 getScreenSize() {
        return Game.instance.getScreenSize();
    }

    @Override
    public void update() {
    }

}
