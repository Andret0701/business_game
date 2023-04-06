package business_game;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.animation.Timeline;
import javafx.util.Duration;
/*import game.Entities.Player;*/
import business_game.game_engine.managers.*;
import business_game.scenes.GrassLandScene;
import business_game.game_engine.Camera;
import javafx.animation.KeyFrame;

import business_game.game_engine.*;

public class GameController {
    @FXML
    private Canvas canvas;
    public Scene scene;

    int FPS = 60;

    @FXML // this is called when the fxml file is loaded
    private void initialize() {
        scene = new GrassLandScene();

        Input.init(canvas);
        Draw.init(canvas);
        Time.init();

        Draw.setPixelSize(5);

        Duration frame_time = Duration.seconds(1.0 / FPS);
        KeyFrame frame = new KeyFrame(frame_time, e -> update());
        Timeline game_loop = new Timeline();
        game_loop.setCycleCount(Timeline.INDEFINITE);
        game_loop.getKeyFrames().add(frame);
        game_loop.play();
        /*
         * tilemap = new Tilemap(0, 0, 200, 200);
         * tilemap.fill(new Sprite("Tiles/grass", 0, 0));
         * 
         * for (int i = 0; i < 200; i++) {
         * tilemap.setTile(i, 5, new Sprite("Tiles/RoadGrassTop", 0, 0));
         * tilemap.setTile(i, 4, new Sprite("Tiles/RoadTop", 0, 0));
         * tilemap.setTile(i, 3, new Sprite("Tiles/Road", 0, 0));
         * tilemap.setTile(i, 2, new Sprite("Tiles/RoadBottom", 0, 0));
         * tilemap.setTile(i, 1, new Sprite("Tiles/RoadGrassBottom", 0, 0));
         * }
         * player = new Player(0, 0);
         * main_camera.setTarget(player);
         */

    }

    private void update() {
        // fix this later
        // make canvas scale with window
        canvas.widthProperty().bind(canvas.getScene().widthProperty());
        canvas.heightProperty().bind(canvas.getScene().heightProperty());

        canvas.requestFocus();

        Input.update();
        Time.update();

        Draw.background(Color.BLACK);

        scene.update();
        scene.draw();

        Camera main_camera = Scene.getMainCamera();
        if (Input.getKey("O"))
            main_camera.setZoom((float) (main_camera.getZoom() + main_camera.getZoom() *
                    Time.delta_time));
        else if (Input.getKey("P"))
            main_camera.setZoom((float) (main_camera.getZoom() - main_camera.getZoom() *
                    Time.delta_time));

    }

}
