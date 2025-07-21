package main;

import entity.Entity;

public class CollisionDetector {
    
    private GamePanel gamePanel;

    public CollisionDetector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.solidArea.x;
        int entityRightWorldX = entity.getWorldX() + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.getWorldY() + entity.solidArea.y;
        int entityBottomWorldY = entity.getWorldY() + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.getTileSize();
        int entityRightCol = entityRightWorldX / gamePanel.getTileSize();
        int entityTopRow = entityTopWorldY / gamePanel.getTileSize();
        int entityBottomRow = entityBottomWorldY / gamePanel.getTileSize();

        int tileNumber1, tileNumber2; // only 2 tiles can hit while moving in one direction

        switch (entity.getDirection()) {
            case Entity.UP:
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gamePanel.getTileSize();
                tileNumber1 = gamePanel.tileManager.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.getMapTileNum()[entityRightCol][entityTopRow];
                break;
            case Entity.DOWN:
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gamePanel.getTileSize();
                tileNumber1 = gamePanel.tileManager.getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNumber2 = gamePanel.tileManager.getMapTileNum()[entityRightCol][entityBottomRow];
                break;
            case Entity.LEFT:
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gamePanel.getTileSize();
                tileNumber1 = gamePanel.tileManager.getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNumber2 = gamePanel.tileManager.getMapTileNum()[entityLeftCol][entityTopRow];
                break;
            case Entity.RIGHT:
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gamePanel.getTileSize();
                tileNumber1 = gamePanel.tileManager.getMapTileNum()[entityRightCol][entityBottomRow];
                tileNumber2 = gamePanel.tileManager.getMapTileNum()[entityRightCol][entityTopRow];
                break;
            default:
                tileNumber1 = -1;
                tileNumber2 = -1;
                break;
        }
        if (tileNumber1 >= 0 && tileNumber2 >= 0 && (gamePanel.tileManager.getTiles()[tileNumber1].isColliding() || gamePanel.tileManager.getTiles()[tileNumber2].isColliding())) {
            entity.collisionOn = true;
        } 
    }
}
