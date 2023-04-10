package business_game;

import javafx.scene.paint.Color;

public class TestEntity extends Entity implements Interactable, Drawable {
    Rigidbody rigidbody;

    public TestEntity() {
        super();
        rigidbody = new Rigidbody(this);
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
    public void onCollision(Interactable other) {
        System.out.println(other.getRigidbody());
    }

    @Override
    public Entity copy() {
        TestEntity entity = new TestEntity();
        entity.transform.setTransform(transform);
        return entity;
    }

}
