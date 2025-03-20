package tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[20];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage(){
        try{
            this.tile[0] = new Tile();
            this.tile[0].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/wall.png"));
            this.tile[0].collision = true;
            this.tile[1] = new Tile();
            this.tile[1].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/grass.png"));
            this.tile[2] = new Tile();
            this.tile[2].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/trees.png"));
            this.tile[2].collision = true;
            this.tile[3] = new Tile();
            this.tile[3].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/road.png"));
            this.tile[4] = new Tile();
            this.tile[4].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/pine.png"));
            this.tile[4].collision = true;
            this.tile[5] = new Tile();
            this.tile[5].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/pineb.png"));
            this.tile[6] = new Tile();
            this.tile[6].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/mushroom.png"));
            this.tile[6].collision = true;
            this.tile[7] = new Tile();
            this.tile[7].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/tree2.png"));
            this.tile[7].collision = true;
            this.tile[8] = new Tile();
            this.tile[8].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/tree3.png"));
            this.tile[8].collision = true;
            this.tile[9] = new Tile();
            this.tile[9].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/tree4.png"));
            this.tile[9].collision = true;
            this.tile[10] = new Tile();
            this.tile[10].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/book.png"));
            this.tile[11] = new Tile();
            this.tile[11].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/fall.png"));
            this.tile[12] = new Tile();
            this.tile[12].image = ImageIO.read(this.getClass().getResourceAsStream("/tiles/waterfall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col=0;
            int row=0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();
                while(col< gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row]=num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col=0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e){

        }
    }
    public void draw(Graphics2D g2){
        int worldCol=0;
        int worldRow=0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX=worldCol*gp.tileSize;
            int worldY=worldRow*gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol=0;
                worldRow++;
            }
        }


    }
}
