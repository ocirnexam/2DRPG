package main;

import java.awt.Graphics2D;

import entity.Entity;
import entity.NPC_TownOldest;

public class NPCManager {
    private GamePanel gamePanel;

    private final Entity npc[] = new Entity[10];

    public NPCManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void createNPCs() {
        npc[0] = new NPC_TownOldest(gamePanel);
    }

    public void update() {
        for (int i = 0; i < npc.length; i++) {
            if(npc[i] != null) {
                npc[i].update();
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < npc.length; i++) {
            if(npc[i] != null) {
                npc[i].draw(graphics2D);
            }
        }
    }
}
