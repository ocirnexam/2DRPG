package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
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

        interactiveObjects[1] = new OpenDoor();
        interactiveObjects[1].setWorldY(16 * gamePanel.getTileSize());
        interactiveObjects[1].setWorldX(13 * gamePanel.getTileSize());

        interactiveObjects[2] = new Door();
        interactiveObjects[2].setWorldY(39 * gamePanel.getTileSize());
        interactiveObjects[2].setWorldX(58 * gamePanel.getTileSize());
    }

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < MAX_SIZE; i++) {
            if (interactiveObjects[i] != null) {
                interactiveObjects[i].draw(graphics2D, gamePanel);
            }
        }
    }
}
