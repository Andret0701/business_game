package business_game;

import game_engine.Game;
import game_engine.managers.Draw;
import game_engine.managers.Input;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

public class GameController {
    @FXML
    private Canvas canvas;
    @FXML
    private AnchorPane root;

    public static Game game; // this is bad

    @FXML // this is called when the fxml file is loaded
    private void initialize() {
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());
        Draw.init(canvas);
        Input.init(canvas);

        game = new BusinessGame();
        game.load("save.txt");

        game.start();
    }

}
