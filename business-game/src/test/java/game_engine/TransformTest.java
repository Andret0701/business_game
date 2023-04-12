package game_engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import game_engine.types.Transform;
import game_engine.types.Vector2;

public class TransformTest {
    @Test
    public void testTransformGettersAndSetters() {
        Transform transform = new Transform();
        transform.setPosition(new Vector2(1, 1));
        transform.setAngle(1.4);
        transform.setScale(3.1);

        assertEquals(transform.getPosition().x, 1);
        assertEquals(transform.getPosition().y, 1);
        assertEquals(transform.getAngle(), 1.4);
        assertEquals(transform.getScale(), 3.1);
    }

    @Test
    public void testTransformMove() {
        Transform transform = new Transform();
        transform.setPosition(new Vector2(1, 1));
        transform.setAngle(1.4);
        transform.setScale(3.0);

        transform.translate(new Vector2(1, 1));
        transform.rotate(1.4);
        transform.scale(3.1);

        assertEquals(transform.getPosition().x, 2);
        assertEquals(transform.getPosition().y, 2);
        assertEquals(transform.getAngle(), 2.8);
        assertEquals(transform.getScale(), 3.0 * 3.1);
    }

}
