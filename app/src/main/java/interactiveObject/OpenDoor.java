package interactiveObject;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OpenDoor extends InteractiveObject {

    public OpenDoor(GamePanel gamePanel) {
        super(gamePanel);
        setName("open_door");
        setCollision(false);
        this.loadImage();
    }

    private void loadImage() {
        setScaledImage("OpenDoor");
    }
}
