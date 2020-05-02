package controllers;
import java.awt.Point;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameStatesTypes.ArrayGameState;
import models.GameModel;


public class GameController {
	
	private List<GameModel> gModelArray = null; 
	
	private List<NextStateEvent> nextStatelisteners = null;
	

	public GameController() {
		super();
		this.gModelArray = new ArrayList<GameModel>();
		this.nextStatelisteners = new ArrayList<NextStateEvent>();
	}
	
	public void saveAlivePoint(Point alivePoint) {
		int index = 0;
		for (GameModel gModel : this.gModelArray) {
			gModel.addNewPoint(alivePoint);
			Map<String, List<Point>> map = new HashMap<String, List<Point>>();
			List<Point> points = gModel.getPoints();
			map.put("ALIVE",points);
			map.put("CHECK",gModel.getPointsNeededToCheck());
			map.put("NEXTALIVE",gModel.getNextPointsAlive());
			
			NextStateEvent listener = this.nextStatelisteners.get(index);
			listener.newPointEvent(map,points.size());
			index++;
		}
	}

	public synchronized void nextTurn() {
		int index = 0;
		for (GameModel gModel : this.gModelArray) {
			Instant start = Instant.now();
			
			gModel.setNextStatus();
			
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			
			Map<String, List<Point>> map = new HashMap<String, List<Point>>();
			List<Point> points = gModel.getPoints();
			map.put("ALIVE",points);
			map.put("CHECK",gModel.getPointsNeededToCheck());
			map.put("NEXTALIVE",gModel.getNextPointsAlive());
			
			NextStateEvent listener = this.nextStatelisteners.get(index);
			listener.nextStateEvent(map,gModel.getSteps(),points.size(),timeElapsed.toMillis());
			index++;
		}
		
	}
	
	public void clearGame() {
		for (GameModel gModel : this.gModelArray) {
			gModel.clearPoints();
		}
	}
	
	public void addGameModelAndListener(GameModel model,NextStateEvent nextStatelistener) {
		this.gModelArray.add(model);
		this.nextStatelisteners.add(nextStatelistener);
	}
}
