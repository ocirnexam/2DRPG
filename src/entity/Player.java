package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import interactiveObject.InteractiveObject;
import main.GamePanel;
import main.InteractiveObjectManager;
import main.KeyboardHandler;
import main.SoundManager;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyboardHandler keyboardHandler;
    int storedKeys = 0;

    public final int screenX;
    public final int screenY;

    public Player (GamePanel gamePanel, KeyboardHandler keyboardHandler) {
        this.gamePanel = gamePanel;
        this.keyboardHandler = keyboardHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.getTileSize() / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.getTileSize() / 2);

        solidArea = new Rectangle(16, 28, 32, 32);

        this.setDefaultValues();
        this.getPlayerImage();
    }

    private void setDefaultValues() {
        setWorldX(24 * gamePanel.getTileSize());
        setWorldY(12 * gamePanel.getTileSize());
        setSpeed(4);
        direction = DOWN;
    }
 
    private void getPlayerImage() {
        images = new BufferedImage[8];
        try {
            images[0] = ImageIO.read(getClass().getResourceAsStream("/res/player/Character_Front1.png"));
            images[1] = ImageIO.read(getClass().getResourceAsStream("/res/player/Character_Front2.png"));
            images[2] = ImageIO.read(getClass().getResourceAsStream("/res/player/Character_Back1.png"));
            images[3] = ImageIO.read(getClass().getResourceAsStream("/res/player/Character_Back2.png"));
            images[4] = ImageIO.read(getClass().getResourceAsStream("/res/player/Character_Right1.png"));
            images[5] = ImageIO.read(getClass().getResourceAsStream("/res/player/Character_Right2.png"));
            images[6] = ImageIO.read(getClass().getResourceAsStream("/res/player/Character_Left1.png"));
            images[7] = ImageIO.read(getClass().getResourceAsStream("/res/player/Character_Left2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                gamePanel.getUIManager().showMessage("You found a key!");
                break;
            case "door":
                if (storedKeys > 0) {
                    storedKeys--;
                    executeInteraction = true;
                    gamePanel.getUIManager().showMessage("Door opened!");
                } else {
                    gamePanel.getUIManager().showMessage("Door locked!");
                }
                break;
            case "chest":
                gamePanel.getUIManager().setGameFinished();
                gamePanel.stopMusic();
                gamePanel.playSoundEffect(SoundManager.WALKING_SOUND);
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

    public void update() {
        if (keyboardHandler.isAnyMoveKeyPressed()) {
            if (keyboardHandler.upPressed) {
                this.move(UP);
            } 
            else if (keyboardHandler.downPressed) {
                this.move(DOWN);
            }
            else if (keyboardHandler.leftPressed) {
                this.move(LEFT);
            }
            else if (keyboardHandler.rightPressed) {
                this.move(RIGHT);
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum++;
                spriteNum %= 2;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        image = images[direction + spriteNum];

        graphics2D.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }

    @Override
    public String toString() {
        return "Player(x: " + getWorldX() + ", y: " + getWorldY() + ", hasKey: " + hasKey() + ", direction: " + getDirection() + ")";
    }
}
