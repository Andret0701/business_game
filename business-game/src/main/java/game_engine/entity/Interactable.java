package game_engine.entity;

import game_engine.physics.Rigidbody;
import game_engine.types.Transform;

public interface Interactable {
    public Transform getTransform();

    public Rigidbody getRigidbody();

    public void onCollision(Interactable other);
}
