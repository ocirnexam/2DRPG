package interactiveObject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class InteractiveObject {
    private BufferedImage image;
    private String name;
    private boolean collision;
    private int worldX;
    private int worldY;
    protected final Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public InteractiveObject() {

    }

    public BufferedImage getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public boolean isColliding() {
        return collision;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public Rectangle getBoundaries() {
        return solidArea;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }
    
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.getWorldX() + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.getWorldY() + gamePanel.player.screenY;
        graphics2D.drawImage(getImage(), screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}
