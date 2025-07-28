package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.ScaleManager;

public class UI {
    private GamePanel gamePanel;
    private Font displayFont;

    private Graphics2D graphics2D;

    private boolean messageOn = false;
    private int messageCounter = 0;
    private String message = "";
    private boolean gameFinished = false;

    private double playDuration = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        displayFont = new Font("Arial", Font.PLAIN, 30);
        loadImages();
    }

    private void loadImages() {
        // try {

        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        
    }

    public void showMessage(String message) {
        this.message = message;
        this.messageOn = true;
    }

    public void setGameFinished() {
        this.gameFinished = true;
    }

    public void draw(Graphics2D graphics2D) {

        if(gamePanel.getGameState() == GamePanel.PLAY_STATE) {
            // play state stuff here
        }
        else if (gamePanel.getGameState() == GamePanel.PAUSE_STATE) {
            drawPauseScreen(graphics2D);
        }
    }

    private void drawPauseScreen(Graphics2D graphics2D) {
        String pauseText = "PAUSED!";
        drawStringCenter(pauseText, graphics2D, displayFont, Color.WHITE);
    }

    private void drawStringCenter(String text, Graphics2D graphics2D, Font font, Color color) {
        int textLength; 
        int x;
        int y;


        graphics2D.setFont(font);
        graphics2D.setColor(color);
        textLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();


        x = ScaleManager.getScreenWidth() / 2 - textLength / 2;
        y = ScaleManager.getScreenHeight() / 2 - (int) (ScaleManager.getTileSize() * 1.5);
        
        graphics2D.drawString(text, x, y);
    }
}
