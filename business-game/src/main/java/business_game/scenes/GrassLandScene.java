package business_game.scenes;

import business_game.entities.FollowCamera;
import business_game.entities.Player;
import business_game.entities.TestEntity;
import business_game.tiles.GrassTile;
import game_engine.Scene;
import game_engine.entity.Tilemap;
import game_engine.types.Vector2;

public class GrassLandScene extends Scene {
    public GrassLandScene() {
        super("Grass Land");
        Player player = new Player();
        add(player, Math.random() * 100, Math.random() * 10);

        for (int i = 0; i < 100; i++) {
            Vector2 position = new Vector2((Math.random() - 0.5) * 100, (Math.random() - 0.5) * 100);
            while (position.dist(player.getTransform().getPosition()) < 20) {
                position = new Vector2((Math.random() - 0.5) * 100, (Math.random() - 0.5) * 100);
            }
            add(new TestEntity(), position.x, position.y);
        }

        FollowCamera camera = new FollowCamera(4, 2, "Player");
        add(camera, 0, 0);

        Tilemap tilemap = new Tilemap(16, 16);
        tilemap.fill(new GrassTile());
        add(tilemap, 0, 0);

    }
}
