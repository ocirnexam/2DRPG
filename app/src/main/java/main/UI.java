package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.ScaleManager;
import math.Vec2D;

public class UI {
    private GamePanel gamePanel;
    private Font displayFont;

    Color BlackTransparent50 = new Color(0, 0, 0, 127);

    BufferedImage keyImage;
    BufferedImage titleScreenImage;

    private boolean messageOn = false;
    private int messageCounter = 0;
    private String message = "";
    private boolean setPossibleInteratcionWithNPC;
    private String currentDialogue;

    // Default value for centering text
    public static final int DEFAULT = -1;

    private int commandNumber = 0;

    public static final int NEW_GAME_COMMAND = 0;
    public static final int LOAD_GAME_COMMAND = 1;
    public static final int QUIT_COMMAND = 2;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadFont();
        loadImages();
    }

    private void loadFont() {
        InputStream inputStream = getClass().getResourceAsStream("/font/fs-pixel-sans.ttf");
        try {
            displayFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            displayFont = displayFont.deriveFont(55f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImages() {
        try {
            keyImage = ImageIO.read(getClass().getResourceAsStream("/objects/Key1.png"));
            titleScreenImage = ImageIO.read(getClass().getResourceAsStream("/titlescreen/titlescreen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void setPossibleInteratcionWithNPC(boolean flag) {
        setPossibleInteratcionWithNPC = flag;
    }
    public void setCurrentDialogue(String dialogue) {
        this.currentDialogue = dialogue;
    }

    public void showMessage(String message) {
        this.message = message;
        this.messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {
        if(gamePanel.getGameState() == GamePanel.PLAY_STATE) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.fillRoundRect(ScaleManager.getTileSize()/2 - 20, ScaleManager.getTileSize()/2 - 20, 130, ScaleManager.getTileSize(), 20, 20);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawRoundRect(ScaleManager.getTileSize()/2 - 20, ScaleManager.getTileSize()/2 - 20, 130, ScaleManager.getTileSize(), 20, 20);
            graphics2D.drawImage(keyImage, ScaleManager.getTileSize()/2, ScaleManager.getTileSize()/2, ScaleManager.getTileSize()/2, ScaleManager.getTileSize()/2, null);

            graphics2D.setFont(displayFont);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("x " + gamePanel.player.getKeys(), 70, 55);

            if(this.messageOn == true) {
                graphics2D.drawString(this.message, ScaleManager.getTileSize() * 2, ScaleManager.getTileSize() * 6);
                this.messageCounter++;
                if (this.messageCounter > 120) {
                    this.messageCounter = 0;
                    this.messageOn = false;
                }
            }

            if (setPossibleInteratcionWithNPC == true) {
                graphics2D.setColor(Color.BLACK);
                graphics2D.fillRoundRect(ScaleManager.getTileSize()* 2 - 20, ScaleManager.getTileSize() * 2 - 20, 300, ScaleManager.getTileSize(), 40, 40);
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawRoundRect(ScaleManager.getTileSize()* 2 - 20, ScaleManager.getTileSize() * 2 - 20, 300, ScaleManager.getTileSize(), 40, 40);
                graphics2D.drawString("Interact with E", ScaleManager.getTileSize()* 2, ScaleManager.getTileSize() * 2 + 20);
            }
        }
        else if (gamePanel.getGameState() == GamePanel.PAUSE_STATE) {
            drawPauseScreen(graphics2D);
        }
        else if (gamePanel.getGameState() == GamePanel.DIALOG_STATE) {
            drawDialogScreen(graphics2D);
        }
        else if (gamePanel.getGameState() == GamePanel.TITLE_STATE) {
            drawTitleScreen(graphics2D);
        }
    }

    public void increaseCommandNumber() {
        if (commandNumber < 2) {
            commandNumber++;
        } else {
            commandNumber = 0;
        }
    }
    public void decreaseCommandNumber() {
        if (commandNumber > 0) {
            commandNumber--;
        } else {
            commandNumber = 2;
        }
    }

    public int getCommandNumber() {
        return commandNumber;
    }

    private void drawTitleScreen(Graphics2D graphics2D) {
        String titleText = "MOUNTAIN VALLEY";
        String newGameText = "New Game";
        String loadGameText = "Load Game";
        String quitText = "Quit";
        int x;
        int y;

        int shadowOffset = (int) (ScaleManager.getTileSize() * 0.05);

        graphics2D.drawImage(titleScreenImage, 0, 0, ScaleManager.getScreenWidth(), ScaleManager.getScreenHeight(), null);
        graphics2D.setFont(displayFont);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 100));
        x = getXForCenteredText(titleText, graphics2D, displayFont.deriveFont(Font.BOLD, 100));
        y = ScaleManager.getScreenHeight() / 2 - (int) (ScaleManager.getTileSize() * 1.5);
        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.drawString(titleText, x + shadowOffset, y + shadowOffset);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(titleText, x, y);

        // Commands
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 50));

        x = getXForCenteredText(newGameText, graphics2D, displayFont.deriveFont(Font.PLAIN, 50));
        y = ScaleManager.getScreenHeight() / 2 + (int) (ScaleManager.getTileSize());
        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.drawString(newGameText, x + shadowOffset, y + shadowOffset);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(newGameText, x, y);
        if (commandNumber == NEW_GAME_COMMAND) {
            graphics2D.drawString(">>", x - ScaleManager.getTileSize(), y);
            graphics2D.drawString("<<", ScaleManager.getScreenWidth() - x + ScaleManager.getTileSize(), y);
        }

        x = getXForCenteredText(loadGameText, graphics2D, displayFont.deriveFont(Font.PLAIN, 50));
        y += (int) (ScaleManager.getTileSize());
        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.drawString(loadGameText, x + shadowOffset, y + shadowOffset);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(loadGameText, x, y);
        if (commandNumber == LOAD_GAME_COMMAND) {
            graphics2D.drawString(">>", x - ScaleManager.getTileSize(), y);
            graphics2D.drawString("<<", ScaleManager.getScreenWidth() - x + ScaleManager.getTileSize(), y);
        }

        x = getXForCenteredText(quitText, graphics2D, displayFont.deriveFont(Font.PLAIN, 50));
        y += (int) (ScaleManager.getTileSize());
        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.drawString(quitText, x + shadowOffset, y + shadowOffset);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(quitText, x, y);
        if (commandNumber == QUIT_COMMAND) {
            graphics2D.drawString(">>", x - ScaleManager.getTileSize(), y);
            graphics2D.drawString("<<", ScaleManager.getScreenWidth() - x + ScaleManager.getTileSize(), y);
        }
    }

    private void drawDialogScreen(Graphics2D graphics2D) {
        if (currentDialogue == null) {
            gamePanel.setGameState(GamePanel.PLAY_STATE);
            return;
        }
        Rectangle windowRectangle = new Rectangle(ScaleManager.getTileSize() * 2, 
                                                  ScaleManager.getTileSize() / 2, 
                                                  ScaleManager.getScreenWidth() - (ScaleManager.getTileSize() * 4), 
                                                  ScaleManager.getTileSize() * 4);
        
        graphics2D.setColor(new Color(0, 0, 0, 200));
        graphics2D.fillRoundRect(windowRectangle.x, windowRectangle.y, windowRectangle.width, windowRectangle.height, 35, 35);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawRoundRect(windowRectangle.x + 10, windowRectangle.y + 10, windowRectangle.width - 20, windowRectangle.height - 20, 35, 35);
    
        Vec2D textCoordinates = new Vec2D(windowRectangle.x + ScaleManager.getTileSize(), windowRectangle.y + ScaleManager.getTileSize());
        graphics2D.setFont(displayFont);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 40));
        for (String line : currentDialogue.split("\n")) {
            graphics2D.drawString(line, textCoordinates.getX(), textCoordinates.getY());
            textCoordinates.setY(textCoordinates.getY() + 40);
        }
    }

    private void drawPauseScreen(Graphics2D graphics2D) {
        graphics2D.setColor(BlackTransparent50);
        graphics2D.fillRect(0, 0, ScaleManager.getWorldHeight(), ScaleManager.getWorldWidth());
        String pauseText = "PAUSED!";
        drawStringCenter(pauseText, graphics2D, displayFont, Color.WHITE);
    }

    private int getXForCenteredText(String text, Graphics2D graphics2D, Font font) {
        int textLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return ScaleManager.getScreenWidth() / 2 - textLength / 2;
    }

    private void drawStringCenter(String text, Graphics2D graphics2D, Font font, Color color) {
        int textLength; 
        int x;
        int y;


        graphics2D.setFont(font);
        graphics2D.setColor(color);
        textLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();


        x = (ScaleManager.getScreenWidth() / 2 - textLength / 2);
        y = ScaleManager.getScreenHeight() / 2 - (int) (ScaleManager.getTileSize() * 1.5);
        
        graphics2D.drawString(text, x, y);
    }
}
