package business_game;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import business_game.game_engine.Game;
import business_game.game_engine.managers.Draw;
import business_game.game_engine.managers.Input;
import business_game.game_engine.entity.Entity;
import business_game.game_engine.entity.Tilemap;

public class GameController {
    @FXML
    private Canvas canvas;
    @FXML
    private AnchorPane root;

    Game game;

    Entity entity;
    Tilemap tilemap;

    @FXML // this is called when the fxml file is loaded
    private void initialize() {
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());
        Draw.init(canvas);
        Input.init(canvas);

        game = new BusinessGame();

    }

    public void resize() {
        canvas.setHeight(canvas.getParent().getLayoutBounds().getHeight());
        canvas.setWidth(canvas.getParent().getLayoutBounds().getWidth());
    }

}
