package Main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //game screen
    //game settings
    final int originalTileSize = 16; //16x16
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize*maxWorldRow;

    //titleScreen
    int FPS = 60;
    public UI ui = new UI(this);
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public  int chosenCharacter = 0;
    public Player player = new Player(this, keyH);
    public CollisionChecker ch = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public SuperObject obj[] = new SuperObject[10];


    //game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;

    //entity
    public Entity npc[] = new Entity[10];



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // game size
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this); //passing the gamepanel to thread
        gameThread.start(); // calls run method
    }

    @Override
    public void run() { // game loop
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime-lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }

    }
    public void update(){
        if(gameState == playState){
            player.update();

            for(int i=0; i<npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        //titlescreen
        if(gameState == titleState){
            ui.draw(g2);
        } else{
            //tile
            tileM.draw(g2);
            //object
            for(int i=0;i<obj.length; i++){
                if(obj[i] != null){
                    obj[i].draw(g2, this);
                }
            }
            //npc
            for(int i=0; i<npc.length; i++){
                if(npc[i] != null){
                    npc[i].draw(g2);
                }
            }

            //player
            player.draw(g2);

            g2.dispose();
        }
    }


}
