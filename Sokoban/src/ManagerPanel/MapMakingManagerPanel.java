package ManagerPanel;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import mapTool.Map;
import mapTool.MapPainter;

public class MapMakingManagerPanel extends JPanel {

	private int mapSize = 10;

	private Map map;
	private MapPainter mapPainter;
	private PlayManagerPanel playManager;

	private int selX = 0;
	private int selY = 0;
	private char selObj = '#';

	private boolean isStarted = false;
	private JButton completedButton;

	private ArrayList<Character> path = new ArrayList<Character>();

	int xOffset = 0;
	int yOffset = 0;

	public MapMakingManagerPanel(int mapSize) {
		this.mapSize = mapSize;
		char[][] tmpMap = new char[mapSize][mapSize];

		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
				tmpMap[i][j] = ' ';
			}
		}

		map = new Map(tmpMap, 0, 0, 0, 0);
		mapPainter = new MapPainter(map, true);
		mapPainter.select(selX, selY, selObj);
		mapPainter.setMap(map);

		repaint();
		this.setLayout(new BorderLayout());
		this.add(mapPainter, BorderLayout.CENTER);

		addKeyListener(new TAdapter());
		setFocusable(true);
	}

	public boolean testMap() {

		if (map.getBaggages().length == 0 || map.getSoko() == null || map.getAreaNum() == 0
				|| map.getBaggages().length != map.getAreaNum())
			return false;

		return true;
	}

	public boolean startGame(JButton completedButton) {

		if (!testMap()) {
			this.requestFocus();
			return false;
		}

		isStarted = true;

		mapPainter.select(-1, -1, ' ');
		playManager = new PlayManagerPanel(completedButton, map, mapPainter);

		this.requestFocus();
		this.addKeyListener(playManager.getTAdapter());
		this.setFocusable(true);
		this.completedButton = completedButton;
		return true;
	}

	public void saveMap(String stage) throws IOException {
		playManager.saveMap(stage);
	}

	public KeyAdapter getAdapter() {
		return new TAdapter();
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (!isStarted) {

				setMoveObj(e);

				setSelObj(e);

				mapPainter.select(selX, selY, selObj);
				mapPainter.setMap(map);
			}
			isComplete();
		}

		public void setMoveObj(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				selX--;
				if (selX < 0)
					selX = 0;
				break;
			case KeyEvent.VK_RIGHT:
				selX++;
				if (selX > mapSize - 1)
					selX = mapSize - 1;
				break;
			case KeyEvent.VK_UP:
				selY--;
				if (selY < 0)
					selY = 0;
				break;
			case KeyEvent.VK_DOWN:
				selY++;
				if (selY > mapSize - 1)
					selY = mapSize - 1;
				break;
			case KeyEvent.VK_SPACE:
				char[][] arrayMap = map.getArrayMap();
				if (map.getSoko() != null && selObj == '@') {
					arrayMap[map.getSoko().getX()][map.getSoko().getY()] = ' ';
					map.getSoko().setX(selX);
					map.getSoko().setY(selY);
					break;
				}
				arrayMap[selX][selY] = selObj;
				map.setArrayMap(arrayMap);
				break;
			}
		}

		public void setSelObj(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_1:
				selObj = '#';
				break;
			case KeyEvent.VK_2:
				selObj = '$';
				break;
			case KeyEvent.VK_3:
				selObj = '.';
				break;
			case KeyEvent.VK_4:
				selObj = '@';
				break;
			case KeyEvent.VK_5:
				selObj = ' ';
				break;
			default:
				break;
			}
		}
	}

	public void isComplete() {
		if (playManager != null && playManager.isComplete()) {
			completedButton.doClick();
		}
	}

}
