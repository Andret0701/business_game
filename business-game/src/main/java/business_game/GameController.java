package business_game;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import business_game.game_engine.Game;
import business_game.game_engine.managers.Draw;
import business_game.game_engine.managers.Input;
import business_game.game_engine.entity.Entity;
import business_game.game_engine.entity.Tilemap;
import business_game.game_engine.utils.Loader;

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

        game = new Game();
        // entity = game.create(new TestEntity(), 0.4, 0);
        tilemap = (Tilemap) game.create(new Tilemap(20, 20), 0, 0);
        tilemap.fill(Loader.loadSprite("Tiles/Grass", 0, 0));

        // debug
        // System.out.println(entity);

        // ((Interactable) entity).getRigidbody().setStatic(true);
    }

    public void resize() {
        canvas.setHeight(canvas.getParent().getLayoutBounds().getHeight());
        canvas.setWidth(canvas.getParent().getLayoutBounds().getWidth());
    }

}
