package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_OpenDoor extends SuperObject {
    public Obj_OpenDoor(String type) {
        name = "Open Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/" + type + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading door image: " + type);
        }
        collision = true;
    }
}
