package mapTool;

import java.util.ArrayList;

import gameObject.Baggage;
import gameObject.GameObject;
import gameObject.MovingWall;
import gameObject.Player;
import gameObject.Wall;

public class Map {
	private String stage;

	private char[][] arrayMap;
	private int highScore = 0;
	private int pathNum = 0;
	private long playTime;
	private int pathLimit = 300;

	private Wall[] walls;
	private Baggage[] baggages;
	private MovingWall movingWall;
	private int areasNum = 0;

	private Player soko;
	private Player soko2 = null;

	public Map(char[][] arrayMap, int highScore, int pathNum, long playTime, int pathLimit) {
		this.arrayMap = arrayMap;
		this.highScore = highScore;
		this.pathNum = pathNum;
		this.playTime = playTime;
		this.pathLimit = pathLimit;
		setObj();
	}

	public int getPathLimit() {
		return pathLimit;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public void setArrayMap(char[][] arrayMap) {
		this.arrayMap = arrayMap;

		for (int i = 0; i < arrayMap.length; i++) {
			for (int j = 0; j < arrayMap[i].length; j++) {
				System.out.print(arrayMap[j][i]);
			}
			System.out.println("");
		}

		setObj();
	}

	public void setObj() {
		ArrayList<Wall> wallList = new ArrayList<Wall>();
		ArrayList<Baggage> baggageList = new ArrayList<Baggage>();

		areasNum = 0;
		for (int i = 0; i < arrayMap.length; i++) {
			for (int j = 0; j < arrayMap[i].length; j++) {
				switch (arrayMap[i][j]) {
				// wall
				case '#':
					wallList.add(new Wall(i, j));
					break;
				// baggage
				case '$':
					baggageList.add(new Baggage(i, j));
					break;
				// player
				case '@':
					if (soko == null)
						soko = new Player(i, j);
					else if(stage != null && stage.equals("2p"))
						 soko2 = new Player(i, j);
					break;
				// movingWall
				case '%':
					if (movingWall != null) {
						movingWall.setMovePoint(i, j);
						continue;
					}
					movingWall = new MovingWall(i, j);
					wallList.add(movingWall);
					break;
				// onAreaBaggage
				case '*':
					baggageList.add(setOnAreaBaggage(i, j));
					break;
				// onAreaSoko
				case '&':
					setOnAreaSoko(i, j);
					break;
				// Area
				case '.':
					areasNum++;
					break;
				}
			}
		}
		walls = (Wall[]) wallList.toArray(new Wall[wallList.size()]);
		baggages = (Baggage[]) baggageList.toArray(new Baggage[baggageList.size()]);
	}
	
	private Baggage setOnAreaBaggage(int i, int j) {
		Baggage baggage = new Baggage(i, j);
		baggage.setIsOnArea(true);
		arrayMap[i][j] = '.';
		areasNum++;
		return baggage;
	}
	
	private void setOnAreaSoko(int i, int j) {
		if (soko == null)
			soko = new Player(i, j);
		else if(stage.equals("2p"))
			soko2 = new Player(i, j);
		arrayMap[i][j] = '.';
		areasNum++;
	}

	public String getStage() {
		return stage;
	}

	public char[][] getArrayMap() {
		return arrayMap;
	}

	public Wall[] getWalls() {
		return walls;
	}

	public Baggage[] getBaggages() {
		return baggages;
	}

	public Player getSoko() {
		return soko;
	}

	public Player getSoko2() {
		return soko2;
	}

	public void addPathNum() {
		this.pathNum++;
	}

	public int getPathNum() {
		return pathNum;
	}

	public int getHighScore() {
		return highScore;
	}

	public MovingWall getMovingWall() {
		return movingWall;
	}

	public void setPlayTime(long playTime) {
		this.playTime = playTime;
	}

	public long getPlayTime() {
		return playTime;
	}

	public int getAreaNum() {
		return areasNum;
	}

	public void setpathNum(int pathNum) {
		this.pathNum = pathNum;
	}

}
