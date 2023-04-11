package game_engine.gfx;

import java.util.HashMap;

import game_engine.interfaces.Updateable;

public class AnimationManager implements Updateable {
    private HashMap<String, Animation> animations = new HashMap<String, Animation>();
    private String current_animation = "";

    private SpriteRenderer sprite_renderer;

    public AnimationManager(SpriteRenderer sprite_renderer) {
        this.sprite_renderer = sprite_renderer;
    }

    public void addAnimation(String name, Animation animation) {
        animations.put(name, animation);
    }

    public void addAnimation(String name, String path, int num_frames, int offset_x, int offset_y, double speed) {
        Animation animation = new Animation(path, num_frames, offset_x, offset_y, speed);
        addAnimation(name, animation);
    }

    private boolean hasAnimation(String name) {
        return animations.containsKey(name);
    }

    @Override
    public void update(double delta_time) {
        if (!hasAnimation(current_animation))
            return;
        animations.get(current_animation).update(delta_time);
        sprite_renderer.sprite = getFrame();
    }

    public void play(String name) {
        if (!hasAnimation(name))
            throw new IllegalArgumentException("Animation '" + name + "' does not exist");
        current_animation = name;
        // animations.get(current_animation).reset();
    }

    private Sprite getFrame() {
        if (!hasAnimation(current_animation))
            return null;
        return animations.get(current_animation).getFrame();
    }
}
