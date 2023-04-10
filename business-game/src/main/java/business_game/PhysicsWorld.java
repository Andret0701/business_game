package business_game;

import org.dyn4j.dynamics.Body;
import org.dyn4j.world.World;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;

import java.util.HashMap;
import java.util.List;

public class PhysicsWorld implements Updateable {
    private World<Body> world = new World<Body>();

    Vector2 gravity = new Vector2(0, -9.81);

    public PhysicsWorld() {

    }

    @Override
    public void update(double delta_time) {
        for (Rigidbody rigidbody : bodies.keySet())
            syncBody(rigidbody, bodies.get(rigidbody));

        world.setGravity(new org.dyn4j.geometry.Vector2(gravity.x, gravity.y));
        world.update(delta_time);

        for (Rigidbody rigidbody : bodies.keySet())
            syncRigidbody(rigidbody, bodies.get(rigidbody));
    }

    private HashMap<Rigidbody, Body> bodies = new HashMap<Rigidbody, Body>();

    public void addRigidbody(Rigidbody rigidbody) {
        Body body = createBody(rigidbody);
        bodies.put(rigidbody, body);
        world.addBody(body);
    }

    public void removeRigidbody(Rigidbody rigidbody) {
        Body body = bodies.get(rigidbody);
        bodies.remove(rigidbody);
        world.removeBody(body);
    }

    private Body createBody(Rigidbody rigidbody) {
        Body body = new Body();
        updateMass(body, rigidbody.getMass(), rigidbody.getStatic(), rigidbody.getCanRotate());

        List<Collider> colliders = rigidbody.getColliders();
        for (Collider collider : colliders) {
            Convex convex = createConvex(collider);
            body.addFixture(convex);
        }
        return body;
    }

    private Convex createConvex(Collider collider) {
        Convex convex = null;
        if (collider instanceof BoxCollider) {
            BoxCollider box_collider = (BoxCollider) collider;
            convex = new Rectangle(box_collider.getWidth(), box_collider.getHeight());
        } else if (collider instanceof CircleCollider) {
            CircleCollider circle_collider = (CircleCollider) collider;
            convex = new Circle(circle_collider.getRadius());
        }

        if (convex != null)
            convex.translate(collider.getOffset().x, collider.getOffset().y);

        return convex;
    }

    private void syncRigidbody(Rigidbody rigidbody, Body body) {
        org.dyn4j.geometry.Transform transform = body.getTransform();
        Vector2 position = new Vector2(transform.getTranslationX(), transform.getTranslationY());
        double angle = transform.getRotationAngle();

        Vector2 velocity = new Vector2(body.getLinearVelocity().x, body.getLinearVelocity().y);
        double angular_velocity = body.getAngularVelocity();

        rigidbody.transform.setPosition(position);
        rigidbody.transform.setAngle(angle);

        rigidbody.setVelocity(velocity);
        rigidbody.setAngularVelocity(angular_velocity);
    }

    private void syncBody(Rigidbody rigidbody, Body body) {
        Vector2 position = rigidbody.transform.getPosition();
        double angle = rigidbody.transform.getAngle();

        Vector2 velocity = rigidbody.getVelocity();
        double angular_velocity = rigidbody.getAngularVelocity();

        org.dyn4j.geometry.Transform transform = body.getTransform();
        transform.setTranslation(position.x, position.y);
        transform.setRotation(angle);

        body.setLinearVelocity(velocity.x, velocity.y);
        body.setAngularVelocity(angular_velocity);

        updateMass(body, rigidbody.getMass(), rigidbody.getStatic(), rigidbody.getCanRotate());
    }

    private void updateMass(Body body, double mass, boolean is_static, boolean can_rotate) {
        Mass body_mass = body.getMass();
        body.setMass(new Mass(body_mass.getCenter(), mass, body_mass.getInertia()));
        if (is_static)
            body.setMass(MassType.INFINITE);
        else if (can_rotate)
            body.setMass(MassType.NORMAL);
        else
            body.setMass(MassType.FIXED_ANGULAR_VELOCITY);
    }

}
