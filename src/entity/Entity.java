package entity;

import java.awt.image.BufferedImage;

public abstract class Entity {
    private int worldX, worldY;
    private int speed;

    public BufferedImage down1, down2;
    public BufferedImage[] images;

    public int spriteCounter = 0;
    public int spriteNum = 0;

    public final int DOWN = 0;
    public final int UP = 2; 
    public final int RIGHT = 4;
    public final int LEFT = 6;

    protected int direction;

    public void setWorldX(int x) {
        this.worldX = x;
    }

    public void setWorldY(int y) {
        this.worldY = y;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public int getWorldX() {
        return this.worldX;
    }

    public int getWorldY() {
        return this.worldY;
    }

    public int getSpeed() {
        return this.speed;
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
}
