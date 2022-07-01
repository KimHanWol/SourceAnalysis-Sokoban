package ManagerPanel;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import gameObject.Baggage;
import gameObject.GameObject;
import gameObject.MovingWall;
import gameObject.Player;
import gameObject.Wall;
import mapTool.Map;
import mapTool.MapDatabase;
import mapTool.MapPainter;
import util.Direction;

public class PlayManagerPanel extends JPanel {

	private JButton isCompletedButton;
	private JButton isFailedButton;

	private Map map = null;
	private MapPainter mapPainter;
	private MapDatabase mapDatabase;

	private boolean isComplete = false;

	private long startTime;
	private long playTime;

	private boolean didUndo = true;

	private ArrayList<Character> path = new ArrayList<Character>();

	public PlayManagerPanel(JButton isCompletedButton, JButton isFailedButton, String stage) {
		mapDatabase = new MapDatabase();
		map = mapDatabase.loadMap(stage);
		map.setStage(stage);
		mapPainter = new MapPainter(map, false);

		this.isCompletedButton = isCompletedButton;
		this.isFailedButton = isFailedButton;

		initBoard();

		this.setLayout(new BorderLayout());
		this.add(mapPainter, BorderLayout.CENTER);

		startTime = System.currentTimeMillis();
	}

	public PlayManagerPanel(JButton jButton, Map map, MapPainter mapPainter) {
		mapDatabase = new MapDatabase();
		this.map = map;
		this.mapPainter = mapPainter;
		isCompletedButton = jButton;

		initBoard();
		startTime = System.currentTimeMillis();
	}

	public MapPainter getMapPainter() {
		return mapPainter;
	}

	public long getPlayTime() {
		long lastTime = System.currentTimeMillis();
		;
		long playTime = map.getPlayTime();

		lastTime = System.currentTimeMillis();
		playTime += lastTime - startTime;
		this.playTime = playTime;
		return this.playTime;
	}

	private void initBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
	}

	public void saveMap(String stage) throws IOException {
		mapDatabase.saveMap(map, stage);
	}

	public Map getMap() {
		return map;
	}

	public int getPathNum() {
		return map.getPathNum();
	}

	public String getStage() {
		return map.getStage();
	}

	public boolean isComplete() {
		Baggage[] baggages = map.getBaggages();

		if (isFailedButton != null && map.getPathLimit() < map.getPathNum())
			isFailedButton.doClick();

		for (int i = 0; i < baggages.length; i++) {
			if (!baggages[i].getIsOnArea())
				return isComplete = false;
		}

		isCompletedButton.doClick();
		return isComplete = true;
	}

	public void Action(Direction dir, boolean isSoko) {
		if (!isSoko && map.getSoko2() == null)
			return;
		
		System.out.println("test");

		try {
			String stage = map.getStage();
			mapDatabase.saveMap(map, "undo");
			map.setStage(stage);
		} catch (IOException e) {
			e.printStackTrace();
		}

		didUndo = false;

		MovingWall movingWall = map.getMovingWall();
		if (movingWall != null) {
			movingWall.reservation();
			if (getCollisionObject(movingWall) == null)
				movingWall.interact();
		}

		Player actor;

		if (isSoko)
			actor = map.getSoko();
		else
			actor = map.getSoko2();

		actor.reservation(dir);
		GameObject colisionObject = getCollisionObject(actor);

		if (colisionObject == null) {
			actor.interact();
			return;
		}

		if (!colisionObject.interrupt(actor))
			return;

		colisionObject.reservation(dir);

		GameObject colisionObject2 = getCollisionObject(colisionObject);
		if (colisionObject2 == null) {
			actor.interact();
			colisionObject.interact();

			System.out.println(map.getArrayMap()[colisionObject.getX()][colisionObject.getY()]);

			if (colisionObject instanceof Baggage
					&& map.getArrayMap()[colisionObject.getX()][colisionObject.getY()] == '.') {
				((Baggage) colisionObject).setIsOnArea(true);
			}
			return;
		}
	}

	private GameObject getCollisionObject(GameObject obj) {
		Wall[] walls = map.getWalls();
		for (int i = 0; i < walls.length; i++) {
			if (obj.getReservationX() == walls[i].getX() && obj.getReservationY() == walls[i].getY()) {
				System.out.println(walls[i].getX() + "/" + walls[i].getY());
				return walls[i];
			}
		}
		Baggage[] baggages = map.getBaggages();
		for (int i = 0; i < baggages.length; i++) {
			if (obj.getReservationX() == baggages[i].getX() && obj.getReservationY() == baggages[i].getY())
				return baggages[i];
		}
		if (map.getSoko2() != null && obj.getReservationX() == map.getSoko2().getX()
				&& obj.getReservationY() == map.getSoko2().getY()) {
			return map.getSoko2();
		}
		if (map.getSoko() != null && obj.getReservationX() == map.getSoko().getX()
				&& obj.getReservationY() == map.getSoko().getY()) {
			return map.getSoko();
		}

		return null;
	}

	public TAdapter getTAdapter() {
		return new TAdapter();
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			if (isComplete) {
				return;
			}

			int key = e.getKeyCode();

			Direction interactDir = null;
			boolean isSoko = true;

			interactDir = player1KeyInput(key);
			if (interactDir == null) {
				interactDir = player2KeyInput(key);
				if(interactDir != null) isSoko = false;
			}
			else {
				isSoko = true;
			}

			switch (key) {
			case KeyEvent.VK_0:
				map.setPlayTime(getPlayTime());
//				map.setStage("save");
				try {
					mapDatabase.saveMap(map, "save");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;

			case KeyEvent.VK_U:
				Map undoMap = mapDatabase.loadMap("undo");
				if (undoMap != null && !didUndo) {
					didUndo = true;
					undoMap.setpathNum(map.getPathNum() - 1);
					map = undoMap;
				}
				break;
			default:
				break;
			}

			if (interactDir != null)
				Action(interactDir, isSoko);

			mapPainter.setMap(map);

			isComplete();
		}

		public Direction player1KeyInput(int key) {
			Direction interactDir = null;

			switch (key) {

			case KeyEvent.VK_LEFT:
				interactDir = new Direction('l');
				path.add((char) key);
				map.addPathNum();
				break;

			case KeyEvent.VK_RIGHT:
				interactDir = new Direction('r');
				path.add((char) key);
				map.addPathNum();
				break;

			case KeyEvent.VK_UP:
				interactDir = new Direction('t');
				path.add((char) key);
				map.addPathNum();
				break;

			case KeyEvent.VK_DOWN:
				interactDir = new Direction('b');
				path.add((char) key);
				map.addPathNum();
				break;
			}

			return interactDir;
		}

		public Direction player2KeyInput(int key) {
			Direction interactDir = null;
			switch (key) {

			case KeyEvent.VK_A:
				interactDir = new Direction('l');
				path.add((char) key);
				map.addPathNum();
				break;
			case KeyEvent.VK_D:
				interactDir = new Direction('r');
				path.add((char) key);
				map.addPathNum();
				break;
			case KeyEvent.VK_W:
				interactDir = new Direction('t');
				path.add((char) key);
				map.addPathNum();
				break;
			case KeyEvent.VK_S:
				interactDir = new Direction('b');
				path.add((char) key);
				map.addPathNum();
				break;
			}
			
			return interactDir;
		}

	}
}
