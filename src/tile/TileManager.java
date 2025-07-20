package tile;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tiles;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[4];
        this.getTileImages();
    }

    private void getTileImages() {
        try {
            tiles[0] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/GrassLand.png")), false);
            tiles[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/Sand.png")), false);
            tiles[2] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/Water.png")), true);
            tiles[3] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/Wall.png")), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (int col = 0; col < gamePanel.maxScreenCol; col++) {
            for (int row = 0; row < gamePanel.maxScreenRow; row++) {
                graphics2D.drawImage(tiles[0].getImage(), gamePanel.getTileSize() * col, gamePanel.getTileSize() * row, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            }
        }
    }
}
