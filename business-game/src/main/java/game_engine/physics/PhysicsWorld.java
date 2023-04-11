package game_engine.physics;

import org.dyn4j.collision.Fixture;
import org.dyn4j.dynamics.Body;
import org.dyn4j.world.ManifoldCollisionData;
import org.dyn4j.world.World;

import org.dyn4j.world.listener.CollisionListenerAdapter;

import game_engine.entity.Interactable;
import game_engine.interfaces.Updateable;
import game_engine.types.Transform;
import game_engine.types.Vector2;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;

import java.util.HashMap;
import java.util.List;

import org.dyn4j.dynamics.BodyFixture;

public class PhysicsWorld implements Updateable {
    private World<Body> world = new World<Body>();

    private class Collision extends CollisionListenerAdapter<Body, BodyFixture> {
        @Override
        public boolean collision(ManifoldCollisionData<Body, BodyFixture> collisionData) {
            Body b1 = collisionData.getBody1();
            Body b2 = collisionData.getBody2();

            Rigidbody r1 = (Rigidbody) b1.getUserData();
            Rigidbody r2 = (Rigidbody) b2.getUserData();

            Interactable i1 = r1.interactable;
            Interactable i2 = r2.interactable;

            if (i1 != null && i2 != null) {
                if (!r2.getTrigger())
                    i1.onCollision(i2);
                if (!r1.getTrigger())
                    i2.onCollision(i1);
            }
            return true;
        }
    }

    private Collision collision = new Collision();
    private Vector2 gravity = new Vector2(0, -9.81);

    public void setGravity(Vector2 gravity) {
        this.gravity = gravity;
    }

    public PhysicsWorld() {
        world.addCollisionListener(collision);
    }

    @Override
    public void update(double delta_time) {
        for (Rigidbody rigidbody : bodies.keySet()) {
            if (rigidbody.getSimulated()) {
                rigidbody.update(delta_time);
                syncBody(rigidbody, bodies.get(rigidbody));
            }
        }

        world.setGravity(new org.dyn4j.geometry.Vector2(gravity.x, gravity.y));
        int n = 1;
        for (int i = 0; i < n; i++)
            world.update(delta_time / n);

        for (Rigidbody rigidbody : bodies.keySet()) {
            if (rigidbody.getSimulated())
                syncRigidbody(rigidbody, bodies.get(rigidbody));
        }
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

        body.setUserData(rigidbody);
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
        if (rigidbody.getStatic())
            return;
        org.dyn4j.geometry.Transform transform = body.getTransform();
        Vector2 position = new Vector2(transform.getTranslationX(), transform.getTranslationY());
        double angle = transform.getRotationAngle();

        Vector2 velocity = new Vector2(body.getLinearVelocity().x, body.getLinearVelocity().y);
        double angular_velocity = body.getAngularVelocity();

        Transform rigidbody_transform = rigidbody.interactable.getTransform();
        rigidbody_transform.setPosition(position);
        rigidbody_transform.setAngle(angle);

        rigidbody.setVelocity(velocity);
        rigidbody.setAngularVelocity(angular_velocity);
    }

    private void syncBody(Rigidbody rigidbody, Body body) {
        Transform rigidbody_transform = rigidbody.interactable.getTransform();
        Vector2 position = rigidbody_transform.getPosition();
        double angle = rigidbody_transform.getAngle();

        Vector2 velocity = rigidbody.getVelocity();
        double angular_velocity = rigidbody.getAngularVelocity();

        org.dyn4j.geometry.Transform transform = body.getTransform();
        transform.setTranslation(position.x, position.y);
        transform.setRotation(angle);

        body.setLinearVelocity(velocity.x, velocity.y);
        body.setAngularVelocity(angular_velocity);

        body.setAtRest(!rigidbody.getSimulated());

        updateMass(body, rigidbody.getMass(), rigidbody.getStatic(), rigidbody.getCanRotate());

        // fix this later
        for (Fixture fixture : body.getFixtures()) {
            fixture.setSensor(rigidbody.getTrigger());
        }
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
