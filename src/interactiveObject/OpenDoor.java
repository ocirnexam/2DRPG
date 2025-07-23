package interactiveObject;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OpenDoor extends InteractiveObject {

    public OpenDoor() {
        setName("door");
        setCollision(false);
        this.loadImage();
    }

    private void loadImage() {
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/res/objects/OpenDoor.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
