package business_game;

import java.util.ArrayList;
import java.util.List;

public class Rigidbody {
    private boolean is_static = false;
    private double mass = 1.0;
    private boolean can_rotate = true;
    public Transform transform;
    private Vector2 velocity = new Vector2(0, 0);
    private double angular_velocity = 0;

    public Rigidbody(Transform transform) {
        this.transform = transform;
    }

    public void setStatic(boolean is_static) {
        this.is_static = is_static;
    }

    public boolean getStatic() {
        return is_static;
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
        return velocity;
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

    private List<Collider> colliders = new ArrayList<Collider>();

    public void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public List<Collider> getColliders() {
        return colliders;
    }
}
