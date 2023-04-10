package business_game;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import business_game.game_engine.Game;

public class GameController {
    @FXML
    private Canvas canvas;

    Game game;

    Entity entity;

    @FXML // this is called when the fxml file is loaded
    private void initialize() {
        game = new Game();
        entity = game.create(new TestEntity(), 0, 0);
        entity = game.create(new TestEntity(), 0, -15);

        ((Interactable) entity).getRigidbody().setStatic(true);
        Draw.init(canvas);
    }

    public void resize() {
        canvas.setHeight(canvas.getParent().getLayoutBounds().getHeight());
        canvas.setWidth(canvas.getParent().getLayoutBounds().getWidth());
    }

}
