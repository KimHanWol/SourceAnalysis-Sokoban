package gameManager;

import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import frames.CompleteFrame;
import frames.GameFrame;
import frames.GameModeFrame;
import frames.IntroFrame;
import frames.MapMakingFrame;

public class FrameManager implements ActionListener {
	private JFrame mainFrame;

	private IntroFrame introFrame;
	private GameModeFrame gameModeFrame;
	private GameFrame gameFrame;
	private CompleteFrame completeFrame;
	private MapMakingFrame mapMakingFrame;

	private Container container;

	private GameManager gameManager;

	public FrameManager(GameManager gameManager) {
		this.gameManager = gameManager;
		mainFrame = new JFrame();
		introFrame = new IntroFrame(this);
		mainFrame = introFrame;
		mainFrame.setVisible(true);
		container = mainFrame.getContentPane();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// startButton
		if (e.getSource() == introFrame.getButton(0)) {
			gameModeFrame = new GameModeFrame(this);
		}
		// mapMakingButton
		else if (e.getSource() == introFrame.getButton(1)) {
			mapMakingButtonClick();
		}
		// soloModeButton
		else if (gameModeFrame != null && e.getSource() == gameModeFrame.getButton(0)) {
			soloButtonClick();
		}
		// co-opModeButton
		else if (gameModeFrame != null && e.getSource() == gameModeFrame.getButton(1)) {
			co_opButtonClick();
		}
		// back to menu
		else if (completeFrame != null && e.getSource() == completeFrame.getButton(0)) {
			backToMenu();
		}
		// back to menu
		else if (mapMakingFrame != null && e.getSource() == mapMakingFrame.getButton(0)) {
			mapMakingBackToMenu();
			return;
		}
		mapMakingTestButton(e);
		gameClearButton(e);
		container.setFocusable(true);
	}

	private void gameClearButton(ActionEvent e) {
		// complete
		if (gameFrame != null && e.getSource() == gameFrame.getButton(0)) {
			completeGame();
		}
		// failed
		else if (gameFrame != null && e.getSource() == gameFrame.getButton(1)) {
			failedGame();
		}
	}

	private void mapMakingTestButton(ActionEvent e) {
		// ??????
		if (mapMakingFrame != null && e.getSource() == mapMakingFrame.getButton(1)) {
			if (gameManager.getMapMakingManager().startGame(mapMakingFrame.getButton(2)))
				mapMakingFrame.getButton(1).setEnabled(false);
		}
		// ??????
		else if (mapMakingFrame != null && e.getSource() == mapMakingFrame.getButton(2)) {
			completeTest();
		}
	}

	private void mapMakingButtonClick() {
		String mapSizeString = JOptionPane.showInputDialog("??? ????????? (3~15)");
		int mapSize;
		try {
			mapSize = Integer.valueOf(mapSizeString);
		} catch (NumberFormatException numError) {
			mapSize = -1;
		}
		if (mapSize < 3 || mapSize > 15) {
			if (mapSizeString != null)
				JOptionPane.showMessageDialog(null, "??? ????????? ?????? ?????????????????????.", "??????", JOptionPane.WARNING_MESSAGE);
			return;
		}
		mainFrame.setVisible(false);
		gameManager.setMapMakingManager(mapSize);
		mapMakingFrame = new MapMakingFrame(this, gameManager.getMapMakingManager());
		mainFrame = mapMakingFrame;
		mainFrame.setVisible(true);
	}

	private void soloButtonClick() {
		String mapName = JOptionPane.showInputDialog("????????? ?????? ???????????????.\n(stage : 1~5)\n(???????????? : save)");
		try {
			if (!new File("src/resources/map/" + mapName + ".txt").exists() || mapName == null
					|| Integer.parseInt(mapName) == 0) {
				JOptionPane.showMessageDialog(null, "????????? ???????????? ????????? ????????? ?????????????????????.", "??????", JOptionPane.WARNING_MESSAGE);
				return;
			}
		} catch (NumberFormatException n) {

		}
		gameFrame = new GameFrame(this);
		gameManager.setStage(gameFrame.getButton(0), gameFrame.getButton(1), mapName);
		gameFrame.addPlayManager(gameManager.getPlayManager());

		if (mapName.equals("1") || mapName.equals("2") || mapName.equals("3") || mapName.equals("4")
				|| mapName.equals("5")) {
			JOptionPane.showMessageDialog(null,
					"????????? ??????????????? ??????????????????.\n???????????? : " + gameManager.getPlayManager().getMap().getHighScore(), mapName,
					JOptionPane.INFORMATION_MESSAGE);
		}

		if (gameManager.getPlayManager().getMap() == null) {
			JOptionPane.showMessageDialog(null, "????????? ??????????????? ??????????????????.", "save", JOptionPane.INFORMATION_MESSAGE);
		}
		mainFrame.setVisible(false);
		gameModeFrame.setVisible(false);
		mainFrame = gameFrame;
		mainFrame.setVisible(true);
	}

	private void co_opButtonClick() {
		mainFrame.setVisible(false);
		gameModeFrame.setVisible(false);
		gameFrame = new GameFrame(this);
		gameManager.setStage(gameFrame.getButton(0), gameFrame.getButton(1), "2p");
		gameFrame.addPlayManager(gameManager.getPlayManager());
		mainFrame = gameFrame;
		mainFrame.setVisible(true);
	}

	private void completeTest() {
		String name = "";
		int nameInt;
		while (true) {
			name = JOptionPane.showInputDialog(null, "??? ????????? ???????????????.", "??????", JOptionPane.OK_CANCEL_OPTION);
			try {
				nameInt = Integer.parseInt(name);
				if (nameInt == 0 || nameInt == 1 || nameInt == 2 || nameInt == 3 || nameInt == 4 || nameInt == 5) {
					JOptionPane.showMessageDialog(null, "??? ????????? ??? ??? ????????????.", "??????", JOptionPane.WARNING_MESSAGE);
				}
			} catch (NumberFormatException numE) {
				break;
			}
		}
		if (name == null)
			return;
		try {
			gameManager.getMapMakingManager().saveMap(name);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		mainFrame.setVisible(false);
		mainFrame = introFrame;
		mainFrame.setVisible(true);
	}

	private void backToMenu() {
		mainFrame.setVisible(false);
		completeFrame.setVisible(false);
		mainFrame = introFrame;
		mainFrame.setVisible(true);
	}

	private void mapMakingBackToMenu() {
		int result = JOptionPane.showConfirmDialog(null, "????????? ????????? ???????????? ????????????.\n????????? ?????????????????????????", "?????????",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == 0) {
			mainFrame.setVisible(false);
			mainFrame = introFrame;
			mainFrame.setVisible(true);
		}
	}

	private void completeGame() {
		completeFrame = new CompleteFrame(this, gameManager.getPlayManager().getPathNum(),
				gameManager.getPlayManager().getPlayTime() / 1000, "Complete!!");
		completeFrame.setVisible(true);
	}

	private void failedGame() {
		completeFrame = new CompleteFrame(this, gameManager.getPlayManager().getPathNum(),
				gameManager.getPlayManager().getPlayTime() / 1000, "Failed!!");
		completeFrame.setVisible(true);
	}

}
