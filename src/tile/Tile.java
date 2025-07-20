package tile;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;
    private boolean collision = false;

    public Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }

    public boolean isColliding() {
        return collision;
    }

    public BufferedImage getImage() {
        return image;
    }
}
