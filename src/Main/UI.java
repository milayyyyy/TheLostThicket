package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    public int commandNum = 0;
    public int titleScreenState = 0;


    BufferedImage characterOrion, characterElden, characterBriana;

    public UI(GamePanel gp){
        this.gp=gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    public void draw(Graphics2D g2){
        this.g2=g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        if(gp.gameState == gp.playState){
            //do playstatte
        } if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }

    }

    public void drawTitleScreen(){
        if(titleScreenState == 0){
            //titlename
            g2.setColor(new Color(0,0,0)); // for bg color
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,42F));
            String text = "LOST THICKET";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            //shadow
            g2.setColor(Color.gray);
            g2.drawString(text, x+4, y+4);
            //maincolor
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,25F));
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.tileSize, y);
            }
        } else if(titleScreenState == 1) { //select character
            selectCharacter();
        }
    }

    public void drawPauseScreen(){
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);

    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = (gp.screenWidth - length) / 2;

        return x;
    }

    public void selectCharacter(){
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42f));
            String text = "Select Character";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            try{
                characterOrion = ImageIO.read(getClass().getResourceAsStream("/player/Orion_down1.png"));
                characterElden = ImageIO.read(getClass().getResourceAsStream("/player/Elden_down1.png"));
                characterBriana = ImageIO.read(getClass().getResourceAsStream("/player/Briana_down1.png"));
            } catch(IOException e) {
                e.printStackTrace();
            }
            g2.drawImage(characterElden, 6*gp.tileSize, 6*gp.tileSize, gp.tileSize, gp.tileSize, null);
            g2.drawImage(characterBriana, 10*gp.tileSize, 6*gp.tileSize, gp.tileSize, gp.tileSize, null);
            g2.drawImage(characterOrion, 14*gp.tileSize, 6*gp.tileSize, gp.tileSize, gp.tileSize, null);

            text = "Elden";
            g2.setFont(g2.getFont().deriveFont(15f));
            g2.drawString(text, 6*gp.tileSize, 8*gp.tileSize);
            if(commandNum == 0){
                g2.drawString(">", 5*gp.tileSize, 8*gp.tileSize);
            }

            text = "Briana";
            g2.setFont(g2.getFont().deriveFont(15f));
            g2.drawString(text, 10*gp.tileSize, 8*gp.tileSize);
            if(commandNum == 1){
                g2.drawString(">", 9*gp.tileSize, 8*gp.tileSize);
            }

            text = "Orion";
            g2.setFont(g2.getFont().deriveFont(15f));
            g2.drawString(text, 14*gp.tileSize, 8*gp.tileSize);
            if(commandNum == 2){
                g2.drawString(">", 13*gp.tileSize, 8*gp.tileSize);
            }

            text = "Back";
            g2.setFont(g2.getFont().deriveFont(15f));
            g2.drawString(text, 2*gp.tileSize, 15*gp.tileSize);
            if(commandNum == 3){
                g2.drawString(">", 1*gp.tileSize, 15*gp.tileSize);
            }
        }
}

