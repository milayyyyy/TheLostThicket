package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Door extends SuperObject {
    public Obj_Door(String type) {
        name = "door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/" + type + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading door image: " + type);
        }
    }
}
