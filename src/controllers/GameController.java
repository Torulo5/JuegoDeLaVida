package controllers;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.GameModel;


public class GameController {
	
	private GameModel gModel = null; 
	
	private List<NextStateEvent> nextStatelisteners = null;
	

	public GameController(GameModel gModel) {
		super();
		this.gModel = gModel;
		this.nextStatelisteners = new ArrayList<NextStateEvent>();
	}
	
	public void saveAlivePoint(Point alivePoint) {
		gModel.addNewPoint(alivePoint);
		Map<String, List<Point>> map = new HashMap<String, List<Point>>();
		List<Point> points = gModel.getPoints();
		map.put("ALIVE",points);
		map.put("CHECK",gModel.getPointsNeededToCheck());
		map.put("NEXTALIVE",gModel.getNextPointsAlive());
		for (NextStateEvent listeners : this.nextStatelisteners) {
			listeners.newPointEvent(map,points.size());
		}
	}

	public synchronized void nextTurn() {
		gModel.setNextStatus();
		Map<String, List<Point>> map = new HashMap<String, List<Point>>();
		List<Point> points = gModel.getPoints();
		map.put("ALIVE",points);
		map.put("CHECK",gModel.getPointsNeededToCheck());
		map.put("NEXTALIVE",gModel.getNextPointsAlive());
		for (NextStateEvent listeners : this.nextStatelisteners) {
			listeners.nextStateEvent(map,gModel.getSteps(),points.size());
		}
	}
	
	public void clearGame() {
		gModel.clearPoints();
	}
	
	
	public void addNextStatelisteners(NextStateEvent nextStatelistener) {
		this.nextStatelisteners.add(nextStatelistener);
	}
	

}
