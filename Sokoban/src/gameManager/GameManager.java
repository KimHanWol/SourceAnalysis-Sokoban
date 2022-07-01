package gameManager;

import javax.swing.JButton;

import ManagerPanel.MapMakingManagerPanel;
import ManagerPanel.PlayManagerPanel;
import mapTool.Map;

public class GameManager {
	
	private PlayManagerPanel playManager;
	private MapMakingManagerPanel mapMakingManager;
	
	
	public void setStage(JButton completedButton, JButton failedButton, String mapName) {
		// TODO Auto-generated method stub
		playManager = new PlayManagerPanel(completedButton, failedButton, mapName);
	}
	
	public PlayManagerPanel getPlayManager() {
		return playManager;
	}
	
	public void setMapMakingManager(int mapSize) {
		this.mapMakingManager = new MapMakingManagerPanel(mapSize);
	}
	
	public MapMakingManagerPanel getMapMakingManager() {
		return this.mapMakingManager;
	}
}
