package business_game;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.MassType;
import org.dyn4j.world.World;

import business_game.Vector2;

public class Rigidbody {
    private Body body;
    private boolean is_static = false;
    private double mass = 1.0;

    private boolean can_rotate = true;

    public Rigidbody() {
        body = new Body();
        setStatic(is_static);
        setMass(mass);
        setCanRotate(can_rotate);
    }

    public void setStatic(boolean is_static) {
        this.is_static = is_static;
        body.setMass(is_static ? MassType.INFINITE : MassType.NORMAL);
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

    public void setPosition(Vector2 position) {
        body.translate(position.x, position.y);
    }

    public Vector2 getPosition() {
        org.dyn4j.geometry.Vector2 position = body.getTransform().getTranslation();
        return new Vector2(position.x, position.y);
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
