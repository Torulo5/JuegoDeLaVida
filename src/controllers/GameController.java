package controllers;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import models.GameModel;
import models.NextStateEvent;


public class GameController {
	
	private GameModel gModel = null; 
	private int steps = 0;
	

	private List<NextStateEvent> nextStatelisteners = null;
	

	public GameController(GameModel gModel) {
		super();
		this.gModel = gModel;
		this.nextStatelisteners = new ArrayList<NextStateEvent>();
	}
	
	public void saveAlivePoint(Point alivePoint) {
		gModel.setAlivePoint(alivePoint);
	}

	
	public ArrayList<Point> getPointsToPaint(int tipo){
		ArrayList<Point> auxPoints = null;
		switch (tipo) {
		  case 1:
			  auxPoints = gModel.getPoints();
		    break;
		  case 2:
			  auxPoints = gModel.getPointsNeededToCheck();
		    break;
		  case 3:
			  auxPoints = gModel.getNextPointsAlive();
		    break;
		}
		System.out.println("-->" + tipo + ":" + auxPoints.size());
		return auxPoints;
	}
	
	public synchronized void nextTurn() {
		steps++;
		gModel.setNextStatus();
		for (NextStateEvent listeners : this.nextStatelisteners) {
			listeners.nextStateEvent("");
		}
		System.out.println(steps);
		
	}
	
	public void clearGame() {
		gModel.clearPoints();
	}
	
	
	public void addNextStatelisteners(NextStateEvent nextStatelistener) {
		this.nextStatelisteners.add(nextStatelistener);
	}
	

}
