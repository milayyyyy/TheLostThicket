package entity;

import Main.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class Animals extends Entity {
    private String type;

    public Animals(GamePanel gp, String type) {
        super(gp);
        this.type = type;
        speed = 1;
        direction = "down";
        getImage();
    }

    public void getImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/animals/" + type + "_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/animals/" + type + "_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/animals/" + type + "_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/animals/" + type + "_down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/animals/" + type + "_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/animals/" + type + "_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/animals/" + type + "_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/animals/" + type + "_right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading images for " + type);
        }
    }

    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) direction = "up";
            else if (i <= 50) direction = "down";
            else if (i <= 75) direction = "left";
            else direction = "right";
            actionLockCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        BufferedImage image = null;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            switch (direction) {
                case "up":
                    image = (spriteNum == 1) ? up1 : up2;
                    break;
                case "down":
                    image = (spriteNum == 1) ? down1 : down2;
                    break;
                case "left":
                    image = (spriteNum == 1) ? left1 : left2;
                    break;
                case "right":
                    image = (spriteNum == 1) ? right1 : right2;
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
