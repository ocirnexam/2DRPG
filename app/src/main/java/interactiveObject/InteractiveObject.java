package interactiveObject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.ScaleManager;
import math.Vec2D;
import entity.Player;

public class InteractiveObject {
    private GamePanel gamePanel;
    private BufferedImage image;
    private String name;
    private boolean collision;
    private Vec2D worldCoordinates = new Vec2D();
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
        return worldCoordinates.getX();
    }

    public int getWorldY() {
        return worldCoordinates.getY();
    }

    public Rectangle getBoundaries() {
        return solidArea;
    }

    public void setBoundaries() {
        this.solidArea.x = this.getWorldX();
        this.solidArea.y = this.getWorldY();
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
        this.worldCoordinates.setX(worldX);
        this.solidArea.x = this.worldCoordinates.getX();
    }

    public void setWorldY(int worldY) {
        this.worldCoordinates.setY(worldY);
        this.solidArea.y = this.worldCoordinates.getY();
    }

    public boolean isInView() {
        return (this.getWorldX() + ScaleManager.getTileSize() > gamePanel.player.getWorldX() - gamePanel.player.getScreenX() &&
                this.getWorldX() - ScaleManager.getTileSize() < gamePanel.player.getWorldX() + gamePanel.player.getScreenX() &&
                this.getWorldY() + ScaleManager.getTileSize() > gamePanel.player.getWorldY() - gamePanel.player.getScreenY() &&
                this.getWorldY() - ScaleManager.getTileSize() < gamePanel.player.getWorldY() + gamePanel.player.getScreenY());
    }
    
    public void draw(Graphics2D graphics2D, Player player) {
        if (isInView()) {
            Vec2D screenCoordinates = new Vec2D(getWorldX() - player.getWorldX() + player.getScreenX(), getWorldY() - player.getWorldY() + player.getScreenY());
            graphics2D.drawImage(getImage(), screenCoordinates.getX(), screenCoordinates.getY(), null);
        }
    }
}
