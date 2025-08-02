package math;

public class Vec2D {
    private int x;
    private int y;

    public Vec2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vec2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void add(Vec2D vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    public void subtract(Vec2D vector) {
        this.x -= vector.x;
        this.y -= vector.y;
    }

    public void scalarMultiply(int value) {
        this.x *= value;
        this.y *= value;
    }

    /*
     * Calculates the dot product of two vectors
     */
    public int multiply(Vec2D vector) {
        return (this.x * vector.x) + (this.y + vector.y);
    }
}
