package Model;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {
    private String filePath;
    private Clip clip;

    public Sound(String filePath) {
        this.filePath = filePath;
    }

    public void play() {
        try {
            File file = new File(filePath);
            // tao 1 doi tuong file tu duong truyen vao
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            // AudioInputStream dai dien cho du lieu am thanh

            // khoi tao doi tuong clip
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
}
