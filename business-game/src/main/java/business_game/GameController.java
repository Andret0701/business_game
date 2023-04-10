package business_game;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import business_game.game_engine.Game;

public class GameController {
    @FXML
    private Canvas canvas;
    @FXML
    private AnchorPane root;

    Game game;

    Entity entity;

    @FXML // this is called when the fxml file is loaded
    private void initialize() {
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());
        Draw.init(canvas);

        game = new Game();
        entity = game.create(new TestEntity(), 0.4, 0);
        entity = game.create(new TestEntity(), 0, -15);
        // debug
        System.out.println(entity);

        ((Interactable) entity).getRigidbody().setStatic(true);
    }

    public void resize() {
        canvas.setHeight(canvas.getParent().getLayoutBounds().getHeight());
        canvas.setWidth(canvas.getParent().getLayoutBounds().getWidth());
    }

}
