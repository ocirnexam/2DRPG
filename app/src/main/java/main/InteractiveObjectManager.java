package main;

import java.awt.Graphics2D;

import interactiveObject.OpenDoor;
import interactiveObject.WaterBoots;
import interactiveObject.Chest;
import interactiveObject.Door;
import interactiveObject.InteractiveObject;
import interactiveObject.Key;

import main.ScaleManager;
import math.Vec2D;

public class InteractiveObjectManager {
    private GamePanel gamePanel;

    public static final int MAX_SIZE = 20;

    private int nextObjectIndex = 0;

    // Interactive Objects
    public InteractiveObject interactiveObjects[] = new InteractiveObject[MAX_SIZE];

    public InteractiveObjectManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public InteractiveObject[] getInteractiveObjects() {
        return interactiveObjects;
    }

    public void spawnObject(InteractiveObject object, Vec2D position) {
        interactiveObjects[nextObjectIndex] = object;
        interactiveObjects[nextObjectIndex].setWorldX(position.getX());
        interactiveObjects[nextObjectIndex].setWorldY(position.getY());
        interactiveObjects[nextObjectIndex].setBoundaries();
    }

    public void placeInteractiveObjects() {
        interactiveObjects[0] = new Key(gamePanel);
        interactiveObjects[0].setWorldX(700);
        interactiveObjects[0].setWorldY(880);
        interactiveObjects[0].setBoundaries();

        interactiveObjects[1] = new OpenDoor(gamePanel);
        interactiveObjects[1].setWorldY(16 * ScaleManager.getTileSize());
        interactiveObjects[1].setWorldX(13 * ScaleManager.getTileSize());
        interactiveObjects[1].setBoundaries();

        interactiveObjects[2] = new Door(gamePanel);
        interactiveObjects[2].setWorldY(39 * ScaleManager.getTileSize());
        interactiveObjects[2].setWorldX(58 * ScaleManager.getTileSize());
        interactiveObjects[2].setBoundaries();

        interactiveObjects[3] = new Door(gamePanel);
        interactiveObjects[3].setWorldY(55 * ScaleManager.getTileSize());
        interactiveObjects[3].setWorldX(60 * ScaleManager.getTileSize());
        interactiveObjects[3].setBoundaries();

        interactiveObjects[4] = new Door(gamePanel);
        interactiveObjects[4].setWorldY(28 * ScaleManager.getTileSize());
        interactiveObjects[4].setWorldX(59 * ScaleManager.getTileSize());
        interactiveObjects[4].setBoundaries();

        interactiveObjects[5] = new Chest(gamePanel);
        interactiveObjects[5].setWorldX(94 * ScaleManager.getTileSize());
        interactiveObjects[5].setWorldY(29 * ScaleManager.getTileSize());
        interactiveObjects[5].setBoundaries();

        interactiveObjects[6] = new Key(gamePanel);
        interactiveObjects[6].setWorldX(72 * ScaleManager.getTileSize());
        interactiveObjects[6].setWorldY(48 * ScaleManager.getTileSize());
        interactiveObjects[6].setBoundaries();

        interactiveObjects[7] = new Key(gamePanel);
        interactiveObjects[7].setWorldX(18 * ScaleManager.getTileSize());
        interactiveObjects[7].setWorldY(59 * ScaleManager.getTileSize());
        interactiveObjects[7].setBoundaries();

        interactiveObjects[8] = new Door(gamePanel);
        interactiveObjects[8].setWorldY(10 * ScaleManager.getTileSize());
        interactiveObjects[8].setWorldX(79 * ScaleManager.getTileSize());
        interactiveObjects[8].setBoundaries();

        interactiveObjects[9] = new Door(gamePanel);
        interactiveObjects[9].setWorldY(15 * ScaleManager.getTileSize());
        interactiveObjects[9].setWorldX(86 * ScaleManager.getTileSize());
        interactiveObjects[9].setBoundaries();

        interactiveObjects[10] = new Door(gamePanel);
        interactiveObjects[10].setWorldY(33 * ScaleManager.getTileSize());
        interactiveObjects[10].setWorldX(91 * ScaleManager.getTileSize());
        interactiveObjects[10].setBoundaries();

        interactiveObjects[11] = new Key(gamePanel);
        interactiveObjects[11].setWorldY(6 * ScaleManager.getTileSize());
        interactiveObjects[11].setWorldX(79 * ScaleManager.getTileSize());
        interactiveObjects[11].setBoundaries();

        interactiveObjects[12] = new Key(gamePanel);
        interactiveObjects[12].setWorldY(6 * ScaleManager.getTileSize());
        interactiveObjects[12].setWorldX(80 * ScaleManager.getTileSize());
        interactiveObjects[12].setBoundaries();

        nextObjectIndex = 13;
    }

    public void removeObject(int index) {
        if (index < 0 || index > MAX_SIZE) {
            return;
        }
        interactiveObjects[index] = null;
    }

    public void handleInteraction(int index) {
        if (index < 0) {
            return;
        }
        InteractiveObject interactiveObject = this.getInteractiveObjects()[index];
        String objectName = interactiveObject.getName();

        switch (objectName) {
            case "key":
                gamePanel.playSoundEffect(SoundManager.PICKUP_KEY_SOUND);
                gamePanel.getUIManager().showMessage("You found a key!");
                this.removeObject(index);
                break;
            case "door":
                gamePanel.playSoundEffect(SoundManager.OPEN_DOOR_SOUND);
                int worldX = interactiveObject.getWorldX();
                int worldY = interactiveObject.getWorldY();
                this.interactiveObjects[index] = new OpenDoor(gamePanel);
                this.interactiveObjects[index].setWorldX(worldX);
                this.interactiveObjects[index].setWorldY(worldY);
                break;
            case "chest":
                if (index == 5) {
                        spawnObject(new WaterBoots(this.gamePanel), new Vec2D(interactiveObject.getWorldX() + ScaleManager.getTileSize(), interactiveObject.getWorldY() + ScaleManager.getTileSize()));
                }
                break;
            case "waterboots":
                removeObject(index);
                break;
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < MAX_SIZE; i++) {
            if (interactiveObjects[i] != null) {
                interactiveObjects[i].draw(graphics2D, gamePanel.player);
            }
        }
    }
}
