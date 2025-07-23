package main;

import java.awt.Rectangle;

import entity.Entity;
import entity.Player;
import interactiveObject.InteractiveObject;
import interactiveObject.OpenDoor;

public class CollisionManager {
    
    private GamePanel gamePanel;

    public CollisionManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkCollision(Entity entity) {
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

                if (isCollidingWithObject(entityTopWorldY - entity.getSpeed(), entityLeftWorldX, entityRightWorldX, entity.getDirection(), entity)) {
                    entity.collisionOn = true;
                }
                break;
            case Entity.DOWN:
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gamePanel.getTileSize();
                tileNumber1 = gamePanel.tileManager.getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNumber2 = gamePanel.tileManager.getMapTileNum()[entityRightCol][entityBottomRow];

                if (isCollidingWithObject(entityBottomWorldY + entity.getSpeed(), entityLeftWorldX, entityRightWorldX, entity.getDirection(), entity)) {
                    entity.collisionOn = true;
                }
                break;
            case Entity.LEFT:
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gamePanel.getTileSize();
                tileNumber1 = gamePanel.tileManager.getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNumber2 = gamePanel.tileManager.getMapTileNum()[entityLeftCol][entityTopRow];

                if (isCollidingWithObject(entityLeftWorldX - entity.getSpeed(), entityTopWorldY, entityBottomWorldY, entity.getDirection(), entity)) {
                    entity.collisionOn = true;
                }
                break;
            case Entity.RIGHT:
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gamePanel.getTileSize();
                tileNumber1 = gamePanel.tileManager.getMapTileNum()[entityRightCol][entityBottomRow];
                tileNumber2 = gamePanel.tileManager.getMapTileNum()[entityRightCol][entityTopRow];

                if (isCollidingWithObject(entityRightWorldX + entity.getSpeed(), entityTopWorldY, entityBottomWorldY, entity.getDirection(), entity)) {
                    entity.collisionOn = true;
                }
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

    private boolean isCollidingWithObject(int nextMovePosition, int startBoundaryPosition, int endBoundaryPosition, int direction, Entity entity) {
        
        InteractiveObject[] interactiveObjects = gamePanel.interactiveObjectManager.getInteractiveObjects();
        boolean isColliding = false;
        boolean isPlayer = entity.getClass().equals(Player.class);

        for (int i = 0; i < InteractiveObjectManager.MAX_SIZE; i++) {
            boolean isPlayerAndKey = (isPlayer && interactiveObjects[i] != null && interactiveObjects[i].getName().toLowerCase().equals("key"));

            if (interactiveObjects[i] != null && (interactiveObjects[i].isColliding() || isPlayerAndKey)) {
                Rectangle objectBoundaries = interactiveObjects[i].getBoundaries();
                switch (direction) {
                    case Entity.UP:
                    case Entity.DOWN:
                        if (nextMovePosition >= interactiveObjects[i].getWorldY() &&
                            nextMovePosition <= interactiveObjects[i].getWorldY() + objectBoundaries.height &&
                            ((startBoundaryPosition >= interactiveObjects[i].getWorldX() && startBoundaryPosition <= interactiveObjects[i].getWorldX() + objectBoundaries.width) ||
                              endBoundaryPosition   >= interactiveObjects[i].getWorldX() && endBoundaryPosition   <= interactiveObjects[i].getWorldX() + objectBoundaries.width)
                        )
                        {
                            isColliding = handleObjectInteractions(entity, interactiveObjects, i, isPlayer, isPlayerAndKey);
                        }
                        break;
                    case Entity.LEFT:
                    case Entity.RIGHT:
                        if (nextMovePosition <= interactiveObjects[i].getWorldX() + objectBoundaries.width &&
                            nextMovePosition >= interactiveObjects[i].getWorldX() &&
                            ((startBoundaryPosition >= interactiveObjects[i].getWorldY() && startBoundaryPosition <= interactiveObjects[i].getWorldY() + objectBoundaries.height) ||
                              endBoundaryPosition   >= interactiveObjects[i].getWorldY() && endBoundaryPosition   <= interactiveObjects[i].getWorldY() + objectBoundaries.height)
                        )
                        {
                            isColliding = handleObjectInteractions(entity, interactiveObjects, i, isPlayer, isPlayerAndKey);
                        }
                        break;
                
                    default:
                        break;
                }
            }
        }
        return isColliding;
    }

    private boolean handleObjectInteractions(Entity entity, InteractiveObject[] interactiveObjects, int objectIndex, boolean isPlayer, boolean isPlayerAndKey) {
        if (isPlayerAndKey) {
            gamePanel.player.addKey();
            interactiveObjects[objectIndex] = null;
        } else if (isPlayer && interactiveObjects[objectIndex].getName().toLowerCase().equals("door") && ((Player) entity).hasKey()) {
            int worldX = interactiveObjects[objectIndex].getWorldX();
            int worldY = interactiveObjects[objectIndex].getWorldY();
            interactiveObjects[objectIndex] = new OpenDoor();
            interactiveObjects[objectIndex].setWorldX(worldX);
            interactiveObjects[objectIndex].setWorldY(worldY);
            gamePanel.player.dropKey();
        }
        else {
            return true;
        }
        return false;
    }
}
