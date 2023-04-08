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

import business_game.Draw;
import business_game.CameraTest;

public class GameController {
    @FXML
    private Canvas canvas;

    private World<Body> world = new World<Body>();
    private Body body = new Body();
    Rigidbody rigidbody = new Rigidbody();
    private Body floor = new Body();

    Transform transform = new Transform(0, 0, 1, 0);
    Transform transform2 = new Transform(1, 0, 1.2, 0.1);
    Transform transform3 = new Transform(1, 0, 1, 0);

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

        transform3.parent = transform2;
        transform2.parent = transform;

        Draw.init(canvas);
        camera = new CameraTest(0, 0, 10);
        Draw.setCamera(camera);
    }

    Camera camera;

    private void update() {
        world.update(1.0 / FPS);

        CameraTest c = (CameraTest) camera;
        c.setScreenSize(canvas.getWidth(), canvas.getHeight());

        Draw.background(Color.WHITE);
        Draw.fill(Color.BLUE);
        Draw.circle(transform.localToWorld(new business_game.Vector2(0, 0)).x,
                transform.localToWorld(new business_game.Vector2(0, 0)).y, 1);
        Draw.circle(transform2.localToWorld(new business_game.Vector2(0, 0)).x,
                transform2.localToWorld(new business_game.Vector2(0, 0)).y, 1);
        Draw.circle(transform3.localToWorld(new business_game.Vector2(0, 0)).x,
                transform3.localToWorld(new business_game.Vector2(0, 0)).y, 1);

        transform.angle += 0.01;
        transform2.angle -= 0.003;

        Draw.fill(Color.RED);
        // debug
        System.out.println("transform: " + transform.localToWorld(new business_game.Vector2(0, 0)).x + ", "
                + transform.localToWorld(new business_game.Vector2(0, 0)).y);
        System.out.println("transform2: " + transform2.localToWorld(new business_game.Vector2(0, 0)).x + ", "
                + transform2.localToWorld(new business_game.Vector2(0, 0)).y);
        System.out.println("transform3: " + transform3.localToWorld(new business_game.Vector2(0, 0)).x + ", "
                + transform3.localToWorld(new business_game.Vector2(0, 0)).y);

    }

}
