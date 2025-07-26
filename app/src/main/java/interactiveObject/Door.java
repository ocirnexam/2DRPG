package interactiveObject;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Door extends InteractiveObject {

    public Door(GamePanel gamePanel) {
        super(gamePanel);
        setName("door");
        setCollision(true);
        this.loadImage();
    }

    private void loadImage() {
        setScaledImage("Door1");
    }
}
