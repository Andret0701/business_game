package business_game.game_engine.gfx;

import java.util.ArrayList;

import business_game.game_engine.interfaces.Updateable;

public class Animation implements Updateable {
    private ArrayList<Sprite> frames = new ArrayList<Sprite>();
    private double speed;
    private double timer;

    private boolean is_playing = true;
    private boolean is_looping = true;

    public Animation(double speed) {
        this.speed = speed;
        reset();
    }

    public Animation(String path, int num_frames, int offset_x, int offset_y, double speed) {
        for (int i = 1; i <= num_frames; i++)
            addFrame(path + i, offset_x, offset_y);
        this.speed = speed;
        reset();
    }

    public void addFrame(Sprite sprite) {
        frames.add(sprite);
    }

    public void addFrame(String filename, int offset_x, int offset_y) {
        addFrame(new Sprite(filename, offset_x, offset_y));
    }

    public void reset() {
        timer = 0;
    }

    @Override
    public void update(double delta_time) {
        if (is_playing)
            timer += delta_time * speed;

        if (is_looping && timer >= frames.size())
            timer = 0;
    }

    public Sprite getFrame() {
        int index = (int) timer;
        if (index >= frames.size())
            index = frames.size() - 1;

        return frames.get(index);
    }

}
