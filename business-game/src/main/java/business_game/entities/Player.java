package business_game.entities;

import business_game.animations.PlayerAnimator;
import game_engine.Game;
import game_engine.entity.Drawable;
import game_engine.entity.Entity;
import game_engine.entity.Interactable;
import game_engine.gfx.AnimationManager;
import game_engine.gfx.SpriteRenderer;
import game_engine.managers.Draw;
import game_engine.physics.CircleCollider;
import game_engine.physics.Rigidbody;
import game_engine.types.Vector2;
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

        rigidbody.setDamping(0.95);

        sprite_renderer = new SpriteRenderer();
        animation_manager = new PlayerAnimator(sprite_renderer);

    }

    @Override
    public void draw(double x, double y, double angle, double scale) {
        Draw.sprite(sprite_renderer, x, y, scale, angle);
    }

    @Override
    public void update(double delta_time) {
        Vector2 input = Game.instance.input.getInput();
        Vector2 velocity = new Vector2(input.x * 100, input.y * 100);

        velocity.mul(delta_time);
        rigidbody.addForce(velocity);

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
