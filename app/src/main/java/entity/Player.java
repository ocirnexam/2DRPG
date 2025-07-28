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
import main.ScaleManager;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyboardHandler keyboardHandler;
    int storedKeys = 0;

    public final int screenX;
    public final int screenY;

    public Player (GamePanel gamePanel, KeyboardHandler keyboardHandler) {
        this.gamePanel = gamePanel;
        this.keyboardHandler = keyboardHandler;

        screenX = ScaleManager.getScreenWidth() / 2 - (ScaleManager.getTileSize() / 2);
        screenY = ScaleManager.getScreenHeight() / 2 - (ScaleManager.getTileSize() / 2);

        solidArea = new Rectangle(16, 28, 32, 32);

        this.setDefaultValues();
        this.getPlayerImage();
    }

    private void setDefaultValues() {
        setWorldX(47 * ScaleManager.getTileSize());
        setWorldY(54 * ScaleManager.getTileSize());
        setSpeed(4);
        direction = DOWN;
    }

    private void setupPlayerImage(int index, String imageName) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            BufferedImage scaledImage = new BufferedImage(ScaleManager.getTileSize(), ScaleManager.getTileSize(), image.getType());
            Graphics2D scaledImageGraphics2D = scaledImage.createGraphics();
            scaledImageGraphics2D.drawImage(image, 0, 0, ScaleManager.getTileSize(), ScaleManager.getTileSize(), null);
            images[index] = scaledImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    private void getPlayerImage() {
        images = new BufferedImage[10];
            setupPlayerImage(0, "Character_Front1");
            setupPlayerImage(1, "Character_Front2");
            setupPlayerImage(2, "Character_Back1");
            setupPlayerImage(3, "Character_Back2");
            setupPlayerImage(4, "Character_Right1");
            setupPlayerImage(5, "Character_Right2");
            setupPlayerImage(6, "Character_Left1");
            setupPlayerImage(7, "Character_Left2");
            setupPlayerImage(8, "Character_Front_Standing");
            setupPlayerImage(9, "Character_Back_Standing");
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

        graphics2D.drawImage(image, screenX, screenY, null);
    }

    @Override
    public String toString() {
        return "Player(x: " + getWorldX() + ", y: " + getWorldY() + ", hasKey: " + hasKey() + ", direction: " + getDirection() + ")";
    }
}
