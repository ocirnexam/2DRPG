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

    public void update() {
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

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        image = images[direction + spriteNum];

        graphics2D.drawImage(image, getX(), getY(), gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}
