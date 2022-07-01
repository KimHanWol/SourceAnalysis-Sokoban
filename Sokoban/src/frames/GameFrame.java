package frames;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import ManagerPanel.PlayManagerPanel;
import mapTool.Map;

public class GameFrame extends ScreenFrame {
	
	private JButton completeButton = new JButton("complete");
	private JButton failedButton = new JButton("failed");
	
	public GameFrame(ActionListener actionListener){
		super.setButton(new JButton[] {completeButton, failedButton}, actionListener);
	}
	
	public void addPlayManager(PlayManagerPanel playManager) {
		add(playManager);
	}
}
