package business_game;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import business_game.game_engine.managers.GameLoop;
import org.dyn4j.world.World;

import java.text.Format;

import org.dyn4j.dynamics.Body;
//in dyn4j, body is located in org.dyn4j.body.
import org.dyn4j.geometry.Circle;

import org.dyn4j.geometry.Vector2;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;

public class GameController implements Updateable {
    @FXML
    private Canvas canvas;

    private World<Body> world = new World<Body>();
    private Body body = new Body();
    Transform transform = new Transform();
    private Transform child_transform = new Transform();
    Rigidbody rigidbody;
    private Body floor = new Body();

    int FPS = 60;
    GameLoop gameLoop;

    @FXML // this is called when the fxml file is loaded
    private void initialize() {
        gameLoop = new GameLoop(FPS, this);
        transform.setPosition(new business_game.Vector2(0.01, 4));
        child_transform.setPosition(new business_game.Vector2(1, 4));
        child_transform.setParent(transform);

        world.setGravity(new Vector2(0, -9.81));

        rigidbody = new Rigidbody(transform);
        rigidbody.getBody().addFixture(new Circle(3.0));
        rigidbody.getBody().setMass(MassType.NORMAL);

        body.addFixture(new Circle(3.0));
        body.setMass(MassType.NORMAL);

        floor.addFixture(new Rectangle(100, 1));
        floor.setMass(MassType.INFINITE);
        floor.translate(0, -10);

        world.addBody(body);
        world.addBody(floor);
        world.addBody(rigidbody.getBody());

        Draw.init(canvas);
    }

    float time = 0;

    @Override
    public void update(double delta_time) {
        // make canvas scale with window
        canvas.setWidth(canvas.getParent().getLayoutBounds().getWidth());
        canvas.setHeight(canvas.getParent().getLayoutBounds().getHeight());

        rigidbody.syncBody();
        world.update(delta_time);
        rigidbody.syncTransform();
        System.out.println(
                "Angle: " + transform.getAngle() + ", " + rigidbody.getBody().getTransform().getRotationAngle());

        // System.out.println("Position: " + transform.getPosition().x + ", " +
        // transform.getPosition().y);
        // transform.setPosition(transform.getPosition());

        Format f = new java.text.DecimalFormat("#0.00");
        // System.out.println("Position: " +
        // f.format(rigidbody.getBody().getTransform().getTranslationX()) + ", "
        // + f.format(rigidbody.getBody().getTransform().getTranslationY()));
        // System.out.println("Velocity: " +
        // f.format(rigidbody.getBody().getLinearVelocity().x) + ", "
        // + f.format(rigidbody.getBody().getLinearVelocity().y));

        time += delta_time;
        if (time > 3) {
            time = 0;
            body.applyForce(new Vector2(0, 20000));
        }
        // CameraTest c = (CameraTest) camera;
        // c.setScreenSize(canvas.getWidth(), canvas.getHeight());

        // set angle
        // floor.getTransform().setRotation(Math.sin(time) * 0.5);

        Draw.background(Color.BLACK);
        Draw.fill(Color.BLUE);
        Draw.circle(body.getTransform().getTranslationX(), body.getTransform().getTranslationY(), 3);
        // Draw.circle(transform.getPosition().x, transform.getPosition().y, 3);
        Draw.circle(rigidbody.getBody().getTransform().getTranslationX(),
                rigidbody.getBody().getTransform().getTranslationY(), 3);
        Draw.rect(floor.getTransform().getTranslationX(), floor.getTransform().getTranslationY(), 100, 1);

        Draw.fill(Color.RED);
        Draw.circle(child_transform.getPosition().x, child_transform.getPosition().y, 2);
    }

}
