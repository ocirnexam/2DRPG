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

public class TileManager {
    private GamePanel gamePanel;
    Tile[] tiles;
    int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[40];
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
        setupTile(6, "GrassLandBase", false);
        setupTile(7, "SandBase", false);
        setupTile(8, "WaterBase", true);
        setupTile(10, "GrassToWaterTop", true);
        setupTile(11, "GrassToWaterTopLeftCorner", true);
        setupTile(12, "GrassToWaterTopRightCorner", true);
        setupTile(13, "GrassToWaterTopRightCornerOut", true);
        setupTile(14, "GrassToWaterTopLeftCornerOut", true);
        setupTile(15, "GrassToWaterBottomLeftCorner", true);
        setupTile(16, "GrassToWaterBottomRightCorner", true);
        setupTile(17, "GrassToWaterLeft", true);
        setupTile(18, "GrassToWaterRight", true);
        setupTile(19, "GrassToWaterBottom", true);
        setupTile(20, "GrassToWaterSingleVertical", true);
        setupTile(21, "GrassToWaterSingleHorizontal", true);
        setupTile(22, "GrassToWaterRightU", true);
        setupTile(23, "GrassToWaterLeftU", true);
        setupTile(24, "GrassToWaterTopU", true);
        setupTile(25, "GrassToWaterBottomU", true);
        setupTile(26, "GrassToSandTop", false);
        setupTile(27, "GrassToSandLeft", false);
        setupTile(28, "GrassToSandRight", false);
        setupTile(29, "GrassToSandBottom", false);
        setupTile(30, "GrassToSandCornerTopLeft", false);
        setupTile(31, "GrassToSandCornerTopRight", false);
        setupTile(32, "GrassToSandCornerBottomLeft", false);
        setupTile(33, "GrassToSandCornerBottomRight", false);
        setupTile(34, "GrassToSandCornerTopLeftOut", false);
        setupTile(35, "GrassToSandCornerTopRightOut", false);
        setupTile(36, "GrassToSandCornerBottomLeftOut", false);
        setupTile(37, "GrassToSandCornerBottomRightOut", false);
        setupTile(38, "WallVertical", true);
        setupTile(39, "WallHorizontal", true);
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
                    if (numbers[col].startsWith("0"))
                    {
                        numbers[col] = numbers[col].substring(1);
                    }
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
