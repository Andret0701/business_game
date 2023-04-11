package business_game.entities;

import java.util.ArrayList;

import org.dyn4j.geometry.Vector2;

import business_game.game_engine.Game;
import business_game.game_engine.entity.Drawable;
import business_game.game_engine.entity.Entity;
import business_game.game_engine.entity.Interactable;
import business_game.game_engine.managers.Draw;
import business_game.game_engine.physics.CircleCollider;
import business_game.game_engine.physics.Rigidbody;
import javafx.scene.paint.Color;

public class TestEntity extends Entity implements Interactable, Drawable {
    private Rigidbody rigidbody;

    private Color color;
    private final ArrayList<Color> colors = new ArrayList<Color>() {
        {
            add(Color.RED);
            add(Color.GREEN);
            add(Color.BLUE);
            add(Color.YELLOW);
            add(Color.PURPLE);
            add(Color.ORANGE);
            add(Color.CYAN);
            add(Color.PINK);
            add(Color.BROWN);
            add(Color.BLACK);
            add(Color.WHITE);
        }
    };

    public TestEntity() {
        super();
        rigidbody = new Rigidbody(this);
        rigidbody.addCollider(new CircleCollider(5, 0, 0));
        rigidbody.setStatic(true);
        rigidbody.setTrigger(true);

        color = colors.get((int) (Math.random() * colors.size()));
    }

    @Override
    public void draw(double x, double y, double angle, double scale) {
        Draw.fill(color);
        Draw.circle(x, y, scale * 5);
    }

    @Override
    public double getLayer() {
        return -transform.getPosition().y;
    }

    @Override
    public boolean isVisable() {
        return true;
    }

    @Override
    public Rigidbody getRigidbody() {
        return rigidbody;
    }

    @Override
    public void update(double delta_time) {

    }

    @Override
    public void onCollision(Interactable other) {
        if (transform.getParent() != null)
            return;

        rigidbody.setSimulated(false);

        transform.setParent(other.getTransform());

        Vector2 position = new Vector2((Math.random() - 0.5) * 100, (Math.random() - 0.5) * 100);
        Game.instance.create(new TestEntity(), position.x, position.y);

    }

    @Override
    public Entity copy() {
        TestEntity entity = new TestEntity();
        entity.transform.setTransform(transform);
        return entity;
    }

}
