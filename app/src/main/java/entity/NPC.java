package entity;

import main.GamePanel;

public abstract class NPC extends Entity {

    private int actionLockCounter = 0;

    protected final static int ACTION_UNLOCKED = 0;

    protected static final int NEXT_DIRECTION = 2;
    protected static final int MAX_DIRECTIONS = 9;

    protected NPC(GamePanel gamePanel) {
        super(gamePanel);
    }

    protected int getActionLockCounter() {
        return actionLockCounter;
    }

    public void update() {
        actionLockCounter++;
        if (actionLockCounter > 100) {
            actionLockCounter = 0;
        }
    }

    protected abstract void setAction();
    
}
