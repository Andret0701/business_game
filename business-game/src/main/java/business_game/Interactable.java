package business_game;

public interface Interactable {
    public Transform getTransform();

    public Rigidbody getRigidbody();

    public void onCollision(Interactable other);
}
