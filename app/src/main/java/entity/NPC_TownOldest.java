package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.GamePanel;
import main.ScaleManager;

public class NPC_TownOldest extends NPC {

    public NPC_TownOldest(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    protected void setDefaultValues() {
        setWorldX(47 * ScaleManager.getTileSize());
        setWorldY(54 * ScaleManager.getTileSize());
        setSpeed(1);
        direction = DOWN;
    }

    @Override
    protected void getSprites() {
        images = new BufferedImage[10];
        setupEntityImage(0, "/npc/TownOldest_Front1");
        setupEntityImage(1, "/npc/TownOldest_Front2");
        setupEntityImage(2, "/npc/TownOldest_Back1");
        setupEntityImage(3, "/npc/TownOldest_Back2");
        setupEntityImage(4, "/npc/TownOldest_Right1");
        setupEntityImage(5, "/npc/TownOldest_Right2");
        setupEntityImage(6, "/npc/TownOldest_Left1");
        setupEntityImage(7, "/npc/TownOldest_Left2");
        setupEntityImage(8, "/npc/TownOldest_Front_Standing");
        setupEntityImage(9, "/npc/TownOldest_Back_Standing");
    }

    public boolean isInView() {
        return (this.getWorldX() + ScaleManager.getTileSize() > gamePanel.player.getWorldX() - gamePanel.player.screenX &&
                this.getWorldX() - ScaleManager.getTileSize() < gamePanel.player.getWorldX() + gamePanel.player.screenX &&
                this.getWorldY() + ScaleManager.getTileSize() > gamePanel.player.getWorldY() - gamePanel.player.screenY &&
                this.getWorldY() - ScaleManager.getTileSize() < gamePanel.player.getWorldY() + gamePanel.player.screenY);
    }

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
        collisionOn = false;
        gamePanel.getCollisionManager().checkCollisionWithSolidTiles(this);
        while (collisionOn == true) {
            direction += NEXT_DIRECTION;
            direction %= MAX_DIRECTIONS;
            collisionOn = false;
            gamePanel.getCollisionManager().checkCollisionWithSolidTiles(this);

        }
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
        int screenX = worldX - gamePanel.player.getWorldX() + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.getWorldY() + gamePanel.player.screenY;
        BufferedImage image;

        if (spriteNum < 8) {
            image = images[direction + spriteNum];
        } else {
            image = images[spriteNum];
        }

        if (isInView()) {
            graphics2D.drawImage(image, screenX, screenY, null);
        }
    }
}
