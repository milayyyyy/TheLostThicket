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
        int playerCol = gp.player.worldX / gp.tileSize;
        int playerRow = gp.player.worldY / gp.tileSize;
        int tileNum = gp.tileM.mapTileNum[playerCol][playerRow];

        return tileNum == 17;
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
