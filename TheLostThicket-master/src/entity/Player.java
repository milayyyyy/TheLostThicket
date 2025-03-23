package entity;

import Main.GamePanel;
import Main.KeyHandler;

import Main.EldenSkills;
import java.util.ArrayList;
import java.util.List;

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

    private boolean trapActivated = false;
    private int fallDelayCounter = 0;

    public int hp;
    public int mana;
    public List<EldenSkills> skills = new ArrayList<>();
    public boolean isStealth = false;
    public float alpha = 1.0f;

    int hasKey = 0;

    private long lastDamageTime = 0; // Timestamp of the last damage taken
    private final long damageCooldown = 1000;


    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle(8, 16, 30, 32);
        solidAreaDefaultX = 8;
        solidAreaDefaultY = 16;
        setDefaultValues();
        getPlayerImage();

        skills.add(new EldenSkills(1)); // Healing Bloom
        skills.add(new EldenSkills(2)); // Silent Steps
        skills.add(new EldenSkills(3)); // Puzzle Sense
    }

    public void useSkill(int index) {
        if (index >= 0 && index < skills.size()) {
            skills.get(index).use(this); // Use the skill
        } else {
            System.out.println("Invalid skill selection.");
        }
    }

    public void hpAndMana(){
        switch(chosenCharacter){
            case 0: hp = 100; mana = 50; break;
            case 1: hp = 100; mana = 60; break;
            case 2: hp = 120; mana = 50; break;
        }
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
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

                fall = ImageIO.read(getClass().getResourceAsStream("/player/Elden_falling1.png"));

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
        if (isFalling) {
            fallAnimation();
            return;
        }
        if (gp.eHandler.checkPitTile()) {
            trapActivated = true;
        }
        if (trapActivated) {
            fallDelayCounter++;
            if (fallDelayCounter > 1) {
                startFalling();
                trapActivated = false;
                fallDelayCounter = 0;
            }
            return;
        }

        collisionOn = false;

        if (keyH.downPressed || keyH.leftPressed || keyH.upPressed || keyH.rightPressed) {
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            gp.ch.checkTile(this);
            int objIndex = gp.ch.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.ch.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            int animalIndex = gp.ch.checkEntity(this, gp.animals); // Check for animal collisions
            interactAnimals(animalIndex); // Call interactAnimals

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

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;
            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    System.out.println("Key: " + hasKey);
                    break;
                case "Door":
                    if (gp.obj[i].collision == false || gp.obj[i].image == gp.obj[6].image) {
                        // Door is already open, do nothing
                        break;
                    }

                    if (hasKey > 0) {
                        // Change the door's image to the open door
                        gp.obj[i].image = gp.obj[6].image;

                        // Disable collision for the open door
                        gp.obj[i].collision = false;

                        hasKey--;
                        System.out.println("Door opens.");
                        System.out.println("Key: " + hasKey);
                    } else {
                        // Only print the message if the door is closed
                        System.out.println("You need a key to open the door.");
                    }
                    break;
            }
        }
    }

    public void setTrapActivated() {
        trapActivated = true;
        fallDelayCounter = 0;
    }

    public void startFalling() {
        isFalling = true;
        fallCounter = 0;

        // 🛠 Snap Player to Pit Center
        int tileCenterX = ((worldX / gp.tileSize) * gp.tileSize) + (gp.tileSize / 2) - (solidArea.width / 2);
        int tileCenterY = ((worldY / gp.tileSize) * gp.tileSize) + (gp.tileSize / 2) - (solidArea.height / 2);

        worldX = tileCenterX;
        worldY = tileCenterY;

        // ✅ Ensure currentDialogue is NOT null before setting it
        if (gp.ui.currentDialogue == null) {
            gp.ui.currentDialogue = "You fell!";
        } else {
            gp.ui.currentDialogue = "You fell!";
        }

        // 🛠 Show Falling Message
        gp.gameState = gp.dialogueState;
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

            // Set opacity for falling effect (if needed)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.drawImage(image, screenX, screenY + fallOffset, gp.tileSize, gp.tileSize, null);

            // Reset opacity after drawing
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
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
            // Set opacity based on the alpha value
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            // Draw the player
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            // Reset opacity to fully opaque after drawing
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
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

    public void interactNPC(int i){
        if(i != 999){
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        }
    }

    public void interactAnimals(int i) {
        if (i != 999) {
            // Check if the player is colliding with the animal
            if (isCollidingWithAnimal(gp.animals[i])) {
                takeDamage(1); // Player takes 1 damage
            }
        }
    }

    private boolean isCollidingWithAnimal(Entity animal) {
        // Calculate the distance between the player and the animal
        int dx = animal.worldX - worldX;
        int dy = animal.worldY - worldY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Check if the distance is less than the sum of their collision radii
        int collisionDistance = gp.tileSize / 2; // Adjust this value as needed
        return distance < collisionDistance;
    }

    public void takeDamage(int damage) {
        long currentTime = System.currentTimeMillis();

        // Apply damage only if the cooldown has passed
        if (currentTime - lastDamageTime >= damageCooldown) {
            hp -= damage; // Reduce player's health
            lastDamageTime = currentTime; // Update the last damage time
            System.out.println("Player took " + damage + " damage! HP: " + hp);
        }
    }

}

