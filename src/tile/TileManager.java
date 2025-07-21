package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tiles;
    int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[6];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        this.getTileImages();
        this.loadMap("/res/maps/map_0_2.txt");
    }

    private void getTileImages() {
        try {
            tiles[0] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/GrassLand.png")), false);
            tiles[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/Sand.png")), false);
            tiles[2] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/Water.png")), true);
            tiles[3] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/Wall.png")), true);
            tiles[4] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/Wood.png")), false);
            tiles[5] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/Forest.png")), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapFileName) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(mapFileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;
            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = bufferedReader.readLine();
                while(col < gamePanel.maxWorldCol) {
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
        for (int col = 0; col < gamePanel.maxWorldCol; col++) {
            for (int row = 0; row < gamePanel.maxWorldRow; row++) {
                int worldX = col * gamePanel.getTileSize();
                int worldY = row * gamePanel.getTileSize();
                int screenX = worldX - gamePanel.player.getWorldX() + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.getWorldY() + gamePanel.player.screenY;
                graphics2D.drawImage(tiles[mapTileNum[col][row]].getImage(), screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            }
        }
    }
}
