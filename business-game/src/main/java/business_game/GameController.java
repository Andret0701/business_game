package business_game;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import business_game.game_engine.managers.GameLoop;
import org.dyn4j.world.World;
import org.dyn4j.dynamics.Body;
//in dyn4j, body is located in org.dyn4j.body.
import org.dyn4j.geometry.Circle;

import org.dyn4j.geometry.Vector2;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;

import business_game.Draw;
import business_game.CameraTest;

public class GameController {
    @FXML
    private Canvas canvas;

    private World<Body> world = new World<Body>();
    private Body body = new Body();
    Rigidbody rigidbody = new Rigidbody();
    private Body floor = new Body();

    int FPS = 60;
    // World<Body> world = new World<Body>();

    @FXML // this is called when the fxml file is loaded
    private void initialize() {
        GameLoop.start(FPS, this::update);
        world.setGravity(new Vector2(0, -9.81));
        body.addFixture(new Circle(3.0));
        body.setMass(MassType.NORMAL);
        world.addBody(body);

        floor.addFixture(new Rectangle(100, 1));
        floor.setMass(MassType.INFINITE);
        floor.translate(0, -10);
        world.addBody(floor);
        world.addBody(rigidbody.getBody());

        Draw.init(canvas);
        camera = new CameraTest(0, 0, 10);
        Draw.setCamera(camera);
    }

    Camera camera;

    float time = 0;

    private void update() {
        // make canvas scale with window
        canvas.setWidth(canvas.getParent().getLayoutBounds().getWidth());
        canvas.setHeight(canvas.getParent().getLayoutBounds().getHeight());
        world.update(1.0 / FPS);
        time += 1.0 / FPS;
        if (time > 3) {
            time = 0;
            body.applyForce(new Vector2(0, 20000));
        }
        CameraTest c = (CameraTest) camera;
        c.setScreenSize(canvas.getWidth(), canvas.getHeight());

        // set angle
        // floor.getTransform().setRotation(Math.sin(time) * 0.5);

        Draw.background(Color.BLACK);
        Draw.fill(Color.BLUE);
        Draw.circle(body.getTransform().getTranslationX(), body.getTransform().getTranslationY(), 3);

        Draw.rect(floor.getTransform().getTranslationX(), floor.getTransform().getTranslationY(), 100, 1);
    }

}
