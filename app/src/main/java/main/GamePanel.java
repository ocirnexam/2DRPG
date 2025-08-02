package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // System
    Thread gameThread;
    KeyboardManager keyboardManager = new KeyboardManager();
    private TileManager tileManager = new TileManager(this);
    private CollisionManager collisionManager = new CollisionManager(this);
    private InteractiveObjectManager interactiveObjectManager = new InteractiveObjectManager(this);
    private NPCManager npcManager = new NPCManager(this);
    private SoundManager soundManager = new SoundManager();
    private SoundManager themeSoundManager = new SoundManager();
    private UI uiManager = new UI(this);

    // Entities
    public final Player player = new Player(this, keyboardManager);

    // Game state
    private int gameState = 0;
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;

    private int FPS = 60;
    private int secondInNanoSeconds = 1000000000;

    public GamePanel() {
        this.setPreferredSize(new Dimension(ScaleManager.getScreenWidth(), ScaleManager.getScreenHeight()));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyboardManager);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void gameSetup() {
        interactiveObjectManager.placeInteractiveObjects();
        npcManager.createNPCs();
        playMusic();
        gameState = PLAY_STATE;
    }

    private void playMusic() {
        themeSoundManager.selectSound(SoundManager.THEME_MUSIC);
        themeSoundManager.setVolume(0.5f);
        themeSoundManager.play();
        themeSoundManager.loop();
    }

    @SuppressWarnings("unused")
    private void stopMusic() {
        themeSoundManager.stop();
    }

    private void pauseMusic() {
        themeSoundManager.pause();
    }

    private void resumeMusic() {
        themeSoundManager.resume();
    }

    public void playSoundEffect(int soundEffect) {
        soundManager.selectSound(soundEffect);
        soundManager.setVolume(0.3f);
        soundManager.play();
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    public InteractiveObjectManager getInteractiveObjectManager() {
        return interactiveObjectManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public UI getUIManager() {
        return uiManager;
    }

    public int getGameState() {
        return gameState;
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
                System.out.println("FPS:" + drawCount + ", " + player);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    private void update() {
        if (gameState == PLAY_STATE) {
            if (keyboardManager.pausePressed) {
                gameState = PAUSE_STATE;
                pauseMusic();
            }
            player.update();
            npcManager.update();
        } else if (gameState == PAUSE_STATE) {
            if (!keyboardManager.pausePressed) {
                gameState = PLAY_STATE;
                resumeMusic();
            }
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        tileManager.draw(graphics2D); 
        interactiveObjectManager.draw(graphics2D);
        npcManager.draw(graphics2D);
        player.draw(graphics2D);
        uiManager.draw(graphics2D);
    }
}
