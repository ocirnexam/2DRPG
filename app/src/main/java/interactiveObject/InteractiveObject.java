package interactiveObject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.ScaleManager;

import entity.Player;

public class InteractiveObject {
    private GamePanel gamePanel;
    private BufferedImage image;
    private String name;
    private boolean collision;
    private int worldX = 0;
    private int worldY = 0;
    protected final Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public InteractiveObject(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    protected void setScaledImage(String imageName) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/objects/" + imageName + ".png"));
            BufferedImage scaledImage = new BufferedImage(ScaleManager.getTileSize(), ScaleManager.getTileSize(), image.getType());
            Graphics2D scaledImageGraphics2D = scaledImage.createGraphics();
            scaledImageGraphics2D.drawImage(image, 0, 0, ScaleManager.getTileSize(), ScaleManager.getTileSize(), null);
            this.image = scaledImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void setBoundaries() {
        this.solidArea.x = this.worldX;
        this.solidArea.y = this.worldY;
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
        this.solidArea.x = this.worldX;
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.solidArea.y = this.worldY;
        this.worldY = worldY;
    }
    
    public void draw(Graphics2D graphics2D, Player player) {
        int screenX = worldX - player.getWorldX() + player.screenX;
        int screenY = worldY - player.getWorldY() + player.screenY;
        graphics2D.drawImage(getImage(), screenX, screenY, null);
    }
}
