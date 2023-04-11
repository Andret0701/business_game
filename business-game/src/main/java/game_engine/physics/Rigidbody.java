package game_engine.physics;

import java.util.ArrayList;
import java.util.List;

import game_engine.entity.Interactable;
import game_engine.interfaces.Updateable;
import game_engine.types.Vector2;

public class Rigidbody implements Updateable {
    private boolean is_simulated = true;
    private boolean is_static = false;
    private boolean is_trigger = false;
    private double mass = 1.0;
    private boolean can_rotate = true;
    private Vector2 velocity = new Vector2(0, 0);
    private double angular_velocity = 0;

    private double damping = 0.1;
    private double angular_damping = 0.1;

    public Interactable interactable;

    public Rigidbody(Interactable interactable) {
        this.interactable = interactable;
    }

    public void setStatic(boolean is_static) {
        this.is_static = is_static;
    }

    public boolean getStatic() {
        return is_static;
    }

    public void setTrigger(boolean is_trigger) {
        this.is_trigger = is_trigger;
    }

    public boolean getTrigger() {
        return is_trigger;
    }

    public void setSimulated(boolean is_simulated) {
        this.is_simulated = is_simulated;
    }

    public boolean getSimulated() {
        return is_simulated;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        return mass;
    }

    public void setCanRotate(boolean can_rotate) {
        this.can_rotate = can_rotate;
    }

    public boolean getCanRotate() {
        return can_rotate;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getVelocity() {
        return velocity.copy();
    }

    public void setAngularVelocity(double angular_velocity) {
        this.angular_velocity = angular_velocity;
    }

    public double getAngularVelocity() {
        return angular_velocity;
    }

    public void addForce(Vector2 force) {
        velocity.add(force);
    }

    public void addTorque(double torque) {
        angular_velocity += torque;
    }

    public void setDamping(double damping) {
        this.damping = damping;
    }

    public double getDamping() {
        return damping;
    }

    public void setAngularDamping(double angular_damping) {
        this.angular_damping = angular_damping;
    }

    public double getAngularDamping() {
        return angular_damping;
    }

    private List<Collider> colliders = new ArrayList<Collider>();

    public void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public List<Collider> getColliders() {
        return colliders;
    }

    @Override
    public void update(double delta_time) {
        Vector2 velocity = getVelocity();
        double angular_velocity = getAngularVelocity();

        velocity.mul(Math.pow(1 - getDamping(), delta_time));
        angular_velocity *= Math.pow(1 - getAngularDamping(), delta_time);

        setVelocity(velocity);
        setAngularVelocity(angular_velocity);
    }

    @Override
    public String toString() {
        return "Rigidbody { " + "is_static = " + is_static + ", mass = " + mass + ", can_rotate = " + can_rotate
                + ", velocity = " + velocity + ", angular_velocity = " + angular_velocity + " }";
    }
}
