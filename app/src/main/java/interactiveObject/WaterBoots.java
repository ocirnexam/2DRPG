package interactiveObject;

import main.GamePanel;

public class WaterBoots extends InteractiveObject {
    public WaterBoots(GamePanel gamePanel) {
        super(gamePanel);
        setName("waterboots");
        setCollision(true);
        this.loadImage();
    }

    private void loadImage() {
        setScaledImage("WaterBoots");
    }
}
