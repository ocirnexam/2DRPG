package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ScaleManager {
    // SCREEN SETTINGS
    private static final int originalTileSize = 16; // 16x16 P Tile
    private static int scale = 4; // scale to higher resolution screens

    private static int tileSize = originalTileSize * scale;
    private static final int maxScreenCol = 16;
    private static final int maxScreenRow = 12;
    private static final int screenWidth = tileSize * maxScreenCol;
    private static final int screenHeight = tileSize * maxScreenRow;

    // World Settings
    private static final int maxWorldCol = 105;
    private static final int maxWorldRow = 66;
    private static final int worldWidth = tileSize * maxWorldCol;
    private static final int worldHeight = tileSize * maxWorldRow;

    public static BufferedImage scaleImage(BufferedImage image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, image.getType());
        Graphics2D scaledImageGraphics2D = scaledImage.createGraphics();
        scaledImageGraphics2D.drawImage(image, 0, 0, width, height, null);
        scaledImageGraphics2D.dispose();
        return scaledImage;
    }

    public static int getTileSize() {
        return tileSize;
    }

    public static int getMaxWorldColumns() {
        return maxWorldCol;
    }
    public static int getMaxWorldRows() {
        return maxWorldRow;
    }

    public static int getWorldWidth() {
        return worldWidth;
    }

    public static int getWorldHeight() {
        return worldHeight;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }
}
