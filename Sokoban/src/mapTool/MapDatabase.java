package mapTool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import gameObject.Baggage;

public class MapDatabase {

	public void saveMap(Map map, String stage) throws IOException {
		map.setStage(stage);
		char[][] arrayMap = map.getArrayMap();
		String mapString = arrayMap.length + ":" + map.getHighScore() + ":" + map.getPathNum() + ":" + map.getPlayTime() + "/" + map.getPathLimit() + '\n';

		for (int j = 0; j < arrayMap[0].length; j++) {
			for (int i = 0; i < arrayMap[0].length; i++) {
				if (map.getStage().equals(stage)) {
					mapString = arrayMapToString(map, mapString, arrayMap[i][j], i, j);
				} else
					mapString += arrayMap[i][j];
			}
			mapString += '\n';
		}
		save(stage, mapString);
	}
	
	private String arrayMapToString(Map map, String mapString, char charMap, int i, int j) {
		
		Baggage[] baggages = map.getBaggages();
		switch (charMap) {
		case '@':
			if (map.getSoko().getX() == i && map.getSoko().getY() == j) {
				if (charMap == '.')
					mapString += '&';
				else
					mapString += '@';
			} else
				mapString += ' ';
			break;
		case '$':
		case '.':
		default:
			mapString = setElseObj(baggages, mapString, charMap, map, i, j);
			break;
		}
		
		return mapString;
	}
	
	private String setElseObj(Baggage[] baggages, String mapString, char charMap, Map map, int i, int j) {
		for (int k = 0; k < baggages.length; k++) {
			if (baggages[k].getX() == i && baggages[k].getY() == j) {
				if (charMap == '.')
					mapString += '*';
				else
					mapString += '$';
				break;
			}
			if (k == baggages.length - 1) {
				if (map.getSoko().getX() == i && map.getSoko().getY() == j) {
					if (charMap == '.')
						mapString += '&';
					else
						mapString += '@';
					break;
				}
				if (charMap == '$')
					mapString += ' ';
				else
					mapString += charMap;
			}
		}
		return mapString;
	}
	
	private void save(String stage, String mapString) {
		try {
			OutputStream output = new FileOutputStream("src/resources/map/" + stage + ".txt");
			byte[] by = mapString.getBytes();
			output.write(by);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public Map loadMap(String stage) {
		Map map = null;

		char[][] arrayMap;
		int highScore = 0;
		int pathNum = 0;
		long playTime = 0;
		int pathLimit = 0;

		try {
			File file = new File("src/resources/map/" + stage + ".txt");
			FileReader file_reader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(file_reader);

			String line = bufReader.readLine();
			
			System.out.println(line);
			if(!stage.equals("2p")) pathLimit = Integer.valueOf(line.split("/")[1]);
			else pathLimit = 9999;
			
			line = line.split("/")[0];
			
			int mapSize = Integer.valueOf(line.split(":")[0]);
			arrayMap = new char[mapSize][mapSize];

			String tmp = "";
			for (int i = 0; i < mapSize; i++) {
				tmp = bufReader.readLine();
				for (int j = 0; j < mapSize; j++) {
					arrayMap[j][i] = tmp.toCharArray()[j];
				}
			}
			highScore = Integer.valueOf(line.split(":")[1]);
			if (stage.equals("save")) {
				pathNum = Integer.valueOf(line.split(":")[2]);
				playTime = Long.valueOf(line.split(":")[3]);
			}

			map = new Map(arrayMap, highScore, pathNum, playTime, pathLimit);
			
			bufReader.close();
			file_reader.close();
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		} catch (IOException e) {
			e.getStackTrace();
		}

		map.setStage(stage);
		map.setObj();
		return map;
	}
}
