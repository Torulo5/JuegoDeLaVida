package controllers;
import java.awt.Point;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import models.GameModel;


public class GameController {
	
	private List<GameModel> gModelArray = null; 
	private List<NextStateEvent> nextStatelisteners = null;
	private Timer timer = null;

	public GameController() {
		super();
		this.gModelArray = new ArrayList<GameModel>();
		this.nextStatelisteners = new ArrayList<NextStateEvent>();
		this.timer = new Timer("GameTurmTImer");
		
		TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                System.out.println("prueba");
                nextTurn();
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 1000);
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
	
	public void removePoint(Point pointTodelete) {
		int index = 0;
		for (GameModel gModel : this.gModelArray) {
			gModel.deletePoint(pointTodelete);
			
			Map<String, List<Point>> map = this.generateMapForFrames(gModel);
			
			NextStateEvent listener = this.nextStatelisteners.get(index);
			listener.newPointEvent(map,gModel.getPoints().size());
		}
	}

	public synchronized void nextTurn() {
		int index = 0;
		for (GameModel gModel : this.gModelArray) {
			Instant start = Instant.now();
			
			gModel.setNextStatus();
			
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			
			Map<String, List<Point>> map = this.generateMapForFrames(gModel);
			
			NextStateEvent listener = this.nextStatelisteners.get(index);
			listener.nextStateEvent(map,gModel.getSteps(),gModel.getPoints().size(),timeElapsed.toNanos());
			index++;
		}
		
	}
	
	public void clearGame() {
		int index = 0;
		for (GameModel gModel : this.gModelArray) {
			
			gModel.clearPoints();
			Map<String, List<Point>> map = this.generateMapForFrames(gModel);
			
			NextStateEvent listener = this.nextStatelisteners.get(index);
			listener.nextStateEvent(map,gModel.getSteps(),gModel.getPoints().size(),0);
			index++;
		}
	}
	
	public void addGameModelAndListener(GameModel model,NextStateEvent nextStatelistener) {
		this.gModelArray.add(model);
		this.nextStatelisteners.add(nextStatelistener);
	}
	
	private Map<String, List<Point>> generateMapForFrames(GameModel gModel){
		Map<String, List<Point>> map = new HashMap<String, List<Point>>();
		map.put("ALIVE",gModel.getPoints());
		map.put("CHECK",gModel.getPointsNeededToCheck());
		map.put("NEXTALIVE",gModel.getNextPointsAlive());
		
		return map;
	}
}
