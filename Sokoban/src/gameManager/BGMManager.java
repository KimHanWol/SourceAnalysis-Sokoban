package gameManager;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BGMManager {
    public void BackMusic() {
        File file = new File("src/resources/MainBGM.wav");
        try {

            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            clip.loop(-1);
        } catch(Exception e) {

            e.printStackTrace();
        }
    }
    public void MoveMusic() {
        File file = new File("src/resources/move.wav");
        try {

            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}