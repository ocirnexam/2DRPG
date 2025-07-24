package main;

import java.awt.Rectangle;

import entity.Entity;
import entity.Player;
import interactiveObject.InteractiveObject;
import tile.TileManager;

public class CollisionManager {
    
    private GamePanel gamePanel;

    public CollisionManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkCollisionWithSolidTiles(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.solidArea.x;
        int entityRightWorldX = entity.getWorldX() + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.getWorldY() + entity.solidArea.y;
        int entityBottomWorldY = entity.getWorldY() + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.getTileSize();
        int entityRightCol = entityRightWorldX / gamePanel.getTileSize();
        int entityTopRow = entityTopWorldY / gamePanel.getTileSize();
        int entityBottomRow = entityBottomWorldY / gamePanel.getTileSize();

        int tileNumber1, tileNumber2; // only 2 tiles can hit while moving in one direction

        TileManager tileManager = gamePanel.getTileManager();

        switch (entity.getDirection()) {
            case Entity.UP:
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gamePanel.getTileSize();
                tileNumber1 = tileManager.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNumber2 = tileManager.getMapTileNum()[entityRightCol][entityTopRow];
                break;
            case Entity.DOWN:
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gamePanel.getTileSize();
                tileNumber1 = tileManager.getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNumber2 = tileManager.getMapTileNum()[entityRightCol][entityBottomRow];
                break;
            case Entity.LEFT:
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gamePanel.getTileSize();
                tileNumber1 = tileManager.getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNumber2 = tileManager.getMapTileNum()[entityLeftCol][entityTopRow];
                break;
            case Entity.RIGHT:
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gamePanel.getTileSize();
                tileNumber1 = tileManager.getMapTileNum()[entityRightCol][entityBottomRow];
                tileNumber2 = tileManager.getMapTileNum()[entityRightCol][entityTopRow];
                break;
            default:
                tileNumber1 = -1;
                tileNumber2 = -1;
                break;
        }
        if (tileNumber1 >= 0 && tileNumber2 >= 0 && (tileManager.getTiles()[tileNumber1].isColliding() || tileManager.getTiles()[tileNumber2].isColliding())) {
            entity.collisionOn = true;
        } 
    }

    public int checkCollisionWithInteractiveObject(Entity entity) {
        int indexOfCollidingObject = -1;
        InteractiveObject[] interactiveObjects = gamePanel.getInteractiveObjectManager().getInteractiveObjects();
        boolean isPlayer = entity.getClass().equals(Player.class);

        for (int i = 0; i < InteractiveObjectManager.MAX_SIZE; i++) {

            if (interactiveObjects[i] == null) {
                continue;
            }

            Rectangle objectBoundaries = interactiveObjects[i].getBoundaries();
            Rectangle entitySolidAreaWorld = (Rectangle) entity.solidArea.clone();
            entitySolidAreaWorld.y += entity.getWorldY();
            entitySolidAreaWorld.x += entity.getWorldX();

            switch (entity.getDirection()) {
                case Entity.UP:
                    entitySolidAreaWorld.y -= entity.getSpeed();
                case Entity.DOWN:
                    entitySolidAreaWorld.y += entity.getSpeed();
                    break;
                case Entity.LEFT:
                    entitySolidAreaWorld.x -= entity.getSpeed();
                    break;
                case Entity.RIGHT:
                    entitySolidAreaWorld.x += entity.getSpeed();
                    break;
            
                default:
                    break;
            }

            // continue with next object if this one is not intersecting with the entity
            if (!entitySolidAreaWorld.intersects(objectBoundaries)) {
                continue;
            }

            if (interactiveObjects[i].isColliding()) {
                entity.collisionOn = true;
            }
            if (isPlayer) {
                indexOfCollidingObject = i;
            }
        }
        return indexOfCollidingObject;
    }
}
