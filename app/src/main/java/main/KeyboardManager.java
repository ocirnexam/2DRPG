package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardManager implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed, eKeyPressed, enterKeyPressed;

    public boolean isAnyMoveKeyPressed() {
        return upPressed || downPressed || leftPressed || rightPressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            this.upPressed = true;
        }
        else if (code == KeyEvent.VK_A) {
            this.leftPressed = true;
        }
        else if (code == KeyEvent.VK_S) {
            this.downPressed = true;
        }
        else if (code == KeyEvent.VK_D) {
            this.rightPressed = true;
        }
        else if (code == KeyEvent.VK_E) {
            this.eKeyPressed = true;
        } else if (code == KeyEvent.VK_ESCAPE) {
            this.pausePressed = !this.pausePressed;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            this.upPressed = false;
        }
        else if (code == KeyEvent.VK_A) {
            this.leftPressed = false;
        }
        else if (code == KeyEvent.VK_S) {
            this.downPressed = false;
        }
        else if (code == KeyEvent.VK_D) {
            this.rightPressed = false;
        }
        else if (code == KeyEvent.VK_E) {
            this.eKeyPressed = false;
        } else if (code == KeyEvent.VK_ENTER) {
            this.enterKeyPressed = true;
        }
    }
    
}
