package interactiveObject;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Chest extends InteractiveObject {
    
    public Chest() {
        setName("chest");
        setCollision(true);
        this.loadImage();
    }

    private void loadImage() {
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/Chest.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
