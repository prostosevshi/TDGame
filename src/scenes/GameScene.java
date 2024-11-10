package scenes;

import main.Game;

import java.awt.image.BufferedImage;

public class GameScene {

    protected Game game;
    protected int animationIndex;
    protected int ANIMATION_SPEED = 20;
    protected int tick;

    public GameScene(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    protected boolean isAnimation(int spriteID) {
        return getGame().getTileManager().isSpriteAnimation(spriteID);
    }

    protected BufferedImage getSprite(int spriteID) {
        return getGame().getTileManager().getSprite(spriteID);
    }

    protected BufferedImage getSprite(int spriteID, int animationIndex) {
        return getGame().getTileManager().getAniSprite(spriteID, animationIndex);
    }

    protected void updateTick() {
        tick++;
        if (tick >= ANIMATION_SPEED) {
            tick = 0;
            animationIndex++;
            if (animationIndex >= 4) {
                animationIndex = 0;
            }
        }
    }
}
