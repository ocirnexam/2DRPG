package entity;

import main.GamePanel;
import main.ScaleManager;

public abstract class NPC extends Entity {

    private int actionLockCounter = 0;

    protected final static int ACTION_UNLOCKED = 0;

    protected static final int NEXT_DIRECTION = 2;
    protected static final int MAX_DIRECTIONS = 9;

    private static final int ACTION_CHANGE_DELAY = 180; // 180 ticks = 3 secs

    private int currentDialogIndex = 0;

    protected String[] dialogues;
    protected int maxDialogueIndex;

    protected NPC(GamePanel gamePanel) {
        super(gamePanel);
        setDialogues();
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

    public String getCurrentDialog() {
        if (currentDialogIndex >= dialogues.length) {
            currentDialogIndex = 0;
            return null;
        }
        String dialog = dialogues[currentDialogIndex];
        currentDialogIndex++;
        return dialog;
    }

    public void speak() {
        gamePanel.getUIManager().setCurrentDialogue(getCurrentDialog());
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
        gamePanel.getCollisionManager().checkCollisionWithInteractiveObject(this);
        while (collisionOn == true) {
            direction += NEXT_DIRECTION;
            direction %= MAX_DIRECTIONS;
            collisionOn = false;
            gamePanel.getCollisionManager().checkCollisionWithInteractiveObject(this);
        }
    }
    
    protected abstract void setDialogues();

    protected abstract void setAction();
    
}
