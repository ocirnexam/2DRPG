package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import math.Vec2D;

public class ScaleManager {
    // SCREEN SETTINGS
    private static final int originalTileSize = 16; // 16x16 P Tile
    private static int scale = 4; // scale to higher resolution screens

    private static int tileSize = originalTileSize * scale;
    private static final Vec2D maxScreenTiles = new Vec2D(16, 12);
    private static final Vec2D screenResolution = new Vec2D(tileSize * maxScreenTiles.getX(), tileSize * maxScreenTiles.getY());

    // World Settings
    private static final Vec2D maxWorldTiles = new Vec2D(105, 69);
    private static final Vec2D worldResolution = new Vec2D(tileSize * maxWorldTiles.getX(), tileSize * maxWorldTiles.getY());

    public static BufferedImage scaleImage(BufferedImage image, Vec2D size) {
        BufferedImage scaledImage = new BufferedImage(size.getX(), size.getY(), image.getType());
        Graphics2D scaledImageGraphics2D = scaledImage.createGraphics();
        scaledImageGraphics2D.drawImage(image, 0, 0, size.getX(), size.getY(), null);
        scaledImageGraphics2D.dispose();
        return scaledImage;
    }

    public static int getTileSize() {
        return tileSize;
    }

    public static int getMaxWorldColumns() {
        return maxWorldTiles.getX();
    }
    public static int getMaxWorldRows() {
        return maxWorldTiles.getY();
    }

    public static int getWorldWidth() {
        return worldResolution.getX();
    }

    public static int getWorldHeight() {
        return worldResolution.getY();
    }

    public static int getScreenWidth() {
        return screenResolution.getX();
    }

    public static int getScreenHeight() {
        return screenResolution.getY();
    }
}
