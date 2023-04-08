package business_game.game_engine.managers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameLoop {
    public static void start(int FPS, Runnable updateFunction) {
        Duration frameTime = Duration.seconds(1.0 / FPS);
        KeyFrame frame = new KeyFrame(frameTime, e -> updateFunction.run());
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.getKeyFrames().add(frame);
        gameLoop.play();
    }
}
