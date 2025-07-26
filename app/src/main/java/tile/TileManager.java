package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.ScaleManager;
import main.GamePanel;

import entity.Player;

public class TileManager {
    private GamePanel gamePanel;
    Tile[] tiles;
    int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[6];
        mapTileNum = new int[ScaleManager.getMaxWorldColumns()][ScaleManager.getMaxWorldRows()];
        this.getTileImages();
        this.loadMap("/maps/map_0_2.txt");
    }

    private void setupTile(int index, String imageName, boolean collision) {
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tiles[index] = new Tile(ScaleManager.scaleImage(originalImage, ScaleManager.getTileSize(), ScaleManager.getTileSize()), collision);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getTileImages() {
        setupTile(0, "GrassLand", false);
        setupTile(1, "Sand", false);
        setupTile(2, "Water", true);
        setupTile(3, "Wall", true);
        setupTile(4, "Wood", false);
        setupTile(5, "Forest", true);
    }

    public void loadMap(String mapFileName) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(mapFileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;
            while (col < ScaleManager.getMaxWorldColumns() && row < ScaleManager.getMaxWorldRows()) {
                String line = bufferedReader.readLine();
                while(col < ScaleManager.getMaxWorldColumns()) {
                    String numbers[] = line.split(" ");
                    int tileNumber = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = tileNumber;
                    col++;
                }
                col = 0;
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void draw(Graphics2D graphics2D) {
        for (int col = 0; col < ScaleManager.getMaxWorldColumns(); col++) {
            for (int row = 0; row < ScaleManager.getMaxWorldRows(); row++) {
                int worldX = col * ScaleManager.getTileSize();
                int worldY = row * ScaleManager.getTileSize();
                int screenX = worldX - gamePanel.player.getWorldX() + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.getWorldY() + gamePanel.player.screenY;
                graphics2D.drawImage(tiles[mapTileNum[col][row]].getImage(), screenX, screenY, null);
            }
        }
    }
}
