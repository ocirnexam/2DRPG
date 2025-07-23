package interactiveObject;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Door extends InteractiveObject {

    public Door() {
        setName("door");
        setCollision(true);
        this.loadImage();
    }

    private void loadImage() {
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/res/objects/Door1.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
