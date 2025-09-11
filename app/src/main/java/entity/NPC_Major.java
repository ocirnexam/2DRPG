package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.GamePanel;
import main.ScaleManager;
import math.Vec2D;

public class NPC_Major extends NPC {

    public NPC_Major(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    protected void setDefaultValues() {
        setWorldX(86 * ScaleManager.getTileSize());
        setWorldY(18 * ScaleManager.getTileSize());
        setSpeed(2);
        
        dialogues = new String[6];
        maxDialogueIndex = 6;
        direction = DOWN;
    }

    @Override
    protected void setDialogues() {
        dialogues[0] = "Hello visitor and welcome to our town!\nI am the mayor.";
        dialogues[1] = "We have been expecting you.\nThe town oldest told us you would come.";
        dialogues[2] = "To find the path up the mountains,\nyou need to follow a river to the east.";
        dialogues[3] = "The river is where you met the town oldest.\n";
        dialogues[4] = "To cross the river,\nyou will need special boots.\nYou can find them in a\nchest in our town.";
        dialogues[5] = "Good luck on your journey!";        
    }

    @Override
    protected void getSprites() {
        images = new BufferedImage[10];
        setupEntityImage(0, "/npc/TownOldest_Front1");
        setupEntityImage(1, "/npc/TownOldest_Front2");
        setupEntityImage(2, "/npc/TownOldest_Right1");
        setupEntityImage(3, "/npc/TownOldest_Right2");
        setupEntityImage(4, "/npc/TownOldest_Back1");
        setupEntityImage(5, "/npc/TownOldest_Back2");
        setupEntityImage(6, "/npc/TownOldest_Left1");
        setupEntityImage(7, "/npc/TownOldest_Left2");
        setupEntityImage(8, "/npc/TownOldest_Front_Standing");
        setupEntityImage(9, "/npc/TownOldest_Back_Standing");
    }

    @Override
    protected void setAction() {
        Random random = new Random();
        int actionIndex = random.nextInt(1000);
        isStanding = false;
        if (actionIndex <= 200) {
            direction = UP;
        } else if (actionIndex > 200 && actionIndex <= 400) {
            direction = DOWN;
        } else if (actionIndex > 400 && actionIndex <= 600) {
            direction = LEFT;
        } else if (actionIndex > 600 && actionIndex <= 800) {
            direction = RIGHT;
        } else {
            isStanding = true;
        }
    }

    @Override
    public void update() {
        super.update();
        if (!collisionOn && !isStanding) {
            move(direction);
            spriteCounter++;
            if (spriteCounter > 27) {
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

    @Override
    public void draw(Graphics2D graphics2D) {
        if (isInView()) {
            Vec2D screenCoordinates = new Vec2D(getWorldX() - gamePanel.player.getWorldX() + gamePanel.player.getScreenX(),
                                                getWorldY() - gamePanel.player.getWorldY() + gamePanel.player.getScreenY());
            BufferedImage image;

            if (spriteNum < 8) {
                image = images[direction + spriteNum];
            } else {
                image = images[spriteNum];
            }
            graphics2D.drawImage(image, screenCoordinates.getX(), screenCoordinates.getY(), null);
        }
    }
}
