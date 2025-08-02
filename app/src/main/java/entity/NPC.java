package entity;

import main.GamePanel;
import main.ScaleManager;

public abstract class NPC extends Entity {

    private int actionLockCounter = 0;

    protected final static int ACTION_UNLOCKED = 0;

    protected static final int NEXT_DIRECTION = 2;
    protected static final int MAX_DIRECTIONS = 9;

    private static final int ACTION_CHANGE_DELAY = 180; // 180 ticks = 3 secs

    protected NPC(GamePanel gamePanel) {
        super(gamePanel);
    }

    protected int getActionLockCounter() {
        return actionLockCounter;
    }

    public boolean isInView() {
        return (this.getWorldX() + ScaleManager.getTileSize() > gamePanel.player.getWorldX() - gamePanel.player.getScreenX() &&
                this.getWorldX() - ScaleManager.getTileSize() < gamePanel.player.getWorldX() + gamePanel.player.getScreenX() &&
                this.getWorldY() + ScaleManager.getTileSize() > gamePanel.player.getWorldY() - gamePanel.player.getScreenY() &&
                this.getWorldY() - ScaleManager.getTileSize() < gamePanel.player.getWorldY() + gamePanel.player.getScreenY());
    }

    public void update() {
        actionLockCounter++;
        if (actionLockCounter > ACTION_CHANGE_DELAY) {
            actionLockCounter = 0;
        }
        if(getActionLockCounter() == NPC.ACTION_UNLOCKED) {
            setAction();
        }
        collisionOn = false;
        gamePanel.getCollisionManager().checkCollisionWithSolidTiles(this);
        while (collisionOn == true) {
            direction += NEXT_DIRECTION;
            direction %= MAX_DIRECTIONS;
            collisionOn = false;
            gamePanel.getCollisionManager().checkCollisionWithSolidTiles(this);

        }
    }

    protected abstract void setAction();
    
}
