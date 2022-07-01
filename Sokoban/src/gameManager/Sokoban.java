package gameManager;

import java.awt.EventQueue;

public class Sokoban{   

    public Sokoban() {
    	GameManager gameManager = new GameManager();
    	new FrameManager(gameManager);
    }
    
	public static void main(String[] args) {
		BGMManager mainbgm = new BGMManager();
        mainbgm.BackMusic();
        
        EventQueue.invokeLater(() -> {
            
            new Sokoban();
        });
    }
}