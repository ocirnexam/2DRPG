package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 P Tile
    final int scale = 4; // scale to higher resolution screens

    final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    Thread gameThread;
    KeyboardHandler keyboardHandler = new KeyboardHandler();

    // Entities
    public final Player player = new Player(this, keyboardHandler);

    TileManager tileManager = new TileManager(this);
    CollisionDetector collisionDetector = new CollisionDetector(this);

    // World Settings
    public final int maxWorldCol = 105;
    public final int maxWorldRow = 66;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    private int FPS = 60;
    private int secondInNanoSeconds = 1000000000;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyboardHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public CollisionDetector getCollisionDetector() {
        return collisionDetector;
    }

    @Override
    public void run() {
        // GAME LOOP
        double drawInterval = secondInNanoSeconds/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta > 1) {
                // update information (character pos, ...)
                this.update();
                // draw screen with updated information
                this.repaint(); 
                delta--;
                drawCount++;
            }
            if (timer >= secondInNanoSeconds) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    private void update() {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        tileManager.draw(graphics2D);
        player.draw(graphics2D);
    }

    public int getTileSize() {
        return tileSize;
    }
}
