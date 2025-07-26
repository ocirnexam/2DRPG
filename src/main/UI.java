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
    private BufferedImage keyImage;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        displayFont = new Font("Arial", Font.PLAIN, 30);
        loadImages();
    }

    private void loadImages() {
        try {
            keyImage = ImageIO.read(getClass().getResourceAsStream("/res/objects/Key1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRoundRect(gamePanel.getTileSize()/2 - 20, gamePanel.getTileSize()/2 - 20, 130, gamePanel.getTileSize(), 20, 20);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawRoundRect(gamePanel.getTileSize()/2 - 20, gamePanel.getTileSize()/2 - 20, 130, gamePanel.getTileSize(), 20, 20);
        graphics2D.drawImage(keyImage, gamePanel.getTileSize()/2, gamePanel.getTileSize()/2, gamePanel.getTileSize()/2, gamePanel.getTileSize()/2, null);

        graphics2D.setFont(displayFont);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("x " + gamePanel.player.getKeys(), 70, 55);

    }
    
}
