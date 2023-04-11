package business_game.entities;

import java.util.Vector;

import business_game.game_engine.Game;
import business_game.game_engine.entity.Camera;
import business_game.game_engine.entity.Entity;
import business_game.game_engine.types.Vector2;

public class FollowCamera extends Camera {
    private String tag_to_follow;
    private double speed = 0.1;

    public FollowCamera(double zoom, double speed, String tag_to_follow) {
        super(zoom);
        this.tag_to_follow = tag_to_follow;
        this.speed = speed;
    }

    @Override
    public void update(double time_delta) {
        if (target == null || target.hasTag(tag_to_follow))
            target = Game.instance.getEntityWithTag(tag_to_follow);
        if (target == null) {
            System.out.println("No target found");
            return;
        }

        Vector2 target_pos = target.getTransform().getPosition();
        Vector2 camera_pos = transform.getPosition();

        Vector2 move = target_pos.copy();
        move.sub(camera_pos);
        move.mul(speed * time_delta);

        transform.translate(move);
    }

    public void setTagToFollow(String tag) {
        tag_to_follow = tag;
    }

    private Entity target;

    public void setTarget(Entity target) {
        this.target = target;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public Entity copy() {
        FollowCamera camera = new FollowCamera(getZoom(), speed, tag_to_follow);
        camera.transform.setTransform(transform);
        camera.setTarget(target);

        return camera;
    }

}
