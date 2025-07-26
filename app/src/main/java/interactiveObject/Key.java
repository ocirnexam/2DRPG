package interactiveObject;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Key extends InteractiveObject {

    public Key(GamePanel gamePanel) {
        super(gamePanel);
        setName("key");
        setCollision(false);
        this.loadImage();
    }

    private void loadImage() {
        setScaledImage("Key1");
    }
}
