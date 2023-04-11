package business_game.entities;

import business_game.animations.PlayerAnimator;
import business_game.game_engine.Game;
import business_game.game_engine.entity.Drawable;
import business_game.game_engine.entity.Entity;
import business_game.game_engine.entity.Interactable;
import business_game.game_engine.gfx.Animation;
import business_game.game_engine.gfx.AnimationManager;
import business_game.game_engine.gfx.Sprite;
import business_game.game_engine.gfx.SpriteRenderer;
import business_game.game_engine.managers.Draw;
import business_game.game_engine.physics.CircleCollider;
import business_game.game_engine.physics.Rigidbody;
import business_game.game_engine.types.Vector2;
import business_game.game_engine.types.Vector2Int;
import business_game.game_engine.utils.Loader;
import javafx.scene.paint.Color;

public class Player extends Entity implements Interactable, Drawable {
    private Rigidbody rigidbody;
    private SpriteRenderer sprite_renderer;
    private AnimationManager animation_manager;

    public Player() {
        super();
        addTag("Player");
        rigidbody = new Rigidbody(this);
        rigidbody.setCanRotate(false);
        rigidbody.addCollider(new CircleCollider(2, 0, 1.1));

        sprite_renderer = new SpriteRenderer();
        animation_manager = new PlayerAnimator(sprite_renderer);
    }

    @Override
    public void draw(double x, double y, double angle, double scale) {
        Draw.fill(Color.GREEN);
        Draw.circle(x, y + 1.1 * scale, scale * 2);
        Draw.sprite(sprite_renderer, x, y, scale, angle);
    }

    @Override
    public void update(double delta_time) {
        Vector2 input = Game.instance.input.getInput();
        Vector2 velocity = new Vector2(input.x * 100, input.y * 100);

        velocity.mul(delta_time);
        rigidbody.addForce(velocity);

        transform.setAngle(transform.getAngle() + delta_time);

        if (input.x > 0) {
            sprite_renderer.flip_x = false;
        } else if (input.x < 0) {
            sprite_renderer.flip_x = true;
        }

        if (input.x != 0 || input.y != 0) {
            animation_manager.play("run");
        } else {
            animation_manager.play("idle");
        }
        animation_manager.update(delta_time);
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

    double collision_timer = 0;

    @Override
    public void onCollision(Interactable other) {
        collision_timer = 0.1;
    }

    @Override
    public Entity copy() {
        Player entity = new Player();
        entity.transform.setTransform(transform);
        return entity;
    }
}
