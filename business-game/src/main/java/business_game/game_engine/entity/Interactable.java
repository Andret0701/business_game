package business_game.game_engine.entity;

import business_game.game_engine.physics.Rigidbody;
import business_game.game_engine.types.Transform;

public interface Interactable {
    public Transform getTransform();

    public Rigidbody getRigidbody();

    public void onCollision(Interactable other);
}
