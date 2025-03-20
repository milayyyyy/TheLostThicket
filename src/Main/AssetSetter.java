package Main;

import entity.NPC_Woman;
import object.Obj_Key;

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
    }

    public void setNPC(){
        gp.npc[0] = new NPC_Woman(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
}
