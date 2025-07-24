package main;

import java.awt.Graphics2D;

import interactiveObject.OpenDoor;
import interactiveObject.Door;
import interactiveObject.InteractiveObject;
import interactiveObject.Key;

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
        interactiveObjects[0] = new Key();
        interactiveObjects[0].setWorldX(700);
        interactiveObjects[0].setWorldY(880);
        interactiveObjects[0].setBoundaries();

        interactiveObjects[1] = new OpenDoor();
        interactiveObjects[1].setWorldY(16 * gamePanel.getTileSize());
        interactiveObjects[1].setWorldX(13 * gamePanel.getTileSize());
        interactiveObjects[1].setBoundaries();

        interactiveObjects[2] = new Door();
        interactiveObjects[2].setWorldY(39 * gamePanel.getTileSize());
        interactiveObjects[2].setWorldX(58 * gamePanel.getTileSize());
        interactiveObjects[2].setBoundaries();

        interactiveObjects[3] = new Door();
        interactiveObjects[3].setWorldY(55 * gamePanel.getTileSize());
        interactiveObjects[3].setWorldX(60 * gamePanel.getTileSize());
        interactiveObjects[3].setBoundaries();

        interactiveObjects[4] = new Key();
        interactiveObjects[4].setWorldX(62 * gamePanel.getTileSize());
        interactiveObjects[4].setWorldY(25 * gamePanel.getTileSize());
        interactiveObjects[4].setBoundaries();
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
                this.removeObject(index);
                break;
            case "door":
                int worldX = interactiveObject.getWorldX();
                int worldY = interactiveObject.getWorldY();
                this.interactiveObjects[index] = new OpenDoor();
                this.interactiveObjects[index].setWorldX(worldX);
                this.interactiveObjects[index].setWorldY(worldY);
                break;
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < MAX_SIZE; i++) {
            if (interactiveObjects[i] != null) {
                interactiveObjects[i].draw(graphics2D, gamePanel);
            }
        }
    }
}
