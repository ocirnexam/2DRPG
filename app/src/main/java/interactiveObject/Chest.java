package interactiveObject;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Chest extends InteractiveObject {
    
    public Chest(GamePanel gamePanel) {
        super(gamePanel);
        setName("chest");
        setCollision(true);
        this.loadImage();
    }

    private void loadImage() {
        setScaledImage("Chest");
    }
}
