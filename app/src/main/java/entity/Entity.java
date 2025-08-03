package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.ScaleManager;
import math.Vec2D;

public abstract class Entity {

    protected Vec2D worldCoordinates = new Vec2D();
    protected int speed;

    protected GamePanel gamePanel;

    // sprite array
    protected BufferedImage[] images;

    // sprite update variables
    protected int spriteCounter = 0;
    protected int spriteNum = 0;

    // Collsision details
    public Rectangle solidArea = new Rectangle(16, 28, 32, 32);;
    public boolean collisionOn = false;

    public static final int DOWN = 0;
    public static final int RIGHT = 2;
    public static final int UP = 4; 
    public static final int LEFT = 6;

    protected int direction;

    protected boolean isStanding;

    protected Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setDefaultValues();
        getSprites();
    }
    
    protected void setupEntityImage(int index, String imagePath) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            BufferedImage scaledImage = new BufferedImage(ScaleManager.getTileSize(), ScaleManager.getTileSize(), image.getType());
            Graphics2D scaledImageGraphics2D = scaledImage.createGraphics();
            scaledImageGraphics2D.drawImage(image, 0, 0, ScaleManager.getTileSize(), ScaleManager.getTileSize(), null);
            images[index] = scaledImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWorldX(int x) {
        this.worldCoordinates.setX(x);
    }

    public void setWorldY(int y) {
        this.worldCoordinates.setY(y);
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public int getWorldX() {
        return this.worldCoordinates.getX();
    }

    public int getWorldY() {
        return this.worldCoordinates.getY();
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getDirection() {
        return direction;
    }

    public void move(int direction) {
        switch (direction) {
            case UP:
                setWorldY(getWorldY() - getSpeed());
                break;
            case DOWN:
                setWorldY(getWorldY() + getSpeed());
                break;
            case LEFT:
                setWorldX(getWorldX() - getSpeed());
                break;
            case RIGHT:
                setWorldX(getWorldX() + getSpeed());
                break;
        
            default:
                break;
        }
        this.direction = direction;
    }

    protected abstract void setDefaultValues();

    protected abstract void getSprites();

    public abstract void draw(Graphics2D graphics2D);

    public abstract void update();
}
