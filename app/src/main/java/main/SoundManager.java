package main;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
    Clip clip;
    URL soundURL[] = new URL[30];

    private long clipTime = 0;

    public static final int THEME_MUSIC = 0;
    public static final int PICKUP_KEY_SOUND = 1;
    public static final int WALKING_SOUND = 2;
    public static final int OPEN_DOOR_SOUND = 3;
    public static final int GAME_FINISH = 4;

    public SoundManager() {
        soundURL[0] = getClass().getResource("/sounds/2DRPG_ThemeSong.wav");
        soundURL[1] = getClass().getResource("/sounds/pickup_keys.wav");
        soundURL[2] = getClass().getResource("/sounds/walking.wav");
        soundURL[3] = getClass().getResource("/sounds/open_door_with_keys.wav");
        soundURL[4] = getClass().getResource("/sounds/game_finish.wav");
    }

    public void selectSound(int index) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            return;
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void pause() {
        this.clipTime = clip.getMicrosecondPosition();
        clip.stop();
    }

    public void resume() {
        clip.setMicrosecondPosition(this.clipTime);
        clip.start();
    }

    public void stop() {
        clip.stop();
    }
}
