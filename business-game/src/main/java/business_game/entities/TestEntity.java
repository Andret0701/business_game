package business_game.entities;

import java.util.ArrayList;

import game_engine.Game;
import game_engine.entity.Drawable;
import game_engine.entity.Entity;
import game_engine.entity.Interactable;
import game_engine.managers.Draw;
import game_engine.physics.CircleCollider;
import game_engine.physics.Rigidbody;
import game_engine.types.Vector2;
import javafx.scene.paint.Color;

public class TestEntity extends Entity implements Interactable, Drawable {
    private Rigidbody rigidbody;

    private Color color;
    private final ArrayList<Color> colors = new ArrayList<Color>() {
        {
            // add(Color.RED);
            add(Color.LIGHTGREEN);
            add(Color.LIGHTBLUE);
            // add(Color.YELLOW);
            add(Color.PURPLE);
            add(Color.ORANGE);
            add(Color.CYAN);
            // add(Color.PINK);
            // add(Color.BROWN);
            // add(Color.BLACK);
            // add(Color.WHITE);
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
        Draw.noStroke();
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

    private double time = 0;
    private int state = 0;
    private Vector2 dir;

    @Override
    public void update(double delta_time) {
        double distance_from_center = transform.getPosition().length();
        if (distance_from_center > 100) {
            Game.instance.destroy(this);
            return;
        }

        if (state == 1) {

            Vector2 local_position = transform.getLocalPositon();
            local_position.rotate(delta_time * 2);
            local_position.normalize();
            local_position.mul(Math.sin(time * 3.1) * 1
                    + Math.sin(Game.instance.game_loop.getGameTime() * 2) * 5 + 11);
            transform.setLocalPosition(local_position);

            if (Game.instance.input.getKeyDown("space")) {
                dir = transform.getLocalPositon();
                dir.normalize();
                transform.setParent(null);
                state = 2;
            }
        } else if (state == 2) {
            Vector2 _dir = dir.copy();
            _dir.rotate(transform.getAngle());
            _dir.mul(delta_time * 50);
            transform.translate(_dir);
        }

        time += delta_time;

    }

    @Override
    public void onCollision(Interactable other) {
        if (state != 0) {
            return;
        }

        if (transform.getParent() != null)
            return;

        rigidbody.setSimulated(false);

        transform.setParent(other.getTransform());
        Vector2 position = new Vector2((Math.random() - 0.5) * 100, (Math.random() - 0.5) * 100);
        Game.instance.create(new TestEntity(), position.x, position.y);
        state = 1;

    }

    @Override
    public Entity copy() {
        TestEntity entity = new TestEntity();
        entity.transform.setTransform(transform);
        return entity;
    }

}
