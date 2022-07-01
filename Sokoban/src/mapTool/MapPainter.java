package mapTool;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import gameObject.Baggage;
import gameObject.Wall;

public class MapPainter extends JPanel {

	private Map map;

	private int SPACE = 20;

	private int xOffset;
	private int yOffset;

	private int selX;
	private int selY;
	private char selObj;

	private boolean isCustomMap;

	private boolean selRemove = false;

	public MapPainter(Map map, boolean isCustomMap) {
		setMap(map);
		this.isCustomMap = isCustomMap;
	}

	public void setMap(Map map) {
		this.map = map;
		repaint();
	}

	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	// set world from string level
	private void drawWorld(Graphics g) {
		char[][] arrayMap = map.getArrayMap();

		xOffset = (this.getWidth() - arrayMap.length * SPACE) / 2;
		yOffset = (this.getHeight() - arrayMap[0].length * SPACE) / 2;

		int x = xOffset;
		int y = yOffset;

		g.setColor(new Color(250, 240, 170));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		for (int i = 0; i < arrayMap.length; i++) {

			if (i != 0) {
				y += SPACE;
			}

			x = xOffset;
			for (int j = 0; j < arrayMap[i].length; j++) {
				if (arrayMap[j][i] == '.') {
					g.drawImage(new ImageIcon("src/resources/area.png").getImage(), x, y, this);
				} else if (arrayMap[j][i] == ' ' && isCustomMap) {
					if (isCustomMap) {
						g.drawImage(new ImageIcon("src/resources/space.png").getImage(), x, y, this);
					}
				}
				x += SPACE;
			}
		}
	}

	private void drawObject(Graphics g) {
		// wall
		Wall[] walls = map.getWalls();
		for (int i = 0; i < map.getWalls().length; i++) {
			g.drawImage(new ImageIcon("src/resources/wall.png").getImage(), xOffset + walls[i].getX() * SPACE,
					yOffset + walls[i].getY() * SPACE, this);
		}
		// baggage
		Baggage[] baggages = map.getBaggages();
		for (int i = 0; i < map.getBaggages().length; i++) {
			g.drawImage(new ImageIcon("src/resources/baggage.png").getImage(), xOffset + baggages[i].getX() * SPACE + 2,
					yOffset + baggages[i].getY() * SPACE + 2, this);
		}
		// player
		try {
			g.drawImage(new ImageIcon("src/resources/sokoban.gif").getImage(),
					xOffset + map.getSoko().getX() * SPACE + 2, yOffset + map.getSoko().getY() * SPACE + 2, this);
			if (map.getStage().equals("2p"))
				g.drawImage(new ImageIcon("src/resources/sokoban.gif").getImage(),
						xOffset + map.getSoko2().getX() * SPACE + 2, yOffset + map.getSoko2().getY() * SPACE + 2, this);
		} catch (NullPointerException e) {
			
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawWorld(g);
		drawObject(g);
		if (isCustomMap)
			setSelect(g);
//        else showHighScore(g);
		else
			showGuide(g);
	}

	public void showGuide(Graphics g) {
		g.setColor(Color.black);
		g.drawString("방향키 : 움직이기       /    저장 :  0       /    맵 최고점수 :  " + map.getHighScore()
				+ "       /    현재 움직인 횟수 :  " + map.getPathNum() + "       /    이동횟수 제한 : " + map.getPathLimit(), 10, 10);
	}

	public void select(int x, int y, char object) {
		if (x == -1 && y == -1)
			selRemove = true;
		selX = x;
		selY = y;
		selObj = object;
	}

	public void setSelect(Graphics g) {
		if (selRemove) {
			drawObject(g);
			return;
		}
		switch (selObj) {
		case '#':
			g.drawImage(new ImageIcon("src/resources/wall.png").getImage(), xOffset + selX * SPACE,
					yOffset + selY * SPACE, this);
			break;
		case '$':
			g.drawImage(new ImageIcon("src/resources/baggage.png").getImage(), xOffset + selX * SPACE + 2,
					yOffset + selY * SPACE + 2, this);
			break;
		case '.':
			g.drawImage(new ImageIcon("src/resources/Area.png").getImage(), xOffset + selX * SPACE,
					yOffset + selY * SPACE, this);
			break;
		case ' ':
			g.drawImage(new ImageIcon("src/resources/blank.png").getImage(), xOffset + selX * SPACE,
					yOffset + selY * SPACE, this);
			break;
		case '@':
			g.drawImage(new ImageIcon("src/resources/sokoban.gif").getImage(), xOffset + selX * SPACE + 2,
					yOffset + selY * SPACE + 2, this);
			break;
		}
		g.drawImage(new ImageIcon("src/resources/selected.png").getImage(), xOffset + selX * SPACE,
				yOffset + selY * SPACE, this);
	}
}
