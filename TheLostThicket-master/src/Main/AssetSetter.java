package Main;

import entity.NPC_Woman;
import object.Obj_Door;
import object.Obj_Key;
import object.Obj_OpenDoor;
import entity.Animals;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new Obj_Key();
        gp.obj[0].worldX = 23*gp.tileSize;
        gp.obj[0].worldY = 7*gp.tileSize;

        gp.obj[1] = new Obj_Key();
        gp.obj[1].worldX = 23*gp.tileSize;
        gp.obj[1].worldY = 23*gp.tileSize;

        gp.obj[2] = new Obj_Door("door1");
        gp.obj[2].worldX = 30 * gp.tileSize;
        gp.obj[2].worldY = 30 * gp.tileSize;

        gp.obj[3] = new Obj_Door("door2");
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 15 * gp.tileSize;

        gp.obj[4] = new Obj_Door("door3");
        gp.obj[4].worldX = 29 * gp.tileSize;
        gp.obj[4].worldY = 24 * gp.tileSize;

        gp.obj[5] = new Obj_Door("door3");
        gp.obj[5].worldX = 37 * gp.tileSize;
        gp.obj[5].worldY = 22 * gp.tileSize;

        gp.obj[6] = new Obj_OpenDoor("door3_open");
        gp.obj[6].worldX = 50 * gp.tileSize;
        gp.obj[6].worldY = 50 * gp.tileSize;

    }

    public void setNPC(){
        gp.npc[0] = new NPC_Woman(gp);
        gp.npc[0].worldX = gp.tileSize*25;
        gp.npc[0].worldY = gp.tileSize*25;

        gp.npc[1] = new Animals(gp, "animal1");
        gp.npc[1].worldX = gp.tileSize * 35;
        gp.npc[1].worldY = gp.tileSize * 23;

        gp.npc[2] = new Animals(gp, "animal2");
        gp.npc[2].worldX = gp.tileSize * 15;
        gp.npc[2].worldY = gp.tileSize * 15;

        gp.npc[3] = new Animals(gp, "animal3");
        gp.npc[3].worldX = gp.tileSize * 20;
        gp.npc[3].worldY = gp.tileSize * 20;
    }
}
