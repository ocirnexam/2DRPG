package interactiveObject;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Key extends InteractiveObject {

    public Key() {
        setName("key");
        setCollision(false);
        this.loadImage();
    }

    private void loadImage() {
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/Key1.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
