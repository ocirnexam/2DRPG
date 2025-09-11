package main;

import java.awt.Graphics2D;

import entity.Entity;
import entity.NPC_TownOldest;
import entity.NPC_Major;

public class NPCManager {
    private GamePanel gamePanel;

    public static final int MAX_SIZE = 10;

    private final Entity npc[] = new Entity[MAX_SIZE];

    public NPCManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void createNPCs() {
        npc[0] = new NPC_TownOldest(gamePanel);
        npc[1] = new NPC_Major(gamePanel);
    }

    public Entity[] getNPCArray() {
        return this.npc;
    }

    public void update() {
        for (int i = 0; i < MAX_SIZE; i++) {
            if(npc[i] != null) {
                npc[i].update();
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < MAX_SIZE; i++) {
            if(npc[i] != null) {
                npc[i].draw(graphics2D);
            }
        }
    }
}
