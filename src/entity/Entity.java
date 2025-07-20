package entity;

import java.awt.image.BufferedImage;

public abstract class Entity {
    private int x, y;
    private int speed;

    public BufferedImage down1, down2;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    Direction direction;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                setY(getY() - getSpeed());
                break;
            case DOWN:
                setY(getY() + getSpeed());
                break;
            case LEFT:
                setX(getX() - getSpeed());
                break;
            case RIGHT:
                setX(getX() + getSpeed());
                break;
        
            default:
                break;
        }
        this.direction = direction;
    }
}
