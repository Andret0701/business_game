package business_game;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.MassType;

public class Rigidbody {
    private Body body;
    private boolean is_static = false;
    private double mass = 1.0;

    private boolean can_rotate = true;

    private Transform transform;

    public Rigidbody(Transform transform) {
        this.body = new Body();
        this.transform = transform;

        setStatic(is_static);
        // setMass(mass);
        // setCanRotate(can_rotate);
    }

    public void syncBody() {
        org.dyn4j.geometry.Transform body_transform = body.getTransform();
        Vector2 position = transform.getPosition();
        double angle = transform.getAngle();
        double scale = transform.getScale();

        body_transform.setTranslation(position.x, position.y);
        body_transform.setRotation(angle);

        // fix scale - dyn4j doesn't support it
    }

    public void syncTransform() {
        org.dyn4j.geometry.Transform body_transform = body.getTransform();
        org.dyn4j.geometry.Vector2 position = body_transform.getTranslation();
        double angle = body_transform.getRotationAngle();
        // double scale = transform.getScale();

        transform.setPosition(new Vector2(position.x, position.y));
        transform.setAngle(angle);

        // fix scale - dyn4j doesn't support it
    }

    public void setStatic(boolean is_static) {
        this.is_static = is_static;
        body.setMass(is_static ? MassType.INFINITE : MassType.NORMAL);
        body.setMass(MassType.NORMAL);
    }

    public void setMass(double mass) {
        this.mass = mass;
        Mass body_mass = body.getMass();
        body.setMass(new Mass(body_mass.getCenter(), mass, body_mass.getInertia()));
    }

    public void setCanRotate(boolean can_rotate) {
        this.can_rotate = can_rotate;
        body.setMass(can_rotate ? MassType.NORMAL : MassType.FIXED_ANGULAR_VELOCITY);
    }

    public void setVelocity(Vector2 velocity) {
        body.setLinearVelocity(velocity.x, velocity.y);
    }

    public Vector2 getVelocity() {
        org.dyn4j.geometry.Vector2 velocity = body.getLinearVelocity();
        return new Vector2(velocity.x, velocity.y);
    }

    public void setAngularVelocity(double angular_velocity) {
        body.setAngularVelocity(angular_velocity);
    }

    public double getAngularVelocity() {
        return body.getAngularVelocity();
    }

    public void addForce(Vector2 force) {
        body.applyForce(new org.dyn4j.geometry.Vector2(force.x, force.y));
    }

    public void addTorque(double torque) {
        body.applyTorque(torque);
    }

    public Body getBody() {
        return body;
    }
}
