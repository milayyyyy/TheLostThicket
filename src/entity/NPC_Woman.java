package entity;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_Woman extends Entity{
    public int hp = 3;
    public NPC_Woman(GamePanel gp){
        super(gp);
        speed = 1;
        direction = "down";
        getImage();

    }
    public void takeDamage(int damage) {
        hp -= damage;
        System.out.println("NPC took " + damage + " damage! Remaining HP: " + hp);

        if (hp <= 0) {
            System.out.println("NPC defeated!");
            gp.npc[0] = null;
        }
    }

    public void getImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/npc/up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/npc/up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/npc/down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/npc/down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/npc/left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/npc/left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/npc/right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/npc/right_2.png"));
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("getPlayerImage() is not working");
        }
    }

    public void setAction(){
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i<=25){
                direction="up";
            } else if(i>25 && i<=50){
                direction="down";
            } else if(i>50 && i<=75){
                direction="left";
            } else if(i>75 && i<=100){
                direction="right";
            }
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
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                    break;
                case "down":
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                    break;
                case "left":
                    if (spriteNum == 1)
                        image = left1;
                    if (spriteNum == 2)
                        image = left2;
                    break;
                case "right":
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }




}
