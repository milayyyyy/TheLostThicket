package entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    private boolean isAttacking = false;
    private int attackFrame = 0;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    private int chosenCharacter;
    private boolean attacking = false;
    private int attackCooldown = 0;

    public boolean isFalling = false; // Indicates if player is falling
    public int fallCounter = 0; // Timer for falling animation

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle(8, 16, 30, 32);
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 103;
        worldY = gp.tileSize * 101;
        speed = 4;
        direction = "right";
    }

    public void getPlayerImage() {
        try {
            if (gp.chosenCharacter == 0) { // Elden
                up1 = ImageIO.read(getClass().getResourceAsStream("/player/Elden_up1.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/player/Elden_up2.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/player/Elden_down1.png"));
                down2 = ImageIO.read(getClass().getResourceAsStream("/player/Elden_down2.png"));
                left1 = ImageIO.read(getClass().getResourceAsStream("/player/Elden_left1.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/player/Elden_left2.png"));
                right1 = ImageIO.read(getClass().getResourceAsStream("/player/Elden_right1.png"));
                right2 = ImageIO.read(getClass().getResourceAsStream("/player/Elden_right2.png"));

                attackLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/Elden_attack_left1.png"));
                attackLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/Elden_attack_left2.png"));
                attackRight1 = ImageIO.read(getClass().getResourceAsStream("/player/Elden_attack_right1.png"));
                attackRight2 = ImageIO.read(getClass().getResourceAsStream("/player/Elden_attack_right2.png"));

            } else if (gp.chosenCharacter == 1) { // Briana
                up1 = ImageIO.read(getClass().getResourceAsStream("/player/Briana_up1.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/player/Briana_up2.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/player/Briana_down1.png"));
                down2 = ImageIO.read(getClass().getResourceAsStream("/player/Briana_down2.png"));
                left1 = ImageIO.read(getClass().getResourceAsStream("/player/Briana_left1.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/player/Briana_left2.png"));
                right1 = ImageIO.read(getClass().getResourceAsStream("/player/Briana_right1.png"));
                right2 = ImageIO.read(getClass().getResourceAsStream("/player/Briana_right2.png"));
            } else if (gp.chosenCharacter == 2) { // Orion
                up1 = ImageIO.read(getClass().getResourceAsStream("/player/Orion_up1.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/player/Orion_up2.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/player/Orion_down1.png"));
                down2 = ImageIO.read(getClass().getResourceAsStream("/player/Orion_down2.png"));
                left1 = ImageIO.read(getClass().getResourceAsStream("/player/Orion_left1.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/player/Orion_left2.png"));
                right1 = ImageIO.read(getClass().getResourceAsStream("/player/Orion_right1.png"));
                right2 = ImageIO.read(getClass().getResourceAsStream("/player/Orion_right2.png"));

                attackLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/Orion_attack_left1.png"));
                attackLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/Orion_attack_left2.png"));
                attackRight1 = ImageIO.read(getClass().getResourceAsStream("/player/Orion_attack_right1.png"));
                attackRight2 = ImageIO.read(getClass().getResourceAsStream("/player/Orion_attack_right2.png"));

                fall = ImageIO.read(getClass().getResourceAsStream("/player/Orion_falling1.png"));

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("getPlayerImage() is not working");
        }
    }

    public void update() {
        if (gp.chosenCharacter != this.chosenCharacter) {
            this.chosenCharacter = gp.chosenCharacter;
            getPlayerImage();
        }

        if (attackCooldown > 0) {
            attackCooldown--;
        }

        // 🛠️ Handle Falling Animation (Stops Movement)
        if (isFalling) {
            fallAnimation();
            return; // Prevent movement updates while falling
        }

        // 🛠️ Check if Player Should Fall
        if (gp.eHandler.checkPitTile()) {
            startFalling();
            return;
        }

        collisionOn = false;

        if (keyH.downPressed || keyH.leftPressed || keyH.upPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            gp.ch.checkTile(this);

            int npcIndex = gp.ch.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }


    public void startFalling() {
        isFalling = true;
        fallCounter = 0;

        // 🛠 Move player inside the pit (center of tile)
        worldX = (worldX / gp.tileSize) * gp.tileSize; // Align X to grid
        worldY = (worldY / gp.tileSize) * gp.tileSize; // Align Y to grid

        // Show dialogue before falling
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You fell!";
    }


    public void fallAnimation() {
        if (fallCounter < 30) {
            worldY += 2; // Move player down gradually
            fallCounter++;
        } else {
            respawnPlayer(); // After animation, reset player
        }
    }


    // 🛠️ Respawn After Falling
    public void respawnPlayer() {
        isFalling = false;
        worldX = gp.tileSize * 23; // Reset position
        worldY = gp.tileSize * 21;
        gp.gameState = gp.playState; // Resume game state
    }




    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // 🛠 Falling Effect: Make the player appear inside the pit
        if (isFalling) {
            image = fall; // ✅ Use the falling sprite
            int fallOffset = (fallCounter * gp.tileSize) / 30; // Moves player inside the pit gradually
            g2.drawImage(image, screenX, screenY + fallOffset, gp.tileSize, gp.tileSize, null);
            return; // Skip drawing other sprites
        }

        // 🛠 Attacking Animation
        else if (isAttacking && (gp.chosenCharacter == 0 || gp.chosenCharacter == 2)) {
            if (direction.equals("left")) {
                image = (attackFrame % 2 == 0) ? attackLeft1 : attackLeft2;
            } else if (direction.equals("right")) {
                image = (attackFrame % 2 == 0) ? attackRight1 : attackRight2;
            }
        }

        // 🛠 Normal Movement Sprites
        else {
            switch (direction) {
                case "up": image = (spriteNum == 1) ? up1 : up2; break;
                case "down": image = (spriteNum == 1) ? down1 : down2; break;
                case "left": image = (spriteNum == 1) ? left1 : left2; break;
                case "right": image = (spriteNum == 1) ? right1 : right2; break;
            }
        }

        if (image == null) {
            System.out.println("Error: No image found for player!");
        } else {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }





    public void attack(String attackDirection) {
        for (int i = 0; i < gp.npc.length; i++) {
            if (gp.npc[i] != null && isNpcInRange(gp.npc[i], attackDirection)) {
                gp.npc[i].takeDamage(1);
            }
        }
    }



    public boolean isNpcInRange(Entity npc, String attackDirection) {
        int attackRange = gp.tileSize / 2;

        int npcLeft = npc.worldX;
        int npcRight = npc.worldX + gp.tileSize;
        int npcTop = npc.worldY;
        int npcBottom = npc.worldY + gp.tileSize;

        int playerLeft = worldX;
        int playerRight = worldX + gp.tileSize;
        int playerTop = worldY;
        int playerBottom = worldY + gp.tileSize;

        boolean hit = false;

        if (attackDirection.equals("left")) {
            hit = (playerLeft - attackRange < npcRight && playerLeft > npcLeft);
        } else if (attackDirection.equals("right")) {
            hit = (playerRight + attackRange > npcLeft && playerRight < npcRight);
        }



        return hit;
    }

    public void pickUpObject(int i){
        if(i != 999){

        }
    }

    public void interactNPC(int i){
        if(i != 999){
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        }
    }

}

