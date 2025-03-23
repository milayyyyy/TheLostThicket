package Main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;
    boolean canTouchEvent = true;
    int previousEventX, previousEventY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new Rectangle(23, 23, 2, 2);
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent() {
        if (checkPitTile()) {
            damagePit();
        }
    }
    public boolean checkPitTile() {
        int leftCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
        int rightCol = (gp.player.worldX + gp.player.solidArea.x + gp.player.solidArea.width) / gp.tileSize;
        int topRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
        int bottomRow = (gp.player.worldY + gp.player.solidArea.y + gp.player.solidArea.height) / gp.tileSize;

        int tileTopLeft = gp.tileM.mapTileNum[leftCol][topRow];
        int tileTopRight = gp.tileM.mapTileNum[rightCol][topRow];
        int tileBottomLeft = gp.tileM.mapTileNum[leftCol][bottomRow];
        int tileBottomRight = gp.tileM.mapTileNum[rightCol][bottomRow];

        // 🛠️ If any part of the player steps onto tile 18 (normal), turn it into a trap instantly
        if (tileTopLeft == 18 || tileTopRight == 18 || tileBottomLeft == 18 || tileBottomRight == 18) {
            gp.tileM.mapTileNum[leftCol][topRow] = 23;
            gp.tileM.mapTileNum[rightCol][topRow] = 23;
            gp.tileM.mapTileNum[leftCol][bottomRow] = 23;
            gp.tileM.mapTileNum[rightCol][bottomRow] = 23;
            gp.player.setTrapActivated();
            return false; // Let them move into the pit first
        }

        // 🛠️ If already a trap (17), trigger fall
        return (tileTopLeft == 23 && tileTopRight == 23 && tileBottomLeft == 23 && tileBottomRight == 23);
    }




    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;

        eventRect.x = col * gp.tileSize + eventRectDefaultX;
        eventRect.y = row * gp.tileSize + eventRectDefaultY;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        if (gp.player.solidArea.intersects(eventRect) && canTouchEvent) {
            if (reqDirection.equals("any") || gp.player.direction.equals(reqDirection)) {
                hit = true;
                canTouchEvent = false;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

    public void damagePit() {
        if (canTouchEvent) {
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You fell into a pit!";
            knockBackPlayer();
            canTouchEvent = false;
        }
    }

    public void knockBackPlayer() {
        switch (gp.player.direction) {
            case "up": gp.player.worldY += gp.player.speed * 2; break;
            case "down": gp.player.worldY -= gp.player.speed * 2; break;
            case "left": gp.player.worldX += gp.player.speed * 2; break;
            case "right": gp.player.worldX -= gp.player.speed * 2; break;
        }
    }
}
