package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyboardHandler;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyboardHandler keyboardHandler;

    public Player (GamePanel gamePanel, KeyboardHandler keyboardHandler) {
        this.gamePanel = gamePanel;
        this.keyboardHandler = keyboardHandler;
        this.setDefaultValues();
        this.getPlayerImage();
    }

    private void setDefaultValues() {
        setX(100);
        setY(100);
        setSpeed(4);
        direction = Direction.DOWN;
    }

    private void getPlayerImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Character_Front1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Character_Front2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
         if (keyboardHandler.upPressed) {
            this.move(Direction.UP);
         } 
         else if (keyboardHandler.downPressed) {
            this.move(Direction.DOWN);
         }
         else if (keyboardHandler.leftPressed) {
            this.move(Direction.LEFT);
         }
         else if (keyboardHandler.rightPressed) {
            this.move(Direction.RIGHT);
         }
         spriteCounter++;
         if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else {
                spriteNum = 1;
            }
            spriteCounter = 0;
         }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        switch (direction) {
            case Direction.DOWN:
            case Direction.UP:
            case Direction.LEFT:
            case Direction.RIGHT:
            if (spriteNum == 1) {
                image = down1;
            }
            if (spriteNum == 2) {
                image = down2;
            }
                break;
        
            default:
                break;
        }

        graphics2D.drawImage(image, getX(), getY(), gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}
