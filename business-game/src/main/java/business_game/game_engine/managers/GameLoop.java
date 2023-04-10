package business_game.game_engine.managers;

import business_game.game_engine.interfaces.Updateable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameLoop {
    private Updateable updateable;
    private long timer_start;
    private long last_frame;

    public GameLoop(int FPS, Updateable updateable) {
        this.updateable = updateable;
        timer_start = System.currentTimeMillis();
        last_frame = System.nanoTime();
        setupGameLoop(FPS, this::update);
    }

    private void setupGameLoop(int FPS, Runnable updateFunction) {
        Duration frameTime = Duration.seconds(1.0 / FPS);
        KeyFrame frame = new KeyFrame(frameTime, e -> updateFunction.run());
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.getKeyFrames().add(frame);
        gameLoop.play();
    }

    public double getGameTime() {
        long diff = System.currentTimeMillis() - timer_start;
        return diff / 1000.0;
    }

    private double getDeltaTime() {
        long new_frame = System.nanoTime();
        double delta_time = (new_frame - last_frame) / 1000000000.0;
        last_frame = new_frame;
        return delta_time;
    }

    private void update() {
        double delta_time = getDeltaTime();
        updateable.update(delta_time);
    }

}
