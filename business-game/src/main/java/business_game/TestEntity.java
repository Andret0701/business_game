package business_game;

import javafx.scene.paint.Color;

public class TestEntity extends Entity implements Interactable, Drawable {
    Rigidbody rigidbody;

    public TestEntity() {
        super();
        rigidbody = new Rigidbody(this.transform);
        rigidbody.addCollider(new CircleCollider(5, 0, 0));
    }

    @Override
    public void draw(double x, double y, double angle, double scale) {
        Draw.fill(Color.RED);
        Draw.circle(x, y, scale * 5);
    }

    @Override
    public double getLayer() {
        return 0;
    }

    @Override
    public boolean isVisable() {
        return true;
    }

    @Override
    public Rigidbody getRigidbody() {
        return rigidbody;
    }

    @Override
    public Entity copy() {
        TestEntity testEntity = new TestEntity();
        testEntity.transform.setTransform(this.transform);
        return testEntity;
    }
}
