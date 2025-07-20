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
        tiles = new Tile[5];
        mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
        this.getTileImages();
        this.loadMap("/res/maps/map_0_1.txt");
    }

    private void getTileImages() {
        try {
            tiles[0] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/GrassLand.png")), false);
            tiles[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/Sand.png")), false);
            tiles[2] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/Water.png")), true);
            tiles[3] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/Wall.png")), true);
            tiles[4] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/Wood.png")), false);
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
            while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
                String line = bufferedReader.readLine();
                while(col < gamePanel.maxScreenCol) {
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

    public void draw(Graphics2D graphics2D) {
        for (int col = 0; col < gamePanel.maxScreenCol; col++) {
            for (int row = 0; row < gamePanel.maxScreenRow; row++) {

                graphics2D.drawImage(tiles[mapTileNum[col][row]].getImage(), gamePanel.getTileSize() * col, gamePanel.getTileSize() * row, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            }
        }
    }
}
