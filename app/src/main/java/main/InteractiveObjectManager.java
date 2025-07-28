package main;

import java.awt.Graphics2D;

import interactiveObject.OpenDoor;
import interactiveObject.Chest;
import interactiveObject.Door;
import interactiveObject.InteractiveObject;
import interactiveObject.Key;

import main.ScaleManager;

public class InteractiveObjectManager {
    private GamePanel gamePanel;

    public static final int MAX_SIZE = 10;

    // Interactive Objects
    public InteractiveObject interactiveObjects[] = new InteractiveObject[MAX_SIZE];

    public InteractiveObjectManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public InteractiveObject[] getInteractiveObjects() {
        return interactiveObjects;
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
        interactiveObjects[5].setWorldX(60 * ScaleManager.getTileSize());
        interactiveObjects[5].setWorldY(24 * ScaleManager.getTileSize());
        interactiveObjects[5].setBoundaries();

        interactiveObjects[6] = new Key(gamePanel);
        interactiveObjects[6].setWorldX(72 * ScaleManager.getTileSize());
        interactiveObjects[6].setWorldY(48 * ScaleManager.getTileSize());
        interactiveObjects[6].setBoundaries();

        interactiveObjects[7] = new Key(gamePanel);
        interactiveObjects[7].setWorldX(18 * ScaleManager.getTileSize());
        interactiveObjects[7].setWorldY(59 * ScaleManager.getTileSize());
        interactiveObjects[7].setBoundaries();
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
