package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UI {
    private GamePanel gamePanel;
    private Font displayFont;
    private Font finishFont;
    private BufferedImage keyImage;
    private boolean messageOn = false;
    private int messageCounter = 0;
    private String message = "";
    private boolean gameFinished = false;

    private double playDuration = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        displayFont = new Font("Arial", Font.PLAIN, 30);
        finishFont = new Font("Arial", Font.BOLD, 60);
        loadImages();
    }

    private void loadImages() {
        try {
            keyImage = ImageIO.read(getClass().getResourceAsStream("/res/objects/Key1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void showMessage(String message) {
        this.message = message;
        this.messageOn = true;
    }

    public void setGameFinished() {
        this.gameFinished = true;
    }

    public void draw(Graphics2D graphics2D) {
        if (this.gameFinished) {
            String congratsText = "Congratulations!";
            int textLength;
            int x;
            int y;

            graphics2D.setColor(Color.YELLOW);
            graphics2D.setFont(finishFont);
            textLength = (int)graphics2D.getFontMetrics().getStringBounds(congratsText, graphics2D).getWidth();


            x = gamePanel.screenWidth / 2 - textLength / 2;
            y = gamePanel.screenHeight / 2 - (int) (gamePanel.getTileSize() * 1.5);
            
            graphics2D.drawString(congratsText, x, y);

            gamePanel.gameThread = null;


        }
        else {
            graphics2D.setColor(Color.BLACK);
            graphics2D.fillRoundRect(gamePanel.getTileSize()/2 - 20, gamePanel.getTileSize()/2 - 20, 130, gamePanel.getTileSize(), 20, 20);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawRoundRect(gamePanel.getTileSize()/2 - 20, gamePanel.getTileSize()/2 - 20, 130, gamePanel.getTileSize(), 20, 20);
            graphics2D.drawImage(keyImage, gamePanel.getTileSize()/2, gamePanel.getTileSize()/2, gamePanel.getTileSize()/2, gamePanel.getTileSize()/2, null);

            // TIME Calculation and display
            playDuration += (double) 1/60;
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString("Time: " + this.getTimeString(), gamePanel.getTileSize() / 2 + 1, (int)(gamePanel.getTileSize() * 1.5) + 1);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("Time: " + this.getTimeString(), gamePanel.getTileSize() / 2, (int)(gamePanel.getTileSize() * 1.5));

            graphics2D.setFont(displayFont);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("x " + gamePanel.player.getKeys(), 70, 55);

            if(this.messageOn == true) {
                graphics2D.drawString(this.message, gamePanel.getTileSize() * 2, gamePanel.getTileSize() * 6);
                this.messageCounter++;
                if (this.messageCounter > 120) {
                    this.messageCounter = 0;
                    this.messageOn = false;
                }
            }
        }
    }
    
    private String getTimeString() {
        int minutes = (int)(playDuration / 60);
        int seconds = (int)(playDuration % 60);

        String minutesText;
        String secondsText;

        if (seconds < 10) {
            secondsText = "0" + seconds;
        }
        else {
            secondsText = Integer.toString(seconds);
        }

        if (minutes < 10) {
            minutesText = "0" + minutes;
        }
        else {
            minutesText = Integer.toString(minutes);
        }
        return minutesText + ":" + secondsText + "min";
    }
}
