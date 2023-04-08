package business_game;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.animation.Timeline;
import javafx.util.Duration;

import business_game.game_engine.managers.*;
import business_game.scenes.GrassLandScene;
import business_game.game_engine.Camera;
import javafx.animation.KeyFrame;

import business_game.game_engine.Scene;

public class GameController {
    @FXML
    private Canvas canvas;
    public Scene scene;

    int FPS = 60;

    @FXML // this is called when the fxml file is loaded
    private void initialize() {
        scene = new GrassLandScene();

        // Input.init(canvas);
        // Draw.init(canvas);
        // Time.init();

        GameLoop.start(FPS, this::update);
    }

    private void update() {
        // fix this later
        // make canvas scale with window
        /*
         * canvas.widthProperty().bind(canvas.getScene().widthProperty());
         * canvas.heightProperty().bind(canvas.getScene().heightProperty());
         * 
         * canvas.requestFocus();
         * 
         * Input.update();
         * Time.update();
         * 
         * Draw.background(Color.BLACK);
         * 
         * scene.update();
         * scene.draw();
         * 
         * Camera main_camera = Scene.getMainCamera();
         * if (Input.getKey("O"))
         * main_camera.setZoom((float) (main_camera.getZoom() + main_camera.getZoom() *
         * Time.delta_time));
         * else if (Input.getKey("P"))
         * main_camera.setZoom((float) (main_camera.getZoom() - main_camera.getZoom() *
         * Time.delta_time));
         */
    }

}
