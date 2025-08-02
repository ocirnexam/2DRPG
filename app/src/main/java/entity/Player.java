package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import interactiveObject.InteractiveObject;
import main.GamePanel;
import main.InteractiveObjectManager;
import main.KeyboardManager;
import main.SoundManager;
import math.Vec2D;
import main.ScaleManager;

public class Player extends Entity {
    KeyboardManager keyboardManager;
    int storedKeys = 0;

    public final Vec2D screenCoordinates = new Vec2D();

    public Player (GamePanel gamePanel, KeyboardManager keyboardManager) {
        super(gamePanel);
        this.keyboardManager = keyboardManager;

        screenCoordinates.setX(ScaleManager.getScreenWidth() / 2 - (ScaleManager.getTileSize() / 2));
        screenCoordinates.setY(ScaleManager.getScreenHeight() / 2 - (ScaleManager.getTileSize() / 2));
    }

    @Override
    protected void setDefaultValues() {
        setWorldX(23 * ScaleManager.getTileSize());
        setWorldY(11 * ScaleManager.getTileSize());
        setSpeed(4);
        direction = DOWN;
    }
 
    @Override
    protected void getSprites() {
        images = new BufferedImage[10];
        setupEntityImage(0, "/player/Character_Front1");
        setupEntityImage(1, "/player/Character_Front2");
        setupEntityImage(2, "/player/Character_Back1");
        setupEntityImage(3, "/player/Character_Back2");
        setupEntityImage(4, "/player/Character_Right1");
        setupEntityImage(5, "/player/Character_Right2");
        setupEntityImage(6, "/player/Character_Left1");
        setupEntityImage(7, "/player/Character_Left2");
        setupEntityImage(8, "/player/Character_Front_Standing");
        setupEntityImage(9, "/player/Character_Back_Standing");
    }

    public int getScreenX() {
        return screenCoordinates.getX();
    }

    public int getScreenY() {
        return screenCoordinates.getY();
    }

    @Override
    public void move(int direction) {
        int indexOfCollidingObject;
        collisionOn = false;
        this.direction = direction;
        gamePanel.getCollisionManager().checkCollisionWithSolidTiles(this);
        indexOfCollidingObject = gamePanel.getCollisionManager().checkCollisionWithInteractiveObject(this);
        interactWithObject(indexOfCollidingObject);

        if (collisionOn == false) {
            super.move(direction);
        }
    }

    private void interactWithObject(int indexOfObject) {
        InteractiveObjectManager interactiveObjectManager = gamePanel.getInteractiveObjectManager();
        boolean executeInteraction = false;
        if (indexOfObject < 0) {
            return;
        }
        InteractiveObject interactiveObject = interactiveObjectManager.getInteractiveObjects()[indexOfObject];
        String objectName = interactiveObject.getName();

        switch (objectName) {
            case "key":
                storedKeys++;
                executeInteraction = true;
                gamePanel.playSoundEffect(SoundManager.PICKUP_KEY_SOUND);
                gamePanel.getUIManager().showMessage("You found a key!");
                break;
            case "door":
                if (storedKeys > 0) {
                    storedKeys--;
                    executeInteraction = true;
                    gamePanel.playSoundEffect(SoundManager.OPEN_DOOR_SOUND);
                    gamePanel.getUIManager().showMessage("Door opened!");
                } else {
                    gamePanel.getUIManager().showMessage("Door locked!");
                }
                break;
            case "chest":
                gamePanel.playSoundEffect(SoundManager.GAME_FINISH);
            default:
                break;
        }
        if(executeInteraction) {
            interactiveObjectManager.handleInteraction(indexOfObject);
        }
    }

    public boolean hasKey() {
        return storedKeys > 0;
    }

    public int getKeys() {
        return storedKeys;
    }

    @Override
    public void update() {
        if (keyboardManager.isAnyMoveKeyPressed()) {
            if (keyboardManager.upPressed) {
                this.move(UP);
            } 
            else if (keyboardManager.downPressed) {
                this.move(DOWN);
            }
            else if (keyboardManager.leftPressed) {
                this.move(LEFT);
            }
            else if (keyboardManager.rightPressed) {
                this.move(RIGHT);
            }
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum++;
                spriteNum %= 2;
                spriteCounter = 0;
            }
        } else {
            if (this.getDirection() == DOWN) {
                spriteNum = 8;
            }
            else if (this.getDirection() == UP) {
                spriteNum = 9;
            } else {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        if (spriteNum < 8) {
            image = images[direction + spriteNum];
        } else {
            image = images[spriteNum];
        }

        graphics2D.drawImage(image, screenCoordinates.getX(), screenCoordinates.getY(), null);
    }

    @Override
    public String toString() {
        return "Player(x: " + getWorldX() + ", y: " + getWorldY() + ", hasKey: " + hasKey() + ", direction: " + getDirection() + ")";
    }
}
