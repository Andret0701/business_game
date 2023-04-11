package business_game.animations;

import game_engine.gfx.Animation;
import game_engine.gfx.AnimationManager;
import game_engine.gfx.SpriteRenderer;
import game_engine.types.Vector2Int;

public class PlayerAnimator extends AnimationManager {
    public PlayerAnimator(SpriteRenderer sprite_renderer) {
        super(sprite_renderer);
        Vector2Int offset = new Vector2Int(-4, -14);
        addAnimation("idle", new Animation("Player/Idle/PlayerIdle", 4, offset.x, offset.y, 10));
        addAnimation("run", new Animation("Player/Run/PlayerRun", 4, offset.x, offset.y, 10));
    }
}
